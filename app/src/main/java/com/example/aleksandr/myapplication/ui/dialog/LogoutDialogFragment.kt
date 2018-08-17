//package com.example.aleksandr.myapplication.ui.dialog
//
//import android.app.Dialog
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.support.v7.app.AlertDialog
//import android.support.v7.app.AppCompatDialogFragment
//
//import com.loyverse.dashboard.R
//import com.loyverse.dashboard.base.Utils
//import com.loyverse.dashboard.base.mvp.SettingsView
//
//import timber.log.Timber
//
//import com.loyverse.dashboard.core.Navigator.CONTENT_LAYOUT
//
//class LogoutDialogFragment : AppCompatDialogFragment() {
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder = AlertDialog.Builder(activity!!)
//        builder.setMessage(resources.getString(R.string.logout_dialog_msg))
//        builder.setPositiveButton(R.string.logout) { dialog, which ->
//            Timber.v(Utils.formatBreadCrumb(TAG, "Positive button click"))
//            val callingFragment = activity!!.supportFragmentManager.findFragmentById(CONTENT_LAYOUT)
//            if (callingFragment != null && callingFragment is SettingsView)
//                (callingFragment as SettingsView).onLogoutConfirmation()
//            dismissAllowingStateLoss()
//        }
//        builder.setNegativeButton(android.R.string.no) { dialog, which ->
//            Timber.v(Utils.formatBreadCrumb(TAG, "Negative button click"))
//            dismissAllowingStateLoss()
//        }
//        return builder.create()
//    }
//
//    companion object {
//
//        val TAG = "LogoutDialog"
//    }
//}
