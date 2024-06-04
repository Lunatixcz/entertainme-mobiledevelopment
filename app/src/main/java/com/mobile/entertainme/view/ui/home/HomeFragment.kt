package com.mobile.entertainme.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.mobile.entertainme.R
import com.mobile.entertainme.adapter.BookAdapter
import com.mobile.entertainme.adapter.MovieAdapter
import com.mobile.entertainme.api.ApiConfig
import com.mobile.entertainme.databinding.FragmentHomeBinding
import com.mobile.entertainme.response.BookResponse
import com.mobile.entertainme.response.MovieResponse
import com.mobile.entertainme.view.detail.book.BookDetailActivity
import com.mobile.entertainme.view.detail.movie.MovieDetailActivity
import com.mobile.entertainme.view.login.LoginActivity
import com.mobile.entertainme.view.ui.survey.SurveyFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var bookAdapter: BookAdapter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()

        bookAdapter = BookAdapter()
        movieAdapter = MovieAdapter()

        homeViewModel.username.observe(viewLifecycleOwner) { username ->
            binding.username.text = username
        }

        binding.logout.setOnClickListener {
            homeViewModel.clearGoogleSignInInfo()
            homeViewModel.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        binding.seeAllBooks.setOnClickListener {
            startActivity(Intent(requireContext(), BookDetailActivity::class.java))
        }

        binding.seeAllMovies.setOnClickListener {
            startActivity(Intent(requireContext(), MovieDetailActivity::class.java))
        }

        setupRecyclerView()
        fetchBooks()
        fetchMovies()

        return root
    }

    private fun setupRecyclerView() {
        binding.bookRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = bookAdapter
        }

        binding.movieRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieAdapter
        }
    }

    private fun fetchBooks() {
        val user = auth.currentUser
        val uid = user?.uid

        if (uid != null) {
            val client = ApiConfig.getApiService().getRecommendedBooks(uid)
            client.enqueue(object : Callback<BookResponse> {
                override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                    if (response.isSuccessful) {
                        val books = response.body()?.titles?.filterNotNull()
                        books?.forEach { Log.d("BookData", it.toString()) }
                        bookAdapter.submitList(books)
                    }
                }
                override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                    // Handle failure
                }
            })
        } else {
            // Handle case where user is not logged in
        }
    }

    private fun fetchMovies() {
        val client = ApiConfig.getApiService().getRecommendedMovies()
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { movies ->
                        movieAdapter.submitList(movies.filterNotNull())
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}