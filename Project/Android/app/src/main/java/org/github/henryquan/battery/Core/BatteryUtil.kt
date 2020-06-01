package org.github.henryquan.battery.Core

import android.content.Context
import android.os.BatteryManager
import android.util.Log

/**
 * Battery Util to get battery statistics
 */
class BatteryUtil(val context: Context) {

    val batteryManager = this.context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    /**
     * Get device designed capacity and convert to a string
     * @return Unknown or Designed capacity
     */
    fun getDesignedCapacity(): Double {
        val POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile"
        try {
            val mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context::class.java).newInstance(this.context)
            val batteryCapacity = Class.forName(POWER_PROFILE_CLASS)
                .getMethod("getAveragePower", java.lang.String::class.java)
                .invoke(mPowerProfile_, "battery.capacity") as Double
            return batteryCapacity
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0.0
    }

    /**
     * @return current capacity
     */
    fun getCurrentCapacity(): Double {
        val chargeCounter = this.batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER).toDouble()
        return chargeCounter / 1000
    }

    /**
     * @return current percentage
     */
    fun getCurrentPercentage(): Double {
        val percentage = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY).toDouble()
        return percentage
    }

    /**
     * Current capacity / Designed capacity to get battery health
     * @return estimated health in string
     */
    fun getEstimatedBatteryHealth(): String {
        val percent = this.getCurrentPercentage()
        val curr = this.getCurrentCapacity()
        val estimate = String.format("%.2f",curr / (percent / 100))
        val max = this.getDesignedCapacity()
        val percentage = String.format("%.2f", (curr / percent / max * 10000))

        return "${curr} (${percent}%) - ${estimate} (100%)\nMax: ${max}\nEstimated: ${percentage}%"
    }

}