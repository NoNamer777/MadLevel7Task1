package com.nonamer777.madlevel7task1.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nonamer777.madlevel7task1.R
import com.nonamer777.madlevel7task1.databinding.FragmentCreateProfileBinding
import com.nonamer777.madlevel7task1.model.Profile
import com.nonamer777.madlevel7task1.model.ProfileViewModel

/**
 * A [Fragment] subclass where Users can create a profile.
 */
class CreateProfileFragment: Fragment() {

    companion object {

        const val GALLERY_REQUEST_CODE = 100
    }

    private lateinit var binding: FragmentCreateProfileBinding

    private var profileImageUri: Uri? = null

    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    profileImageUri = data?.data

                    binding.imgProfilePicture.setImageURI(profileImageUri)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOpenGallery.setOnClickListener { onOpenGallery() }
        binding.btnConfirm.setOnClickListener { onConfirm() }
    }

    private fun onOpenGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)

        galleryIntent.type = "image/*"

        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    private fun onConfirm() {
        profileViewModel.createProfile(
            binding.inputFirstName.text.ifNullOrEmpty(""),
            binding.inputLastName.text.ifNullOrEmpty(""),
            binding.inputDescription.text.ifNullOrEmpty(""),
            profileImageUri.ifNullOrEmpty(),
        )

        observeProfileCreation()

        findNavController().navigate(R.id.profileFragment)
    }

    private fun CharSequence?.ifNullOrEmpty(default: String) =
        if (this.isNullOrEmpty()) default else this.toString()

    private fun Uri?.ifNullOrEmpty() = this?.toString() ?: ""

    private fun observeProfileCreation() {
        profileViewModel.createSuccess.observe(viewLifecycleOwner, {
            Toast
                .makeText(
                    activity,
                    R.string.notification_profile_created_success,
                    Toast.LENGTH_LONG
                )
                .show()

            findNavController().popBackStack()
        })

        profileViewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
    }
}
