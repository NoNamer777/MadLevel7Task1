package com.nonamer777.madlevel7task1.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nonamer777.madlevel7task1.repository.ProfileRepository
import com.nonamer777.madlevel7task1.repository.exception.ProfileRetrievalError
import com.nonamer777.madlevel7task1.repository.exception.ProfileSaveError
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    private val TAG = "FIRESTORE"

    private val profileRepo = ProfileRepository()

    val profile: LiveData<Profile> = profileRepo.profile

    val createSuccess: LiveData<Boolean> = profileRepo.createSuccess

    private val _error = MutableLiveData<String>()

    val error: LiveData<String> get() = _error

    fun getProfile() = viewModelScope.launch {
        try { profileRepo.getProfile() } catch (exception: ProfileRetrievalError) {
            logAndSendError("Something went wrong while retrieving the profile", exception)
        }
    }

    fun createProfile(firstName: String, lastName: String, description: String, imageUri: String) {
        val profile = Profile(firstName, lastName, description, imageUri)

        viewModelScope.launch {
            try { profileRepo.createProfile(profile) } catch (exception: ProfileSaveError) {
                logAndSendError("Something went wrong while saving the profile", exception)
            }
        }
    }

    private fun logAndSendError(message: String, exception: Exception) {
        Log.e(TAG, exception.message ?: message)

        _error.value = message
    }
}
