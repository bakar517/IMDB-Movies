package com.abubakar.features.movies.discovery

import androidx.paging.PagingSource
import com.abubakar.base.ErrorLogger
import com.abubakar.features.models.ImageUrl
import com.abubakar.features.models.Movie
import com.abubakar.features.models.MoviesResponse
import com.abubakar.features.service.IMDBService
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class DiscoverySourceTest {

    @MockK
    lateinit var service: IMDBService

    @MockK
    lateinit var errorLog: ErrorLogger

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }


    @Test
    fun `should return list of discover movies from service`() = runBlockingTest {
        val mockData = testMoviesResponse()
        coEvery { service.getLatestMovies(any(), any(),any()) }.returns(mockData)

        val pagingSource = DiscoverySource(service = service, errorLog = errorLog)


        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(
            PagingSource.LoadResult.Page(
                data = mockData.movies,
                prevKey = null,
                nextKey = null // we have only one page in mock response!
            )
        )

        coVerify(exactly = 1) { service.getLatestMovies(any(), any(),any()) }
        verify(exactly = 0) { errorLog.log(any()) }
    }

    @Test
    fun `should throw error when try to discover movies from service`() = runBlockingTest {
        val mockException = Exception("Mock exception!!!")
        coEvery { service.getLatestMovies(any(), any(),any()) }.throws(mockException)

        val pagingSource = DiscoverySource(service = service, errorLog = errorLog)

        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(
            PagingSource.LoadResult.Error<Int, Movie>(mockException)
        )

        coVerify(exactly = 1) { service.getLatestMovies(any(), any(),any()) }
        verify(exactly = 1) { errorLog.log(any()) }
    }

    private fun testMoviesResponse() = MoviesResponse(
        page = 1,
        totalPages = 1,
        totalResults = 1,
        movies = listOf(
            Movie(

                adult = false,
                overview = "Go behind the scenes during One Directions sell out \"Take Me Home\" tour and experience life on the road.",
                releaseDate = "2013-08-30",
                id = 164558,
                title = "One Direction: This Is Us",
                poster = ImageUrl.PosterImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                banner = ImageUrl.BannerImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                voteCount = 55,
                voteAverage = 8.45F
            )
        )
    )

}