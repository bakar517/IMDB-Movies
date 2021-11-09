package com.abubakar.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.abubakar.features.R
import com.abubakar.features.databinding.FragmentMovieDetailBinding
import com.abubakar.features.util.RecyclerViewRightSpace
import com.abubakar.features.util.ext.*
import javax.inject.Inject

class MovieDetailsFragment @Inject constructor(
    vmFactory: MovieDetailViewModel.Factory
) : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailsFragmentArgs>()
    private val adapter = MovieCastAdapter()
    private val viewModel by lazy { vmFactory.create(args.movieDetail) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(R.layout.fragment_movie_detail, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupMovieCastList()

        viewModel.state.observe(viewLifecycleOwner) {
            binding.bind(it)
        }
    }

    private fun setupMovieCastList() {
        val itemDecoration = RecyclerViewRightSpace(
            resources.getDimension(R.dimen.spacing_small).toInt()
        )
        binding.castList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@MovieDetailsFragment.adapter
            overScrollMode = View.OVER_SCROLL_NEVER
            addItemDecoration(itemDecoration)
        }
    }

    private fun FragmentMovieDetailBinding.bind(state: ViewState) {

        hideAll(progress, lblError, movieDetailGroup)

        if (state.status != null) {
            when (state.status) {
                Status.Loading -> progress.visible()

                Status.Error -> lblError.visible()
            }
            return
        }

        if (state.details != null) {
            movieDetailGroup.visible()
            showDetails(state)
        }

        if (state.castDetails != null) {
            castGroup.visible()
            adapter.submitList(state.castDetails)
        }
    }

    private fun FragmentMovieDetailBinding.showDetails(state: ViewState) {
        if (state.details == null) return

        with(state.details) {

            lblTitle.text = title
            lblReleaseYear.text = releaseDate
            lblAdult.text = ("$ageLimit+")
            lblDuration.text = duration
            lblOverview.text = overview
            genresGroup.addChips(genres)

            detailsActionLayout.root.showOrHide(state.showActionUI)

            backdrop.image.loadImage(banner, binding.backdrop.imageLoading)

            detailsActionLayout.btnShare.setOnClickListener { state.onShareMovie(state.details) }

            detailsActionLayout.btnAddToList.setOnClickListener { state.onAddToList(state.details) }
        }

    }

}
