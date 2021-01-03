package de.mwvb.blockpuzzle

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import de.mwvb.blockpuzzle.persistence.IPersistence
import de.mwvb.blockpuzzle.persistence.Persistence
import de.mwvb.blockpuzzle.sound.SoundService
import kotlinx.android.synthetic.main.activity_info.*
import java.lang.Class as Class

class InfoActivity : AppCompatActivity() {
    private val soundService = SoundService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        if (Build.VERSION.SDK_INT >= 21) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.navigationBackground);
        }

        soundService.init(this)

        val mode = if (intent.extras == null) 0 else intent.extras!!.getInt("mode")
        val activity: Class<*>
        activity = when (mode) {
            1 -> { // Milky Way alert
                infotext.setText(R.string.milkyWayAlertInfotext)
                MainActivity::class.java
            }
            2 -> { // back from Death Star
                infotext.setText(R.string.backFromDeathStar)
                BridgeActivity::class.java
            }
            else -> {
                BridgeActivity::class.java
            }
        }

        contBtn.setOnClickListener { startActivity(Intent(this, activity)) }
        muteBtn.setOnClickListener { soundService.alarm(false) }
    }

    override fun onResume() {
        super.onResume()
        try {
            soundService.alarm(true)
        } catch (e: Exception) {
            Toast.makeText(this, e.javaClass.toString() + ": " + e.message + "\n" + e.stackTrace[0].toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        soundService.alarm(false) // make sure that alarm is off
    }

    override fun onBackPressed() {
        // Wenn ich das hier nicht verhindern würde, würde die App zur Bridge gehen und der Alarm geht nie mehr aus.
    }

    private fun per(): IPersistence {
        return Persistence(this)
    }
}
