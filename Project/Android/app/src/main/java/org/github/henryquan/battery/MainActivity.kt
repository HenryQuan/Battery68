package org.github.henryquan.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.github.henryquan.battery.Core.BatteryUtil

class MainActivity : AppCompatActivity() {

    lateinit var battery: BatteryUtil
    // Temp solution to get better estimate
    val mah = arrayOf<Double>()
    var curr = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get an estimated value
        battery = BatteryUtil(this)
        capacityLabel.text = battery.getEstimatedBatteryHealth()

        // add boardcast receiver to track battery level
        registerReceiver(this.broadCastReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    /**
     * To update when battery level changed
     */
    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            val level = intent?.getIntExtra("level", -1)!!

            if (curr == -1) {
                curr = level
            } else if (curr > level) {
                // update curr
                curr = level
                // save current mah
                mah.plus(battery.getCurrentCapacity())

                // With only one, the result will be zero...
                if (mah.size > 1) {
                    // Make first element zero because it might not be accurate
                    mah[0] = 0.0
                    // Get average capacity per 1%,
                    val total = mah.reduce { acc, d -> acc + d }
                    val estimate = total / (mah.size - 1) * 100.0
                    val max = battery.getDesignedCapacity()
                    val percentage = String.format("%.2f", estimate / max * 100)

                    capacityLabel.text = "${estimate} | ${percentage}% |${max} "
                }
            }
        }
    }
}
