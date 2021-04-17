package com.cuneytayyildiz.smstotelegram.utils.managers

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.cuneytayyildiz.smstotelegram.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


/**
 * Created by Cuneyt AYYILDIZ on 4/17/2021.
 */
class PermissionsManager(private val context: Context) {

      fun requestPermissions(areAllPermissionsGranted: (Boolean) -> Unit) {
        Dexter.withContext(context)
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
                        areAllPermissionsGranted.invoke(report.areAllPermissionsGranted())
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    AlertDialog.Builder(context)
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