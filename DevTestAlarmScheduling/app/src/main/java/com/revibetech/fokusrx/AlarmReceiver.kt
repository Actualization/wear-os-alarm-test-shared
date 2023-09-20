package com.revibetech.fokusrx

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.os.Vibrator
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.random.Random


class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private val TAG = AlarmReceiver::class.java.simpleName
        fun startAlert(context: Context) {
            try {
                val triggerAtMilliseconds = System.currentTimeMillis() + 30000
                val alarmIntent = Intent(context.applicationContext, AlarmReceiver::class.java)
                alarmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                alarmIntent.putExtra("SCHEDULED_ALARM_TIME", triggerAtMilliseconds.toString())
                val pendingIntent =
                    PendingIntent.getBroadcast(
                        context.applicationContext,
                        Random.nextInt(100000, 999999999),
                        alarmIntent,
                        PendingIntent.FLAG_IMMUTABLE or 0
                    )
                val alarmManager =
                    context.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

                alarmManager!!.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMilliseconds,
                    pendingIntent
                )

                Log.d(
                    "ALARM_STARTED",
                    "Current time in milliseconds: " + System.currentTimeMillis()
                )

                val simpleDateFormatEastUs = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
                val triggerAtDateTime = simpleDateFormatEastUs.format(triggerAtMilliseconds)
                Log.d(
                    "$TAG.startAlert()",
                    "AlarmScheduledForDateTime: $triggerAtDateTime"
                )
            } catch (e: Exception) {
                Log.d("ALARM_STARTED", "Exception: " + e.message)
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        var currentTime = System.currentTimeMillis()
        var scheduledAlarmTime = intent.getStringExtra("SCHEDULED_ALARM_TIME").toString().toLong()
        Log.d(TAG,currentTime.toString())
        Log.d("ALARM_RECEIVED", "Difference from Scheduled time in seconds: " + (currentTime-scheduledAlarmTime)/1000f)
        try {
            val vibrate =
                context.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val pattern = longArrayOf(100, 1000, 10)
            if (vibrate != null) {
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM) //key
                    .build()
                vibrate.vibrate(pattern, 2, audioAttributes)
            }

            val intentStartVisualActivity = Intent(context, AlarmFiringDisplayActivity::class.java)
            intentStartVisualActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intentStartVisualActivity)

            startAlert(context)
        } catch (e: Exception) {
            Log.d("ALARM_RECEIVED", "Exception: " + e.message)
        }
    }
}