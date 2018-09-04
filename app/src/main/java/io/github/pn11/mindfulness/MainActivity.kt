package io.github.pn11.mindfulness

import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    private lateinit var ringtone: Ringtone

    private val handler = Handler()
    private var timeValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ringtone = RingtoneManager.getRingtone(this.applicationContext, ringtoneUri)

        val textTimer: TextView = findViewById(R.id.textTimer)
        val buttonStart: Button = findViewById(R.id.buttonStart)
        val buttonStop: Button = findViewById(R.id.buttonStop)

        val runnable = object : Runnable {
            override fun run() {
                timeValue++
                timeToText(timeValue)?.let {
                    textTimer.text = it
                }
                handler.postDelayed(this, 1000)
            }
        }

        // start
        buttonStart.setOnClickListener {
            handler.post(runnable)
        }

        // stop
        buttonStop.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            timeToText()?.let {
                textTimer.text = it
            }
        }
    }


    private fun timeToText(time: Int = 0): String? {
        return when {
            time < 0 -> null
            time == 0 -> "15:00"
            else -> {
                val remainedTime = 15 * 60 - time
                //  val h = remainedTime / 3600
                val m = remainedTime % 3600 / 60
                val s = remainedTime % 60
                when (remainedTime) {
                    0 -> this.ringtone.play()
                }
                when {
                    remainedTime >= 0 -> "%1$02d:%2$02d".format(m, s)
                    else -> {
                        "00:00"
                    }
                }

            }
        }
    }
}
