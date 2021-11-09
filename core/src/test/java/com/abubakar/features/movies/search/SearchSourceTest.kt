package com.abubakar.features.movies.search

import androidx.paging.PagingSource
import com.abubakar.base.ErrorLogger
import com.abubakar.features.models.ImageUrl
import com.abubakar.features.models.Movie
import com.abubakar.features.models.MoviesResponse
import com.abubakar.features.service.IMDBService
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class SearchSourceTest {

    @MockK
    lateinit var service: IMDBService

    @MockK
    lateinit var errorLog: ErrorLogger


    val query = "King"

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
    }

    @Test
    fun `should return list of discover movies from service`() = runBlockingTest {
        val mockData = searchResponse()
        coEvery { service.searchMovie(any(), any()) }.returns(mockData)

        val pagingSource = SearchSource(service, errorLog, query)
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertThat(
            result
        ).isEqualTo(
            PagingSource.LoadResult.Page(
                data = mockData.movies,
                prevKey = null,
                nextKey = null // we have only one page in mock response!
            )
        )

        coVerify(exactly = 1) { service.searchMovie(any(), any()) }
        verify(exactly = 0) { errorLog.log(any()) }
    }

    @Test
    fun `should throw error when try to discover movies from service`() = runBlockingTest {
        val mockException = Exception("Mock exception!!!")
        coEvery { service.searchMovie(any(), any()) }.throws(mockException)

        val pagingSource = SearchSource(service, errorLog, query)

        Truth.assertThat(
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

        coVerify(exactly = 1) { service.searchMovie(any(), any()) }
        verify(exactly = 1) { errorLog.log(any()) }
    }


    private fun searchResponse() = MoviesResponse(
        page = 1,
        totalPages = 1,
        totalResults = 1,
        movies = listOf(
            Movie(
                adult = false,
                overview = "A young lion prince is cast out of his pride by his cruel uncle, who claims he killed his father. While the uncle rules with an iron paw, the prince grows up beyond the Savannah, living by a philosophy: No worries for the rest of your days. But when his past comes to haunt him, the young prince must decide his fate: Will he remain an outcast or face his demons and become what he needs to be?",
                releaseDate = "1994-06-23",
                id = 8587,
                title = "The Lion King",
                poster = ImageUrl.PosterImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                banner = ImageUrl.BannerImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                voteCount = 14578,
                voteAverage = 8.3F
            ),

            Movie(
                adult = false,
                overview = "The King of Fighters movie will introduce a new science fiction spin into the setting established in the games universe by following the surviving members of three legendary fighting clans who are continually whisked away to other dimensions by an evil power. As the fighters enter each new world they battle that universes native defenders, while the force that brought them seeks to find a way to invade and infect our world.",
                releaseDate = "2010-08-26",
                id = 44571,
                title = "The King of Fighters",
                poster = ImageUrl.PosterImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                banner = ImageUrl.BannerImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                voteCount = 89,
                voteAverage = 4.7F
            )
        )
    )

}