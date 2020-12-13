package com.nonamer777.madlevel7task1.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nonamer777.madlevel7task1.R
import com.nonamer777.madlevel7task1.databinding.FragmentProfileBinding
import com.nonamer777.madlevel7task1.model.ProfileViewModel

/**
 * A [Fragment] subclass that shows the created profile.
 */
class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProfile()
    }

    private fun observeProfile() {
        profileViewModel.getProfile()

        profileViewModel.profile.observe(viewLifecycleOwner, {
            val profile = it

            binding.labelName.text = getString(
                R.string.label_name,
                profile.firstName,
                profile.lastName
            )
            binding.paragraphDescription.text = profile.description

            if (profile.imageUri!!.isNotEmpty()) {
                binding.imgProfilePic.setImageURI(Uri.parse(profile.imageUri))
            }
        })
    }
}
