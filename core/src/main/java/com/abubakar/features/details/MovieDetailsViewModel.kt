package com.abubakar.features.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abubakar.base.ErrorLogger
import com.abubakar.base.EventLogger
import com.abubakar.base.Experiment
import com.abubakar.features.models.ImageUrl.*
import com.abubakar.features.service.IMDBService
import com.abubakar.features.util.ext.latest
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val KeyNewDetailsUi = "new_details_ui"

class MovieDetailViewModel @AssistedInject constructor(
    private val IMDBService: IMDBService,
    private val experiment: Experiment,
    private val eventLogger: EventLogger,
    private val errorLogger: ErrorLogger,
    private val mapper: UIMapper,
    @Assisted val args: MovieDetailsArgs,
) : ViewModel() {

    private val _state = MutableLiveData(ViewState(
        onAddToList = {
            //TODO implement add to favorites
            eventLogger.log(OnTapMovieShare(it))
        },
        onShareMovie = {
            //TODO implement image share here
            eventLogger.log(OnTapMovieShare(it))
        }
    ))

    val state: LiveData<ViewState>
        get() = _state

    init {
        viewModelScope.loadMovie()
    }

    private fun CoroutineScope.loadMovie() {

        _state.value = _state.latest.copy(status = Status.Loading)

        launch {
            //I am showing an action views through A/B testing where only users who are in enable group can see it
            //This is just for proof of concept as I didn't integrate any 3rd party tool for A/B testing like Firbase
            //Apptimze etc but we can easily implement them. I just used a fake implementation for demo

            val showNewUI = experiment.booleanKey(KeyNewDetailsUi, false)
            _state.value =
                _state.latest.copy(showActionUI = showNewUI)
        }

        launch {

            runCatching { IMDBService.getMovieDetail(args.id) }
                .onFailure {
                    errorLogger.log(it)
                    _state.value = _state.latest.copy(status = Status.Error)
                }.onSuccess {
                    _state.value =
                        _state.latest.copy(status = null, details = mapper.mapToMovieDetail(it))
                }
        }

        launch {

            runCatching { IMDBService.getMovieCastInfo(args.id) }
                .onFailure {
                    errorLogger.log(it)
                    _state.value = _state.latest.copy(status = Status.Error)
                }.onSuccess {
                    _state.value =
                        _state.latest.copy(castDetails = mapper.mapToMovieCast(it))
                }
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(args: MovieDetailsArgs): MovieDetailViewModel
    }

}

data class ViewState(
    val status: Status? = null,
    val details: MovieDetail? = null,
    val showActionUI: Boolean = false,
    val castDetails: List<MovieCast>? = null,
    val onAddToList: (item: MovieDetail) -> Unit,
    val onShareMovie: (item: MovieDetail) -> Unit,
)

sealed interface Status {
    object Loading : Status
    object Error : Status
}

data class MovieDetail(
    val id: Int,
    val overview: String?,
    val ageLimit: Int,
    val banner: BannerImage?,
    val poster: PosterImage?,
    val genres: List<String>,
    val duration: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Float,
    val voteCount: Int,
)

data class MovieCast(
    val id: Int,
    val name: String,
    val image: ProfileImage?,
)
