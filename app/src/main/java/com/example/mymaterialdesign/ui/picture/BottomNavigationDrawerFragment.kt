package com.example.mymaterialdesign.ui.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mymaterialdesign.MainActivity
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.RecyclerActivity
import com.example.mymaterialdesign.databinding.BottomNavigationLayoutBinding
import com.example.mymaterialdesign.ui.AnimationsActivity

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import kotlinx.android.synthetic.main.main_fragment.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> {

                        startActivity(Intent(requireContext(), AnimationsActivity::class.java))


                    Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                }
                R.id.navigation_two -> {
                    startActivity(Intent(requireContext(), RecyclerActivity::class.java))
                    Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()

                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}