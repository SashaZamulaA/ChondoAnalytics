//package com.zaleksandr.aleksandr.myapplication.util
//
//import android.app.Dialog
//import android.os.Bundle
//import android.view.ViewGroup
//import androidx.annotation.NonNull
//import androidx.appcompat.app.AppCompatDialogFragment
//import com.google.android.material.snackbar.Snackbar
//import com.zaleksandr.aleksandr.myapplication.R
//import java.util.ArrayList
//import java.util.Collections
//import java.util.Date
//
//
//class CalendarDialogFragment : AppCompatDialogFragment() {
//
//    @NonNull
//    fun onCreateDialog(savedInstanceState: Bundle): Dialog {
//        val inflater = activity?.layoutInflater
//        val contentView = inflater?.inflate(R.layout.layout_dialog_calendar, view as ViewGroup, false)
//        val titleView = inflater?.inflate(R.layout.layout_dialog_title, view as ViewGroup, false)
//
//        val widget = contentView?.findViewById(R.currentUserId.calendarView) as MaterialCalendarView
//        widget.setSelectionMode(SELECTION_MODE_RANGE)
//        widget.setDynamicHeightEnabled(false)
//        widget.setTileHeight(widget.getTileHeight() / 2)//hack for landscape orientation
//        //Out of range decorator. It disables dates, which are greater than today.
//        widget.addDecorator(object : DayViewDecorator() {
//            fun shouldDecorate(day: CalendarDay): Boolean {
//                return day.getDate().getTime() > PeriodUtils.endDateFromToday
//            }
//
//            fun decorate(view: DayViewFacade) {
//                view.setDaysDisabled(true)
//            }
//        })
//
//        val alertDialog = AlertDialog.Builder(activity)
//                .setView(contentView)
//                .setPositiveButton(android.R.string.ok, null)
//                .setNegativeButton(android.R.string.no, { dialog, which ->
//                    Timber.v(Utils.formatBreadCrumb(TAG, "Negative button click"))
//                    dismissAllowingStateLoss()
//                })
//                .create()
//
//        alertDialog.setOnShowListener({ dialog ->
//            val button = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
//            button.setOnClickListener({ buttonView ->
//                Timber.v(Utils.formatBreadCrumb(TAG, "Positive button click"))
//                if (widget.getSelectedDates().size() < 1)
//                    Snackbar.make(contentView, resources.getString(R.string.select_range), Snackbar.LENGTH_SHORT).show()
//                else {
//                    val dates = ArrayList<Date>()
//                    for (calendarDay in widget.getSelectedDates())
//                        dates.add(calendarDay.getDate())
//                    Collections.sort(dates)
//                    val callingFragment = activity.getSupportFragmentManager().findFragmentById(CONTENT_LAYOUT)
//                    if (callingFragment != null && callingFragment is BasePeriodView)
//                        (callingFragment as BasePeriodView).onCustomPeriodSelected(dates)
//                    dismissAllowingStateLoss()
//                }
//            })
//        })
//        return alertDialog
//    }
//
//    companion object {
//
//
//        val TAG = "CalendarDialog"
//    }
//
//
//}