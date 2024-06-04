package com.mobile.entertainme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mobile.entertainme.databinding.MovieDetailItemBinding
import com.mobile.entertainme.response.MovieDataItem

class MovieDetailAdapter : ListAdapter<MovieDataItem, MovieDetailAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    inner class MovieViewHolder(private val binding: MovieDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDataItem) {
            binding.ivItemMovieCover.load(movie.posterUrl)
            binding.movieYear.text = movie.releaseYear
            binding.movieTitle.text = movie.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = MovieDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieDataItem>() {
            override fun areItemsTheSame(oldItem: MovieDataItem, newItem: MovieDataItem): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: MovieDataItem, newItem: MovieDataItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}