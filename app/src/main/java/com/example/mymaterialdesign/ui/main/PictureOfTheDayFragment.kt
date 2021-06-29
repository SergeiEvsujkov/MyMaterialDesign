package com.example.mymaterialdesign.ui.main


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.databinding.MainFragmentBinding
import com.example.mymaterialdesign.ui.picture.PictureOfTheDayData

import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.api.load
import com.example.mymaterialdesign.MainActivity
import com.example.mymaterialdesign.ui.picture.BottomNavigationDrawerFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_activity.*


import kotlinx.android.synthetic.main.main_fragment.*

class PictureOfTheDayFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("ResourceType", "ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })


        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }

        if (viewModel.isYesterday) {
            chipGroup.check(R.id.yesterday)
        } else
        if (viewModel.isYesterdayBefore) {
            chipGroup.check(R.id.yesterday_before)
        } else { chipGroup.check(R.id.today)}

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setBottomAppBar(view)

        chipGroup.setOnCheckedChangeListener { chipGroup, position ->

            chipGroup.findViewById<Chip>(position)?.let {
                when (position) {
                    R.id.yesterday_before -> {
                        viewModel.isYesterdayBefore = true
                        viewModel.isYesterday = false
                        viewModel.isToday = false
                    }
                    R.id.yesterday -> {
                        viewModel.isYesterdayBefore = false
                        viewModel.isYesterday = true
                        viewModel.isToday = false
                    }
                    R.id.today -> {
                        viewModel.isYesterdayBefore = false
                        viewModel.isYesterday = false
                        viewModel.isToday = true
                    }
                }
            }
            viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
        }
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")

                } else {
                    //showSuccess()
                    image_view.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                    bottom_sheet_description_header.text = serverResponseData.title
                    bottom_sheet_description.text = serverResponseData.explanation
                }
            }
            is PictureOfTheDayData.Loading -> {
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
                //showError(data.error.message)
                // toast(data.error.message)
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
//                    BottomSheetBehavior.STATE_DRAGGING -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_COLLAPSED -> TODO("not implemented")
                    //                   BottomSheetBehavior.STATE_EXPANDED -> TODO("not implemented")
                    //                   BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
                    //                   BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
                    //                  BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                TODO("not implemented")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_search -> Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)

        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    companion object {
        private var isMain = true
        fun newInstance() =
            PictureOfTheDayFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}