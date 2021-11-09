package com.abubakar.features.details

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abubakar.features.R
import com.abubakar.features.databinding.ListItemCastBinding
import com.abubakar.features.util.ext.loadImage

class MovieCastAdapter
    : ListAdapter<MovieCast, MovieCastViewHolder>(MovieCastItemDiffUtil) {
    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        return MovieCastViewHolder(
            inflate(
                from(parent.context),
                R.layout.list_item_cast,
                parent,
                false
            )
        )
    }

}

class MovieCastViewHolder(
    private val binding: ListItemCastBinding,
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: MovieCast?) {
        if (item == null) return
        binding.bind(item)
    }

    private fun ListItemCastBinding.bind(item: MovieCast) {
        lblName.text = item.name
        image.loadImage(item.image)
    }
}


private object MovieCastItemDiffUtil : DiffUtil.ItemCallback<MovieCast>() {
    override fun areItemsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean =
        oldItem == newItem
}