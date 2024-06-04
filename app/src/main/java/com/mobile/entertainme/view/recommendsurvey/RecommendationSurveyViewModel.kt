package com.mobile.entertainme.view.recommendsurvey

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class RecommendationSurveyViewModel(context: Context) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun submitSurvey(surveyData: HashMap<String, Any>) {
        _isLoading.value = true
        val database = FirebaseDatabase.getInstance()
        val surveysRef = database.getReference("recommendation_surveys")
        val newSurveyRef = surveysRef.push()

        newSurveyRef.setValue(surveyData).addOnCompleteListener { task ->
            _isLoading.value = false
            if (task.isSuccessful) {
                _isSuccessful.value = true
            } else {
                _isSuccessful.value = false
                _errorMessage.value = task.exception?.message
            }
        }
    }
}