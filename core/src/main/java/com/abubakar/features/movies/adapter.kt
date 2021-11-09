package com.abubakar.features.movies

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abubakar.features.R
import com.abubakar.features.databinding.ListLatestMoviesBinding
import com.abubakar.features.databinding.LoadingErrorLayoutBinding
import com.abubakar.features.databinding.PromotionMovieBinding
import com.abubakar.features.util.ext.loadImage
import com.abubakar.features.util.ext.showOrHide
import com.abubakar.features.util.ext.visible

private const val TYPE_PROMOTION = 1
private const val TYPE_ITEM = 2

private const val RatingOutOf = 10

class MovieListAdapter(
    private val showPromoBanner: Boolean = true,
    private val onClick: (item: MovieItem) -> Unit
) : PagingDataAdapter<MovieItem, RecyclerView.ViewHolder>(MovieItemDiffUtil) {

    //Since we don't have our custom api to show banner on the top of the screen
    //I have considered first item as promotion item
    override fun getItemViewType(position: Int) = if (showPromoBanner) {
        if (position == 0) TYPE_PROMOTION else TYPE_ITEM
    } else {
        TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_PROMOTION) {
            (holder as PromotionMovieViewHolder).bind(getItem(position))
        } else {
            (holder as MovieViewHolder).bind(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) {
            MovieViewHolder(
                inflate(
                    from(parent.context),
                    R.layout.list_latest_movies,
                    parent,
                    false
                ),
                onClick
            )
        } else {
            PromotionMovieViewHolder(
                inflate(
                    from(parent.context),
                    R.layout.promotion_movie,
                    parent,
                    false
                ),
                onClick
            )
        }
    }

}

class MovieViewHolder(
    private val binding: ListLatestMoviesBinding,
    private val onClick: (item: MovieItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieItem?) {
        if (item == null) return
        binding.root.setOnClickListener {
            onClick(item)
        }
        binding.bind(item)
    }

    private fun ListLatestMoviesBinding.bind(item: MovieItem) {
        lblTitle.text = item.title
        lblDuration.text = item.releaseDate
        lblRating.text = item.voteAverage.rating
        progress.visible()
        image.loadImage(item.poster, progress)
    }
}

class PromotionMovieViewHolder(
    private val binding: PromotionMovieBinding,
    private val onClick: (item: MovieItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieItem?) {
        if (item == null) return
        binding.root.setOnClickListener {
            onClick(item)
        }
        binding.bind(item)
    }

    private fun PromotionMovieBinding.bind(item: MovieItem) {
        lblTitle.text = item.title
        lblReleaseYear.text = item.releaseDate
        lblRating.text = item.voteAverage.rating
        backdrop.image.loadImage(item.banner, backdrop.imageLoading)
    }
}

class LoadingErrorStateAdapter : LoadStateAdapter<LoadingErrorStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.view.title.showOrHide(loadState is LoadState.Error)
        holder.view.progress.showOrHide(loadState == LoadState.Loading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            inflate(
                from(parent.context),
                R.layout.loading_error_layout,
                parent,
                false
            )
        )
    }

    class ViewHolder(val view: LoadingErrorLayoutBinding) :
        RecyclerView.ViewHolder(view.root)
}

private val Float.rating get() = "$this/$RatingOutOf"

private object MovieItemDiffUtil : DiffUtil.ItemCallback<MovieItem>() {
    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
        oldItem == newItem
}
