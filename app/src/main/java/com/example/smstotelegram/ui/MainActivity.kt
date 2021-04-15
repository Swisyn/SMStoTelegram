package com.example.smstotelegram.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.smstotelegram.R
import com.example.smstotelegram.data.local.AppPreferences
import com.example.smstotelegram.databinding.ActivityMainBinding
import com.example.smstotelegram.ui.into.IntroActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!appPreferences.isIntroShown()) {
            startActivity(Intent(this, IntroActivity::class.java))
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonSaveSettings.setOnClickListener {
            appPreferences.setChatId(chatId = binding.editTextChatId.text.toString())
            appPreferences.setTokenId(token = binding.editTextBotId.text.toString())
        }

        requestPermissions()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(Intent.ACTION_MAIN).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            addCategory(Intent.CATEGORY_HOME)
        }

        startActivity(intent)
    }

    private fun requestPermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.RECEIVE_MMS,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.PROCESS_OUTGOING_CALLS
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(permissionsReport: MultiplePermissionsReport?) {
                    permissionsReport?.let { report ->

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle(R.string.general_permission_rationale_title)
                        .setMessage(R.string.general_permission_rationale_message)
                        .setNegativeButton(android.R.string.cancel) { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            token?.cancelPermissionRequest()
                        }
                        .setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            token?.continuePermissionRequest()
                        }
                        .show()
                }
            }).check()
    }
}