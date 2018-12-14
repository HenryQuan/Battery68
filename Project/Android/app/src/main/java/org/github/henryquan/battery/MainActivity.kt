package org.github.henryquan.battery

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import java.lang.Class.*
import android.os.Build

class MainActivity : AppCompatActivity() {

    val savedMah = mutableListOf<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val curr = getCurrentCapacity(this)
        val max = getDesignedCapacity()
        val percentage = String.format("%.2f", (curr / max * 100))
        capacityLabel.text = "${curr} / ${max} (${percentage}%)"
        currentLabel.text = "${curr}";

        val batteryStatus: Unit = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            this.registerReceiver(null, ifilter)
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Get device designed capacity and convert to a string
     * @return Unknown or Designed capacity
     */
    fun getDesignedCapacity(): Double {
        val POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile"
        try {
            val mPowerProfile_ = forName(POWER_PROFILE_CLASS).getConstructor(Context::class.java).newInstance(this)
            val batteryCapacity = forName(POWER_PROFILE_CLASS)
                .getMethod("getAveragePower", java.lang.String::class.java)
                .invoke(mPowerProfile_, "battery.capacity") as Double
            return batteryCapacity
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0.0
    }

    /**
     * Get current battery capacity
     * @return current capacity
     */
    fun getCurrentCapacity(context: Context): Double {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val chargeCounter = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER).toDouble()
        val percentage = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY).toDouble()
        return chargeCounter / 1000
    }

}
