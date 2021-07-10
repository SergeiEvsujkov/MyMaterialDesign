package com.example.mymaterialdesign.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.mymaterialdesign.MainActivity
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.databinding.FragmentThemeBinding
import kotlinx.android.synthetic.main.fragment_theme.*


class ThemeFragment : Fragment() {
    private var _binding: FragmentThemeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThemeBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (MainActivity.ThemeHolder.theme) {
            R.style.AppThemeTwo -> radioGroup.check(R.id.radio_button_green)
            R.style.AppTheme -> radioGroup.check(R.id.radio_button_blue)
        }
        radioGroup.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                R.id.radio_button_green -> {
                    MainActivity.ThemeHolder.theme = R.style.AppThemeTwo
                }
                R.id.radio_button_blue -> {
                    MainActivity.ThemeHolder.theme = R.style.AppTheme
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack()
                    requireActivity().recreate()
                }
            })
    }

    companion object {
        fun newInstance() =
            ThemeFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}