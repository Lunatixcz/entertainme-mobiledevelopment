package com.mobile.entertainme.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.entertainme.view.login.LoginViewModel
import com.mobile.entertainme.view.recommendsurvey.RecommendationSurveyViewModel
import com.mobile.entertainme.view.ui.home.HomeViewModel

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(application) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(application) as T
            modelClass.isAssignableFrom(RecommendationSurveyViewModel::class.java) -> RecommendationSurveyViewModel(application) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}