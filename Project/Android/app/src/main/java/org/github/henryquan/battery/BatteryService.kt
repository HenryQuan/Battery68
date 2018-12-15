package org.github.henryquan.battery

import android.app.IntentService
import android.content.Intent
import android.widget.Toast

class BatteryService : IntentService(BatteryService::class.simpleName) {
    override fun onHandleIntent(current: Intent?) {
        Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show()
        print("Hello")
    }
}