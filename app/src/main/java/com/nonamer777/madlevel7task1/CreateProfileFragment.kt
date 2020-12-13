package com.nonamer777.madlevel7task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nonamer777.madlevel7task1.databinding.FragmentCreateProfileBinding

/**
 * A [Fragment] subclass where Users can create a profile.
 */
class CreateProfileFragment: Fragment() {

    private lateinit var binding: FragmentCreateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProfileBinding.inflate(layoutInflater)

        return binding.root
    }
}
