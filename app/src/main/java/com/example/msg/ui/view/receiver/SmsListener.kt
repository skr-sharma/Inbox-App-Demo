package com.example.msg.ui.view.receiver

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.msg.ui.view.main.MainActivity


class SmsListener : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras //---get the SMS message passed in---
            var msgs: Array<SmsMessage?>? = null
            var msg_from: String
            if (bundle != null) {
                try {
                    val pdus =
                        bundle["pdus"] as Array<*>?
                    msgs = arrayOfNulls(pdus!!.size)
                    for (i in msgs.indices) {
                        msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                        msg_from = msgs[i]?.originatingAddress!!
                        val msgBody: String = msgs[i]?.messageBody!!
                        Log.d("msg_from caught", msg_from)
                        Log.d("msgBody caught", msgBody)
                        generateMsgNotification(msgBody, msg_from, context)
                    }
                } catch (e: Exception) {
                    //Log.d("Exception caught", e.getMessage());
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateMsgNotification(
        msgBody: String,
        msgFrom: String,
        context: Context?
    ) {
        val intent = Intent(
            context?.applicationContext,
            MainActivity::class.java
        )
        val CHANNEL_ID = "MYCHANNEL"
        val notificationChannel =
            NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW)
        val pendingIntent = PendingIntent.getActivity(
            context?.applicationContext,
            1,
            intent,
            0
        )
        val notification = Notification.Builder(
            context?.applicationContext,
            CHANNEL_ID
        )
            .setContentText(msgBody)
            .setContentTitle(msgFrom)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.sym_action_chat, "Inbox-Demo-App", pendingIntent)
            .setChannelId(CHANNEL_ID)
            .setSmallIcon(R.drawable.sym_action_chat)
            .build()

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager!!.createNotificationChannel(notificationChannel)
        notificationManager.notify(1, notification)
    }
}