package com.mobile.entertainme.view.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.entertainme.api.ApiConfig
import com.mobile.entertainme.response.MovieDataItem
import com.mobile.entertainme.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<MovieDataItem>>()
    val movies: LiveData<List<MovieDataItem>> = _movies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchDetailMovies() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRecommendedMovies()
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { movies ->
                        _movies.value = movies.filterNotNull()
                    }
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Handle failure
                _isLoading.value = false
            }
        })
    }
}