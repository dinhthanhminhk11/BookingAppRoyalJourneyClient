package com.github.dhaval2404.imagepicker.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import com.example.libraryimagepicker.R
import com.example.libraryimagepicker.constant.ImageProvider
import com.example.libraryimagepicker.listener.DismissListener
import com.example.libraryimagepicker.listener.ResultListener

/**
 * Show Dialog
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 04 January 2018
 */
internal object DialogHelper {

    /**
     * Show Image Provide Picker Dialog. This will streamline the code to pick/capture image
     *
     */
    fun showChooseAppDialog(
        context: Context,
        listener: ResultListener<ImageProvider>,
        dismissListener: DismissListener?
    ) {

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_choose_app)

        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        if (dialog.window != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialog.setCancelable(true)
//        val body = dialog.findViewById(R.id.body) as TextView
//        body.text = title
//        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//        val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//        yesBtn.setOnClickListener {
//            dialog.dismiss()
//        }
//        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.findViewById<View>(R.id.camera).setOnClickListener {
            listener.onResult(ImageProvider.CAMERA)
            dialog.dismiss()
        }

        // Handle Gallery option click
        dialog.findViewById<View>(R.id.image).setOnClickListener {
            listener.onResult(ImageProvider.GALLERY)
            dialog.dismiss()
        }

        dialog.findViewById<View>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

//        val layoutInflater = LayoutInflater.from(context)
//        val customView = layoutInflater.inflate(R.layout.dialog_choose_app, null)
//
//        val dialog = AlertDialog.Builder(context)
//            .setView(customView)
//            .setOnCancelListener {
//                listener.onResult(null)
//            }
//
//            .setOnDismissListener {
//                dismissListener?.onDismiss()
//            }
//            .show()
//
//        // Handle Camera option click

    }
}
