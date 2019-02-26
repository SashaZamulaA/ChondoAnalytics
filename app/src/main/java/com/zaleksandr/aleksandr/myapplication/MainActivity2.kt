//package com.zaleksandr.aleksandr.myapplication
//
//import android.app.*
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.DatePicker
//import android.widget.TextView
//import java.util.*
//
//import java.util.Calendar
//
//import android.app.Activity
//import android.app.AlertDialog
//import android.app.DatePickerDialog
//import android.app.Dialog
//import android.app.DialogFragment
//
//
//class MainActivity2 : Activity() {
//
//    internal var button1: Button? = null
//    internal var button2: Button? = null
//    internal var button3: Button? = null
//    internal var button4: Button? = null
//    internal var button5: Button? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        button1 = findViewById<Button>(R.currentUserId.button1)
//        button2 = findViewById<Button>(R.currentUserId.button2)
//        button3 = findViewById<Button>(R.currentUserId.button3)
//        button4 = findViewById<Button>(R.currentUserId.button4)
//        button5 = findViewById<Button>(R.currentUserId.button5)
//
//        button1.setOnClickListener {
//            // TODO Auto-generated method stub
//
//            val dialogfragment = DatePickerDialogTheme1()
//
//            dialogfragment.show(fragmentManager, "Theme 1")
//        }
//
//        button2.setOnClickListener {
//            // TODO Auto-generated method stub
//
//            val dialogfragment = DatePickerDialogTheme2()
//
//            dialogfragment.show(fragmentManager, "Theme 2")
//        }
//
//        button3.setOnClickListener {
//            // TODO Auto-generated method stub
//
//
//            val dialogfragment = DatePickerDialogTheme3()
//
//            dialogfragment.show(fragmentManager, "Theme 3")
//        }
//
//        button4.setOnClickListener {
//            // TODO Auto-generated method stub
//
//
//            val dialogfragment = DatePickerDialogTheme4()
//
//            dialogfragment.show(fragmentManager, "Theme 4")
//        }
//
//        button5.setOnClickListener {
//            // TODO Auto-generated method stub
//
//            val dialogfragment = DatePickerDialogTheme5()
//
//            dialogfragment.show(fragmentManager, "Theme 5")
//        }
//
//    }
//
//    class DatePickerDialogTheme1 : DialogFragment(), DatePickerDialog.OnDateSetListener {
//
//        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
//            val calendar = Calendar.getInstance()
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            return DatePickerDialog(activity,
//                    AlertDialog.THEME_DEVICE_DEFAULT_DARK, this, year, month, day)
//        }
//
//        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
//
//            val textview = activity.findViewById(R.currentUserId.textView1) as TextView
//
//            textview.text = day.toString() + ":" + (month + 1) + ":" + year
//
//        }
//    }
//
//    class DatePickerDialogTheme2 : DialogFragment(), DatePickerDialog.OnDateSetListener {
//
//        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
//            val calendar = Calendar.getInstance()
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            return DatePickerDialog(activity,
//                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day)
//        }
//
//        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
//
//            val textview = activity.findViewById(R.currentUserId.textView1) as TextView
//
//            textview.text = day.toString() + ":" + (month + 1) + ":" + year
//
//        }
//    }
//
//    class DatePickerDialogTheme3 : DialogFragment(), DatePickerDialog.OnDateSetListener {
//
//        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
//            val calendar = Calendar.getInstance()
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            return DatePickerDialog(activity,
//                    AlertDialog.THEME_HOLO_DARK, this, year, month, day)
//        }
//
//        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
//
//            val textview = activity.findViewById(R.currentUserId.textView1) as TextView
//
//            textview.text = day.toString() + ":" + (month + 1) + ":" + year
//
//        }
//    }
//
//    class DatePickerDialogTheme4 : DialogFragment(), DatePickerDialog.OnDateSetListener {
//
//        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
//            val calendar = Calendar.getInstance()
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            return DatePickerDialog(activity,
//                    AlertDialog.THEME_HOLO_LIGHT, this, year, month, day)
//        }
//
//        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
//
//            val textview = activity.findViewById(R.currentUserId.textView1) as TextView
//
//            textview.text = day.toString() + ":" + (month + 1) + ":" + year
//
//        }
//    }
//
//    class DatePickerDialogTheme5 : DialogFragment(), DatePickerDialog.OnDateSetListener {
//
//        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
//            val calendar = Calendar.getInstance()
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            return DatePickerDialog(activity,
//                    AlertDialog.THEME_TRADITIONAL, this, year, month, day)
//        }
//
//        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
//
//            val textview = activity.findViewById(R.currentUserId.textView1) as TextView
//
//            textview.text = day.toString() + ":" + (month + 1) + ":" + year
//
//        }
//    }
//
//}
