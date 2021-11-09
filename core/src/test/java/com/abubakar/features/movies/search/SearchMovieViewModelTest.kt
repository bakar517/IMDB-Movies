package com.abubakar.features.movies.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abubakar.base.EventLogger
import com.abubakar.base.Navigator
import com.abubakar.features.getOrAwaitValue
import com.abubakar.features.movies.MovieItem
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchMovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: SearchRepository

    @MockK
    lateinit var navigator: Navigator

    @MockK
    lateinit var movieItem: MovieItem

    @MockK
    lateinit var eventLogger: EventLogger

    lateinit var viewModel: SearchMovieViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }

    @Test
    fun `navigate to detail screen`() {
        every { navigator.navigate(any()) }.returns(Unit)
        every { eventLogger.log(any()) }.returns(Unit)

        viewModel = SearchMovieViewModel(repository, eventLogger, navigator)
        val state = viewModel.state.getOrAwaitValue()
        state.onItemClick(movieItem)

        verify(exactly = 1) { eventLogger.log(any()) }
        verify(exactly = 1) { navigator.navigate(any()) }
    }

}