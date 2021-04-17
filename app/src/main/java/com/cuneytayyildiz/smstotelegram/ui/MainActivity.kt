package com.cuneytayyildiz.smstotelegram.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cuneytayyildiz.smstotelegram.data.local.AppPreferences
import com.cuneytayyildiz.smstotelegram.ui.into.IntroActivity
import com.cuneytayyildiz.smstotelegram.ui.preferences.PreferencesFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!appPreferences.isIntroShown()) {
            startActivity(Intent(this, IntroActivity::class.java))
        }

        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, PreferencesFragment())
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(Intent.ACTION_MAIN).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            addCategory(Intent.CATEGORY_HOME)
        }

        startActivity(intent)
    }
}