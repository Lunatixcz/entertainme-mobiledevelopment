package com.mobile.entertainme.repository

import com.google.firebase.database.FirebaseDatabase

class RecommendationSurveyRepository {

    private val database = FirebaseDatabase.getInstance().getReference("recommendation_surveys")

    fun submitSurvey(surveyData: HashMap<String, Any>, callback: (Boolean) -> Unit) {
        val newSurveyRef = database.push()

        newSurveyRef.setValue(surveyData).addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
    }
}