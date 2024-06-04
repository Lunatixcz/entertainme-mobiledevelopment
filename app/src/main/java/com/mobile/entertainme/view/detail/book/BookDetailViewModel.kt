package com.mobile.entertainme.view.detail.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.entertainme.api.ApiConfig
import com.mobile.entertainme.response.BookDataItem
import com.mobile.entertainme.response.BookResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailViewModel : ViewModel() {

    private val _books = MutableLiveData<List<BookDataItem>>()
    val books: LiveData<List<BookDataItem>> = _books

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchDetailBooks(uid: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRecommendedBooks(uid)
        client.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    response.body()?.titles?.let { books ->
                        _books.value = books.filterNotNull()
                    }
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                // Handle failure
                _isLoading.value = false
            }
        })
    }
}