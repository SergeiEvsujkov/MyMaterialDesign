package com.example.mymaterialdesign


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import com.example.mymaterialdesign.databinding.MainActivityBinding
import com.example.mymaterialdesign.ui.main.PictureOfTheDayFragment

const val THEME_PREFERENCE = "Theme"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var themePreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        themePreference = getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
        if (themePreference.contains(THEME_PREFERENCE)) {
            ThemeHolder.theme = (themePreference.getInt(THEME_PREFERENCE, R.style.AppTheme));
        }

        setTheme(ThemeHolder.theme)
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }

    }

    override fun onStop() {
        super.onStop()
        val editor: SharedPreferences.Editor = themePreference.edit()
        editor.putInt(THEME_PREFERENCE, ThemeHolder.theme)
        editor.apply()
    }

    object ThemeHolder {
        @StyleRes
        var theme: Int = R.style.AppTheme
    }

    override fun onBackPressed() {
        super.onBackPressed()
        recreate()
    }

}