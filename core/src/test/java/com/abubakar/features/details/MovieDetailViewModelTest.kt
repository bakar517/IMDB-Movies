package com.abubakar.features.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abubakar.base.ErrorLogger
import com.abubakar.base.EventLogger
import com.abubakar.base.Experiment
import com.abubakar.features.BaseTest
import com.abubakar.features.details.Status.Error
import com.abubakar.features.getOrAwaitValue
import com.abubakar.features.models.ImageUrl
import com.abubakar.features.models.ImageUrl.BannerImage
import com.abubakar.features.models.ImageUrl.ProfileImage
import com.abubakar.features.service.IMDBService
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var IMDBService: IMDBService

    @MockK
    lateinit var experiment: Experiment

    @MockK
    lateinit var eventLogger: EventLogger

    @MockK
    lateinit var errorLogger: ErrorLogger

    @MockK
    lateinit var mapper: UIMapper

    @MockK
    lateinit var args: MovieDetailsArgs

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
    }

    @Test
    fun `fetch detail of the movies from remote`() {
        every { args.id }.returns(1)
        coEvery { IMDBService.getMovieDetail(any()) }.returns(response())
        every { mapper.mapToMovieDetail(any()) }.returns(movieDetail())

        val viewModel =
            MovieDetailViewModel(IMDBService, experiment, eventLogger, errorLogger, mapper, args)

        val state = viewModel.state.getOrAwaitValue()
        val result = state.details!!

        assertThat(result.id).isEqualTo(550)
        assertThat(result.title).isEqualTo("Fight Club")
        assertThat(result.releaseDate).isEqualTo("1999-10-12")

    }


    @Test
    fun `throw error while try to load movie detail`() {
        val exception = Exception("Mock Exception!!")
        every { args.id }.returns(1)
        coEvery { IMDBService.getMovieDetail(any()) }.throws(exception)

        val viewModel =
            MovieDetailViewModel(IMDBService, experiment, eventLogger, errorLogger, mapper, args)
        val state = viewModel.state.getOrAwaitValue()

        assertThat(state.status).isInstanceOf(Error::class.java)

        verify(exactly = 1) { errorLogger.log(any()) }
    }


    @Test
    fun `load the information of movie cast`() {
        every { args.id }.returns(1)
        coEvery { IMDBService.getMovieCastInfo(any()) }.returns(castResponse())
        every { mapper.mapToMovieCast(any()) }.returns(movieCastList())

        val viewModel =
            MovieDetailViewModel(IMDBService, experiment, eventLogger, errorLogger, mapper, args)

        val state = viewModel.state.getOrAwaitValue()
        val result = state.castDetails!!

        assertThat(result.size).isEqualTo(2)
        assertThat(result.first().id).isEqualTo(1)
        assertThat(result.first().name).isEqualTo("Edward Norton")
    }


    @Test
    fun `throw error while try to load movie cast information`() {
        val exception = Exception("Mock Exception!!")
        every { args.id }.returns(1)
        coEvery { IMDBService.getMovieCastInfo(any()) }.throws(exception)

        val viewModel =
            MovieDetailViewModel(IMDBService, experiment, eventLogger, errorLogger, mapper, args)
        val state = viewModel.state.getOrAwaitValue()

        assertThat(state.status).isInstanceOf(Error::class.java)

        coVerify(exactly = 1) { IMDBService.getMovieCastInfo(any()) }
        verify(exactly = 1) { errorLogger.log(any()) }
    }


    @Test
    fun `log event when save the movie`() {
        every { args.id }.returns(1)
        coEvery { IMDBService.getMovieDetail(any()) }.returns(response())
        every { mapper.mapToMovieDetail(any()) }.returns(movieDetail())

        val viewModel =
            MovieDetailViewModel(IMDBService, experiment, eventLogger, errorLogger, mapper, args)

        val state = viewModel.state.getOrAwaitValue()

        state.onAddToList(state.details!!)
        verify(exactly = 1) { eventLogger.log(any()) }
    }

    @Test
    fun `log event when share the movie`() {
        every { args.id }.returns(1)
        coEvery { IMDBService.getMovieDetail(any()) }.returns(response())
        every { mapper.mapToMovieDetail(any()) }.returns(movieDetail())

        val viewModel =
            MovieDetailViewModel(IMDBService, experiment, eventLogger, errorLogger, mapper, args)

        val state = viewModel.state.getOrAwaitValue()

        state.onShareMovie(state.details!!)
        verify(exactly = 1) { eventLogger.log(any()) }
    }

    private fun response() = MovieResponse(
        adult = false,
        genres = listOf(
            Genres(18, "Drama")
        ),
        id = 550,
        overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
        banner = BannerImage("/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg"),
        poster = ImageUrl.PosterImage("/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg"),
        releaseDate = "1999-10-12",
        duration = 139,
        title = "Fight Club",
        voteAverage = 7.8F,
        voteCount = 3439,
    )

    private fun movieDetail() = MovieDetail(
        ageLimit = 13,
        genres = listOf(
            "Drama"
        ),
        id = 550,
        overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
        banner = BannerImage("/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg"),
        poster = ImageUrl.PosterImage("/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg"),
        releaseDate = "1999-10-12",
        duration = "",
        title = "Fight Club",
        voteAverage = 7.8F,
        voteCount = 3439,
    )


    private fun castResponse() = MovieCastResponse(
        id = 1,
        cast = listOf(
            movieCastInfo(),
            movieCastInfo(id = 2, name = "Brad Pitt")
        )
    )

    private fun movieCastInfo(
        id: Int = 1,
        name: String = "Edward Norton",
        profileImage: String = "/5XBzD5WuTyVQZeS4VI25z2moMeY.jpg"
    ) = MovieCastInfo(id, name, ProfileImage(profileImage))

    private fun movieCastList() = listOf(movieCast(), movieCast(id = 2, name = "Brad Pitt"))

    private fun movieCast(
        id: Int = 1,
        name: String = "Edward Norton",
        profileImage: String = "/5XBzD5WuTyVQZeS4VI25z2moMeY.jpg"
    ) = MovieCast(id, name, ProfileImage(profileImage))
}