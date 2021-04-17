package com.cuneytayyildiz.smstotelegram.ui.preferences

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.cuneytayyildiz.smstotelegram.R
import com.cuneytayyildiz.smstotelegram.data.local.AppPreferences
import com.cuneytayyildiz.smstotelegram.utils.managers.PermissionsManager
import com.cuneytayyildiz.smstotelegram.utils.widget.TextInputDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by Cuneyt AYYILDIZ on 4/17/2021.
 */

private const val HELP_URL: String = "https://core.telegram.org/bots#6-botfather"

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var appPreferences: AppPreferences

    private val permissionsManager: PermissionsManager by lazy {
        PermissionsManager(context = requireActivity())
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<Preference>("preference_token_id")?.setOnPreferenceClickListener {
            TextInputDialog(
                context = requireActivity(),
                defaultValue = appPreferences.getTokenId()
            ) {
                setTitle(R.string.onboarder_page_token_id_title)
            }.show { enteredInputText ->
                appPreferences.setTokenId(token = enteredInputText)
            }
            false
        }

        findPreference<Preference>("preference_chat_id")?.setOnPreferenceClickListener {
            TextInputDialog(
                context = requireActivity(),
                defaultValue = appPreferences.getChatId()
            ) {
                setTitle(R.string.preference_chat_id_title)
            }.show { enteredInputText ->
                appPreferences.setChatId(chatId = enteredInputText)
            }
            false
        }

        findPreference<Preference>("preference_help")?.setOnPreferenceClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(HELP_URL)
            })
            false
        }

        findPreference<Preference>("preference_grant_permissions")?.setOnPreferenceClickListener { preference ->
            permissionsManager.requestPermissions { areAllPermissionsGranted ->
                preference.isEnabled = areAllPermissionsGranted.not()

                if (!preference.isEnabled) {
                    preference.summary =
                        requireActivity().getString(R.string.preference_category_permissions_given)
                }
            }
            false
        }

        if (appPreferences.isIntroShown()) {
            permissionsManager.requestPermissions { areAllPermissionsGranted ->
                findPreference<Preference>("preference_grant_permissions")?.let { preference ->
                    preference.isEnabled = areAllPermissionsGranted.not()

                    if (!preference.isEnabled) {
                        preference.summary =
                            requireActivity().getString(R.string.preference_category_permissions_given)
                    }
                }
            }
        }
    }
}