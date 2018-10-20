package org.github.henryquan.battery

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        capacityLabel.text = getBatteryCapacity()
    }

    fun getBatteryCapacity(): String {
        var mPowerProfile_: Any? = null

        val POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile"

        try {
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context::class.java).newInstance(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

            val batteryCapacity = Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String::class.java)
                    .invoke(mPowerProfile_, "battery.capacity") as Double
        return batteryCapacity.toString() + " mah"
    }

}
