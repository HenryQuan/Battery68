package com.yihengquan.battery68.core

import android.content.Context
import android.os.BatteryManager

class BatteryHelper(private val context: Context) {
    private val batteryManager = this.context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    /**
     * Get device designed capacity and convert to a string
     * @return Unknown or Designed capacity
     */
    fun getDesignedCapacity(): Double {
        val powerProfile = "com.android.internal.os.PowerProfile"
        try {
            val mPowerProfile = Class.forName(powerProfile).getConstructor(Context::class.java).newInstance(this.context)
            return Class.forName(powerProfile)
                .getMethod("getAveragePower", java.lang.String::class.java)
                .invoke(mPowerProfile, "battery.capacity") as Double
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
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY).toDouble()
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