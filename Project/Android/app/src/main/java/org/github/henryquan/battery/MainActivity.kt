package org.github.henryquan.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.github.henryquan.battery.Core.BatteryUtil

class MainActivity : AppCompatActivity() {

    lateinit var battery: BatteryUtil
    // Temp solution to get better estimate
    val mah = mutableListOf<Double>()
    var currLevel = -1
    var currMah: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get an estimated value
        battery = BatteryUtil(this)

        // add boardcast receiver to track battery level
        registerReceiver(this.broadCastReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    /**
     * To update when battery level changed
     */
    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val level = intent.getIntExtra("level", -1)
            val capacity = battery.getCurrentCapacity()

            // Start up also prevent possible incorrect valid
            if (currLevel == -1) {
                currLevel = level
                currMah = capacity
            }

            // Update mah from the second update
            if (level > currLevel) {
                // update curr
                currLevel = level
                val capacityDiff = capacity - currMah
                // save current mah
                mah.add(capacityDiff)

                // Calculating your battery health
                if (mah.size > 0) {
                    // Get average capacity per 1%
                    val total = mah.reduce { acc, d -> acc + d }
                    val estimate = total / (mah.size ) * 100.0
                    val max = battery.getDesignedCapacity()
                    val percentage = String.format("%.2f", estimate / max * 100)

                    capacityLabel.text = "${estimate} | ${percentage}% |${max} "
                }
            }
        }
    }
}
