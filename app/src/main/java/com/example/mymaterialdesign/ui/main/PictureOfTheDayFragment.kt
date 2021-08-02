package com.example.mymaterialdesign.ui.main


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.*
import android.view.View.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import com.example.mymaterialdesign.MainActivity
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.databinding.MainFragmentStartBinding
import com.example.mymaterialdesign.ui.api.ApiActivity
import com.example.mymaterialdesign.ui.picture.BottomNavigationDrawerFragment
import com.example.mymaterialdesign.ui.picture.PictureOfTheDayData
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*

class PictureOfTheDayFragment : Fragment() {

    private var isExpanded = false

    private var isRed: Boolean = false

    private var _binding: MainFragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentStartBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("ResourceType", "ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            bottom_sheet_description.typeface =
                Typeface.createFromAsset(it.assets, "AndroidInsomniaRegularRLxW.ttf")
        }
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })


        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }

        image_view.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                container_view, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )

            val params: ViewGroup.LayoutParams = image_view.layoutParams
            params.width =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else R.dimen.image
            image_view.layoutParams = params

            image_view.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }




        if (viewModel.isYesterday) {
            chip_group.check(R.id.yesterday)
        } else
            if (viewModel.isYesterdayBefore) {
                chip_group.check(R.id.yesterday_before)
            } else {
                chip_group.check(R.id.today)
            }

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setBottomAppBar(view)

        chip_group.setOnCheckedChangeListener { chipGroup, position ->

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
        bottom_sheet_description.setOnClickListener {
            spanTextO()
        }

    }

    private fun spanTextO() {
        val spannable = SpannableStringBuilder(bottom_sheet_description.text)

        if (!isRed) {
            spannable.setSpan(
                ForegroundColorSpan(Color.RED),
                0, spannable.length / 2,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable.setSpan(
                ForegroundColorSpan(Color.BLACK),
                (spannable.length / 2), spannable.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            isRed = true
        } else {
            spannable.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, spannable.length / 2,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable.setSpan(
                ForegroundColorSpan(Color.RED),
                spannable.length / 2, spannable.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            isRed = false
        }
        bottom_sheet_description.text = spannable
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

                    if (serverResponseData.mediaType.equals("video")) {


                        val builder = AlertDialog.Builder(
                            requireContext()
                        )

                        builder.setTitle("Видео откроется в другом приложении")
                            .setCancelable(true)
                            .setNegativeButton(
                                R.string.no
                            ) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .setPositiveButton(
                                R.string.yes
                            ) { _, _ ->
                                startActivity(Intent(Intent.ACTION_VIEW).apply {
                                    this.data = Uri.parse(serverResponseData.url)
                                })
                            }

                        val alert = builder.create()
                        alert.show()
                    }
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
            R.id.app_bar_telescope -> activity?.let {
                startActivity(
                    Intent(
                        it,
                        ApiActivity::class.java
                    )
                )
            }
            R.id.app_bar_fav -> Toast.makeText(context, "Favorite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_search -> Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()


            R.id.app_bar_settings -> parentFragmentManager.beginTransaction()
                .replace(R.id.container, ThemeFragment.newInstance())
                .addToBackStack("")
                .commit()

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