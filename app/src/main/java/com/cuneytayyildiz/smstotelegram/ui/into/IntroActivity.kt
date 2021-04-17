package com.cuneytayyildiz.smstotelegram.ui.into

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.cuneytayyildiz.onboarder.OnboarderActivity
import com.cuneytayyildiz.onboarder.model.*
import com.cuneytayyildiz.onboarder.utils.OnboarderPageChangeListener
import com.cuneytayyildiz.onboarder.utils.color
import com.cuneytayyildiz.smstotelegram.R
import com.cuneytayyildiz.smstotelegram.data.local.AppPreferences
import com.cuneytayyildiz.smstotelegram.utils.managers.PermissionsManager
import com.cuneytayyildiz.smstotelegram.utils.widget.TextInputDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by [Dzhunet Hasan] on [15 Apr, 2021]. | [KOBIL Systems GmbH] | [dzhunet.hasan@kobil.com]
 */
@AndroidEntryPoint
class IntroActivity : OnboarderActivity(), OnboarderPageChangeListener {

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnboarderPageChangeListener(this)

        val pages: MutableList<OnboarderPage> = createOnboarderPages()

        initOnboardingPages(pages)
    }

    override fun onFinishButtonPressed() {
        appPreferences.setIntroShown()
        finish()
    }

    override fun onPageChanged(position: Int) {}

    private fun createOnboarderPages(): MutableList<OnboarderPage> {
        return mutableListOf(
            createOnboarderPage(
                icon = R.drawable.ic_baseline_api_96dp,
                title = R.string.onboarder_page_token_id_title,
                description = R.string.onboarder_page_token_id_description,
                buttonText = R.string.onboarder_page_token_id_button_text
            ) {
                TextInputDialog(context = this@IntroActivity) {
                    setTitle(R.string.onboarder_page_token_id_title)
                }.show { enteredInputText ->
                    appPreferences.setTokenId(token = enteredInputText)
                    setPage(position = 1)
                }
            },
            createOnboarderPage(
                icon = R.drawable.ic_baseline_mark_chat_id_96dp,
                title = R.string.onboarder_page_chat_id_title,
                description = R.string.onboarder_page_chat_id_description,
                buttonText = R.string.onboarder_page_chat_id_button_text
            ) {
                TextInputDialog(context = this@IntroActivity) {
                    setTitle(R.string.onboarder_page_chat_id_title)
                }.show { enteredInputText ->
                    appPreferences.setChatId(chatId = enteredInputText)
                    setPage(position = 2)
                }
            },
            createOnboarderPage(
                icon = R.drawable.ic_baseline_permission_white_96dp,
                title = R.string.general_permission_rationale_title,
                description = R.string.general_permission_rationale_message,
                buttonText = R.string.general_permissions_grant_button_text
            ) { buttonView ->
                PermissionsManager(context = this).requestPermissions { areAllPermissionsGranted ->
                    buttonView.isEnabled = areAllPermissionsGranted.not()
                }
            }
        )
    }

    private fun createOnboarderPage(
        @DrawableRes icon: Int,
        @StringRes title: Int,
        @StringRes description: Int,
        @StringRes buttonText: Int,
        buttonClickListener: View.OnClickListener
    ) = onboarderPage {
        backgroundColor = color(R.color.color_primary_dark)

        image {
            imageResId = icon
        }

        title {
            text = getString(title)
        }

        description {
            text =
                getString(description)

            multilineCentered = true
        }

        miscellaneousButton {
            backgroundColor = color(R.color.color_accent)
            visibility = View.VISIBLE
            text = getString(buttonText)
            clickListener = buttonClickListener
        }
    }

}