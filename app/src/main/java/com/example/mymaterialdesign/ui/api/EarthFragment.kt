package com.example.mymaterialdesign.ui.api

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import com.example.mymaterialdesign.databinding.FragmentEarthBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_earth.*


class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("ResourceType", "ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderData()
    }


    private fun renderData() {

        val url =
            "https://api.nasa.gov/planetary/earth/imagery?dim=0.1&lon=100.75&lat=1.5&date=2014-10-01&api_key=DEMO_KEY"
        Picasso
            .get()
            .load(url)
            .into(image_earth)


    }


    companion object {
        fun newInstance() =
            EarthFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}