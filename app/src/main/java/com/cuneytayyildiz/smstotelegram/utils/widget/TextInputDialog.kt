package com.cuneytayyildiz.smstotelegram.utils.widget

import android.content.Context
import android.content.DialogInterface
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.cuneytayyildiz.smstotelegram.R
import com.cuneytayyildiz.smstotelegram.utils.extensions.clipboardManager


/**
 * Created by Cuneyt AYYILDIZ on 4/17/2021.
 */
class TextInputDialog(
    private val context: Context,
    private val defaultValue: String? = null,
    private val builder: AlertDialog.Builder.() -> Unit
) {
    fun show(onPositiveButtonClick: (String) -> Unit) {
        val inputText = createInputText()

        val alertDialogBuilder = createAlertDialog(inputText, onPositiveButtonClick)

        alertDialogBuilder.show()
    }

    private fun createAlertDialog(
        inputText: EditText,
        onPositiveButtonClick: (String) -> Unit
    ): AlertDialog {
        return AlertDialog.Builder(context).apply {
            builder()
            setView(inputText)
            setPositiveButton(android.R.string.ok) { dialog, _ ->
                val enteredInputText = inputText.text.toString()
                onPositiveButtonClick.invoke(enteredInputText)
                dialog.dismiss()
            }
            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            setNeutralButton(R.string.input_dialog_button_paste_from_clipboard, null)
        }.create().apply {
            setOnShowListener { dialogInterface ->
                initPositiveButtonCallback(dialogInterface, inputText)

                initNeutralButtonCallback(dialogInterface, inputText)
            }
        }
    }

    private fun getDialogButton(dialogInterface: DialogInterface?, buttonId: Int): Button? {
        return (dialogInterface as? AlertDialog)?.getButton(buttonId)
    }

    private fun initNeutralButtonCallback(
        dialogInterface: DialogInterface?,
        inputText: EditText
    ) {
        getDialogButton(dialogInterface = dialogInterface, buttonId = AlertDialog.BUTTON_NEUTRAL)
            ?.let { neutralButton ->
                neutralButton.setOnClickListener {
                    val clipboardText =
                        context.clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
                    inputText.setText(clipboardText)
                }
            }
    }

    private fun initPositiveButtonCallback(
        dialogInterface: DialogInterface?,
        inputText: EditText
    ) {
        getDialogButton(dialogInterface = dialogInterface, buttonId = AlertDialog.BUTTON_POSITIVE)
            ?.let { positiveButton ->
                positiveButton.isEnabled = false

                inputText.addTextChangedListener {
                    positiveButton.isEnabled = !it.isNullOrEmpty()
                }
            }
    }

    private fun createInputText(): EditText {
        return EditText(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setText(defaultValue)
        }
    }

}