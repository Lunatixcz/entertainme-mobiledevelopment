package com.mobile.entertainme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mobile.entertainme.R
import com.mobile.entertainme.databinding.RvHomeItemBinding
import com.mobile.entertainme.response.BookDataItem

class BookAdapter : ListAdapter<BookDataItem, BookAdapter.BookViewHolder>(DIFF_CALLBACK) {

    inner class BookViewHolder(private val binding: RvHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookDataItem) {
            var coverUrl = book.coverUrl
            if (!coverUrl.isNullOrEmpty()) {
                if (coverUrl.startsWith("http://")) {
                    coverUrl = coverUrl.replace("http://", "https://")
                }
                binding.ivItemCover.load(coverUrl) {
                    placeholder(R.drawable.book_placeholder_image)
                    error(R.drawable.book_placeholder_image)
                }
            } else {
                binding.ivItemCover.load(R.drawable.book_placeholder_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = RvHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookDataItem>() {
            override fun areItemsTheSame(oldItem: BookDataItem, newItem: BookDataItem): Boolean {
                return oldItem.book == newItem.book
            }

            override fun areContentsTheSame(oldItem: BookDataItem, newItem: BookDataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}