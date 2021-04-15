package com.example.smstotelegram.ui.into

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cuneytayyildiz.onboarder.OnboarderActivity
import com.cuneytayyildiz.onboarder.model.*
import com.cuneytayyildiz.onboarder.utils.OnboarderPageChangeListener
import com.cuneytayyildiz.onboarder.utils.color
import com.example.smstotelegram.R


/**
 * Created by [Dzhunet Hasan] on [15 Apr, 2021]. | [KOBIL Systems GmbH] | [dzhunet.hasan@kobil.com]
 */
class IntroActivity : OnboarderActivity(), OnboarderPageChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnboarderPageChangeListener(this)

        val pages: MutableList<OnboarderPage> = createOnboarderPages()

        initOnboardingPages(pages)
    }

    override fun onFinishButtonPressed() {
        TODO("Not yet implemented")
    }

    override fun onPageChanged(position: Int) {
        TODO("Not yet implemented")
    }


    private fun createOnboarderPages(): MutableList<OnboarderPage> {
        return mutableListOf(
            onboarderPage {
                // backgroundColor = color(R.color.color_donut)

                image {
                    imageResId = R.drawable.ic_baseline_api_96
                }

                title {
                    text = "Set Your Token ID"
                    //   textColor = color(R.color.primary_text)
                }

                description {
                    text =
                        "You should enter your Token ID in order to receive SMS or incoming call notifications from your bot."
                    //  textColor = color(R.color.secondary_text)
                    multilineCentered = true
                }

                miscellaneousButton {
                    text = "How to find Token ID?"
                    clickListener = View.OnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data =
                                Uri.parse("https://www.siteguarding.com/en/how-to-get-telegram-bot-api-token")
                        })
                    }
                }
            },
            onboarderPage {
                //   backgroundColor = color(R.color.color_donut)

                image {
                    imageResId = R.drawable.ic_baseline_mark_chat_id_96
                }

                title {
                    text = "Set Your Chat ID"
                    //  textColor = color(R.color.primary_text)
                }

                description {
                    text =
                        "You should enter your Chat ID in order to receive SMS or incoming call notifications from your bot."
                    //  textColor = color(R.color.secondary_text)
                    multilineCentered = true
                }

                miscellaneousButton {
                    text = "How to find Chat ID?"
                    clickListener = View.OnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data =
                                Uri.parse("https://techblog.sillifish.co.uk/2020/03/30/telegram-chat-id-and-token-id/")
                        })
                    }
                }
            },
            onboarderPage {
                // backgroundColor = color(R.color.color_donut)

                image {
                    imageResId = R.drawable.ic_baseline_permission_96
                }

                title {
                    text = getString(R.string.general_permission_rationale_title)
                }

                description {
                    text = getString(R.string.general_permission_rationale_message)
                    multilineCentered = true
                }

                miscellaneousButton {
                    text = "How to find Token ID?"
                    clickListener = View.OnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data =
                                Uri.parse("https://www.siteguarding.com/en/how-to-get-telegram-bot-api-token")
                        })
                    }
                }
            }
        )
    }
}