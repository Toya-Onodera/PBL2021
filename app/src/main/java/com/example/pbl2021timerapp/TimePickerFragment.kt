package com.example.pbl2021timerapp

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(activity, this, hour, minute, false)
    }

    // Fragment から Activity 値を渡すには呼び出し元のメソッドを直接呼ぶ必要がある
    // https://www.ipentec.com/document/android-custom-dialog-using-dialogfragment-return-value
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val callingActivity = activity as SetTimeActivity
        callingActivity.onReturnChooseTime(hourOfDay, minute)
    }
}