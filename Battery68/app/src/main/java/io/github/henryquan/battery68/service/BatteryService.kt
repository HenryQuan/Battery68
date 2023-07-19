package io.github.henryquan.battery68.service

import android.annotation.SuppressLint
import android.content.Context
import android.os.BatteryManager

class BatteryService(private val context: Context) {
    private val batteryManager =
        this.context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    /**
     * Get device designed capacity and convert to a string
     * @return Unknown or Designed capacity
     */
    @SuppressLint("PrivateApi")
    fun getDesignedCapacity(): Double? {
        val powerProfile = "com.android.internal.os.PowerProfile"
        return try {
            val mPowerProfile = Class.forName(powerProfile)
                .getConstructor(Context::class.java)
                .newInstance(this.context)
            val rawCapacity1 = Class.forName(powerProfile)
                .getMethod("getAveragePower", java.lang.String::class.java)
                .invoke(mPowerProfile, "battery.capacity") as Double
            val rawCapacity2 = Class.forName(powerProfile)
                .getMethod("getBatteryCapacity")
                .invoke(mPowerProfile) as Double

            // return whichever larger and more than 1000
            listOf(rawCapacity1, rawCapacity2).maxOrNull()?.takeIf { it > 1000 } ?: return null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * @return current capacity
     */
    fun getCurrentCapacity(): Double {
        return batteryManager
            .getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
            .toDouble() / 1000
    }

    /**
     * @return current percentage
     */
    fun getCurrentPercentage(): Double {
        return batteryManager
            .getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            .toDouble()
    }

    /**
     * Current capacity / Designed capacity to get battery health
     * @return estimated health in string
     */
    fun getEstimatedBatteryHealth(): String {
        val percent = this.getCurrentPercentage()
        val curr = this.getCurrentCapacity()
        val estimate = String.format("%.2f", curr / (percent / 100))
        val max = this.getDesignedCapacity() ?: return "Unknown"
        val percentage = String.format("%.2f", (curr / percent / max * 10000))
        return "$curr (${percent}%) - $estimate (100%)\nMax: ${max}\nEstimated: ${percentage}%"
    }
}