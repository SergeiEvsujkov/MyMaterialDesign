package com.example.mymaterialdesign
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mymaterialdesign.databinding.MainActivityBinding
import com.example.mymaterialdesign.ui.main.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }

    }

}