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
            val level = intent?.getIntExtra("level", 0)
            Log.d("Level", "${level}")
            capacityLabel.text = battery.getEstimatedBatteryHealth()
        }
    }
}
