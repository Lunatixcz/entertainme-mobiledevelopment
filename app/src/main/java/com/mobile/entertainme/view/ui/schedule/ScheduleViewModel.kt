package com.mobile.entertainme.view.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobile.entertainme.data.ScheduleActivity

class ScheduleViewModel : ViewModel() {

    private val _schedules = MutableLiveData<List<ScheduleActivity>>()
    val schedules: LiveData<List<ScheduleActivity>> = _schedules
    private val database = FirebaseDatabase.getInstance().getReference("schedule_activity")

    fun fetchSchedules(userUid: String, category: String = "") {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val scheduleList = mutableListOf<ScheduleActivity>()
                dataSnapshot.children.forEach { snapshot ->
                    val schedule = snapshot.getValue(ScheduleActivity::class.java)
                    schedule?.let {
                        if (it.uid == userUid && (category.isBlank() || it.category == category)) {
                            it.firebaseKey = snapshot.key ?: ""
                            scheduleList.add(it)
                        }
                    }
                }
                _schedules.postValue(scheduleList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun markScheduleAsCompleted(schedule: ScheduleActivity) {
        val updatedSchedule = schedule.copy(completed = true)
        database.child(schedule.firebaseKey).setValue(updatedSchedule)
            .addOnSuccessListener {
                fetchSchedules(schedule.uid) // Refresh the list after updating
            }
            .addOnFailureListener {
                // Handle failure if needed
            }
    }

    fun deleteSchedule(schedule: ScheduleActivity) {
        database.child(schedule.firebaseKey).removeValue()
            .addOnSuccessListener {
                fetchSchedules(schedule.uid)
            }
            .addOnFailureListener {
                // Handle failure if needed
            }
    }
}