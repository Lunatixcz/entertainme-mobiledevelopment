package com.mobile.entertainme.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(name: String, email: String, password: String) {
        _isLoading.value = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid
                    val database = FirebaseDatabase.getInstance().getReference("Users")
                    val userMap = mapOf(
                        "name" to name,
                        "email" to email
                    )
                    uid?.let {
                        database.child(it).setValue(userMap)
                            .addOnCompleteListener { dbTask ->
                                _isLoading.value = false
                                if (dbTask.isSuccessful) {
                                    _isSuccessful.value = true
                                } else {
                                    _isSuccessful.value = false
                                    _errorMessage.value = dbTask.exception?.message
                                }
                            }
                    }
                } else {
                    _isLoading.value = false
                    _isSuccessful.value = false
                    _errorMessage.value = task.exception?.message
                }
            }
    }
}