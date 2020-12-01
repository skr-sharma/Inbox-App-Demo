package com.example.msg.ui.view.inbox

import android.content.ContentResolver
import android.database.Cursor
import android.provider.Telephony
import android.text.format.DateFormat
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.example.msg.base.BaseViewModel
import com.example.msg.model.InboxMsgModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class InboxMsgViewModel : BaseViewModel() {
    var inboxMsgList = MutableLiveData<ArrayList<InboxMsgModel>>()

    fun getInboxMessages(mActivity: FragmentActivity?) {
        val lstSms = ArrayList<InboxMsgModel>()
        val cr: ContentResolver? = mActivity?.contentResolver
        cr?.let {
            val cursor: Cursor? = it.query(
                Telephony.Sms.CONTENT_URI, null, null,
                null, null
            )
            if (cursor != null) {
                val totalSMS = cursor.count
                if (cursor.moveToFirst()) {
                    for (j in 0 until totalSMS) {
                        val objInboxMsg = InboxMsgModel()
                        val displayId =
                            cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.THREAD_ID))
                        val smsDate =
                            cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                        val number =
                            cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                        val body =
                            cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                        when (cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE))
                            .toInt()) {
                            Telephony.Sms.MESSAGE_TYPE_INBOX -> {
                                objInboxMsg._id = displayId
                                objInboxMsg._address = number
                                objInboxMsg._msg = body
                                getDateFromTimeStamp(smsDate, lstSms, objInboxMsg)
                            }
                            Telephony.Sms.MESSAGE_TYPE_SENT -> {
                            }
                        }
                        cursor.moveToNext()
                    }
                }
                cursor.close()
            } else {
                Toast.makeText(mActivity, "No message to show!", Toast.LENGTH_SHORT).show()
            }
        }
        Collections.sort(lstSms, TimeComparator())
        inboxMsgList.postValue(lstSms)
    }

    private fun getDateFromTimeStamp(
        time: String,
        lstSms: ArrayList<InboxMsgModel>,
        objInboxMsg: InboxMsgModel
    ): MutableList<InboxMsgModel> {
        val timestamp = time.toLong()
        val msgDate: String = DateFormat.format("EEE, d MMM yyyy, HH:mm a", timestamp).toString()

        val sdf = SimpleDateFormat("EEE, d MMM yyyy, HH:mm a", Locale.getDefault())
        val currentDateAndTime = sdf.format(Date())

        val date1: Date? = sdf.parse(msgDate)
        val date2: Date? = sdf.parse(currentDateAndTime)
        var different: Long = date2?.time!! - date1?.time!!


        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays = different / daysInMilli
        different %= daysInMilli

        val elapsedHours = different / hoursInMilli
        different %= hoursInMilli

        val elapsedMinutes = different / minutesInMilli
        different %= minutesInMilli

        val elapsedSeconds = different / secondsInMilli

        System.out.printf(
            "%d days, %d hours, %d minutes, %d seconds%n",
            elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds
        )
        if (elapsedDays <= 1) {
            objInboxMsg._time = elapsedHours.toString()
            lstSms.add(objInboxMsg)
        }
        return lstSms
    }

    class TimeComparator : Comparator<InboxMsgModel?> {
        override fun compare(o1: InboxMsgModel?, o2: InboxMsgModel?): Int {
            return o1?._time?.toInt()?.compareTo(o2?._time?.toInt()!!)!!
        }
    }
}