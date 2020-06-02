package com.yihengquan.battery68.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yihengquan.battery68.core.BatteryHelper

class HomeViewModel : ViewModel() {

    private lateinit var context: Context
    private lateinit var helper: BatteryHelper

    private val _designCapacity = MutableLiveData<String>()
    var designCapacity: LiveData<String> = _designCapacity

    fun setContext(context: Context) {
        this.context = context
        helper = BatteryHelper(context)
        _designCapacity.value = "${helper.getDesignedCapacity()} mah"
    }

}