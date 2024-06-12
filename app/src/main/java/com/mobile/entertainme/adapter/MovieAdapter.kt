package com.mobile.entertainme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mobile.entertainme.R
import com.mobile.entertainme.databinding.RvHomeItemBinding
import com.mobile.entertainme.response.MovieDataItem

class MovieAdapter : ListAdapter<MovieDataItem, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = RvHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MovieViewHolder(private val binding: RvHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDataItem) {
            binding.ivItemCover.load(movie.coverUrl) {
                placeholder(R.drawable.movie_placeholder_image)
                error(R.drawable.movie_placeholder_image)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieDataItem>() {
            override fun areItemsTheSame(oldItem: MovieDataItem, newItem: MovieDataItem): Boolean {
                return oldItem.coverUrl == newItem.coverUrl
            }

            override fun areContentsTheSame(oldItem: MovieDataItem, newItem: MovieDataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}