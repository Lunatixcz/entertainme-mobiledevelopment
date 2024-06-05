package com.mobile.entertainme.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mobile.entertainme.data.ScheduleActivity

class AddScheduleViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun addSchedule(title: String, description: String, category: String, date: String, time: String) {
        _isLoading.value = true

        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            val scheduleActivity = ScheduleActivity(
                title = title,
                description = description,
                category = category,
                date = date,
                time = time,
                completed = false,
                uid = uid
            )

            val database = FirebaseDatabase.getInstance().reference
            val scheduleId = database.child("schedule_activity").push().key

            if (scheduleId != null) {
                database.child("schedule_activity").child(scheduleId).setValue(scheduleActivity)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val updatedScheduleActivity = scheduleActivity.copy(firebaseKey = scheduleId)
                            database.child("schedule_activity").child(scheduleId).setValue(updatedScheduleActivity)
                                .addOnCompleteListener { updateTask ->
                                    _isLoading.value = false
                                    _isSuccess.value = updateTask.isSuccessful
                                }
                        } else {
                            _isLoading.value = false
                            _isSuccess.value = false
                        }
                    }
            } else {
                _isLoading.value = false
                _isSuccess.value = false
            }
        } else {
            _isLoading.value = false
            _isSuccess.value = false
        }
    }
}