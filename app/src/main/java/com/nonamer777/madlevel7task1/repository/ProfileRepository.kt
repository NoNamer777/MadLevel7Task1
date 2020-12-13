package com.nonamer777.madlevel7task1.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nonamer777.madlevel7task1.model.Profile
import com.nonamer777.madlevel7task1.repository.exception.ProfileRetrievalError
import com.nonamer777.madlevel7task1.repository.exception.ProfileSaveError
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class ProfileRepository {

    private var firestore = FirebaseFirestore.getInstance()

    private var profileDocument = firestore
        .collection("profiles")
        .document("profile")

    private val _profile = MutableLiveData<Profile>()

    val profile: LiveData<Profile> get() = _profile

    private val _createSuccess = MutableLiveData<Boolean>()

    val createSuccess: LiveData<Boolean> get() = _createSuccess

    suspend fun getProfile() = try { withTimeout(5_000) {
        val data = profileDocument.get().await()

        val firstName = data.getString("firstName").toString()
        val lastName = data.getString("lastName").toString()
        val description = data.getString("description").toString()
        val imageUri = data.getString("imageUri").toString()

        _profile.value = Profile(
            firstName,
            lastName,
            description,
            imageUri
        )

    }} catch (exception: Exception) {
        throw ProfileRetrievalError("Retrieval-firebase-task has failed")
    }

    suspend fun createProfile(profile: Profile) = try { withTimeout(5_000) {
       profileDocument.set(profile).await()

        _createSuccess.value = true

    }} catch (exception: Exception) {
        throw ProfileSaveError(exception.message.toString(), exception)
    }
}
