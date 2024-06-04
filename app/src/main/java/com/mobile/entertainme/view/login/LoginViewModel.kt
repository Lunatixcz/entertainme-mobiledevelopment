package com.mobile.entertainme.view.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.mobile.entertainme.utils.UserPreferences

class LoginViewModel(context: Context) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userPreferences = UserPreferences(context)

    fun loginUser(email: String, password: String) {
        _isLoading.value = true

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    userPreferences.setLoggedIn(true)
                    _isSuccessful.value = true
                } else {
                    _isSuccessful.value = false
                    _errorMessage.value = task.exception?.message
                }
            }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        _isLoading.value = true
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user?.uid
                    val userName = user?.displayName ?: "No Name"
                    val userEmail = user?.email ?: "No Email"
                    if (userId != null) {
                        val userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId)
                        val userMap = mapOf(
                            "name" to userName,
                            "email" to userEmail
                        )
                        userRef.setValue(userMap).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                userPreferences.setLoggedIn(true)
                                _isSuccessful.value = true
                            } else {
                                _errorMessage.value = task.exception?.message
                            }
                        }
                    } else {
                        userPreferences.setLoggedIn(true)
                        _isSuccessful.value = true
                    }
                } else {
                    _errorMessage.value = task.exception?.message
                }
            }
    }
}