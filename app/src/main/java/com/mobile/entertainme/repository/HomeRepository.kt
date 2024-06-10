package com.mobile.entertainme.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.mobile.entertainme.api.ApiConfig
import com.mobile.entertainme.response.BookDataItem
import com.mobile.entertainme.response.BookResponse
import com.mobile.entertainme.response.MovieDataItem
import com.mobile.entertainme.response.MovieResponse
import com.mobile.entertainme.response.StressPredictionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    private val _books = MutableLiveData<List<BookDataItem>>()
    val books: LiveData<List<BookDataItem>> = _books

    private val _movies = MutableLiveData<List<MovieDataItem>>()
    val movies: LiveData<List<MovieDataItem>> = _movies

    fun fetchBooks(uid: String) {
        val client = ApiConfig.getApiService().getRecommendedBooks(uid)
        client.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    _books.value = response.body()?.titles?.filterNotNull()
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    fun fetchMovies() {
        val client = ApiConfig.getApiService().getRecommendedMovies()
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    _movies.value = response.body()?.data?.filterNotNull()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
}