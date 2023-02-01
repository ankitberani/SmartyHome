package com.smartyhome.app.main.home.roomdevices

import android.view.View

interface DeviceActionInterface {

    fun onDeviceClicked(deviceObject : DeviceModelManual,type: String)
    fun onSwitchChecked(deviceObject: DeviceModelManual, isChecked: Boolean,type:String)
    fun showSnackbar(msg:String)
    fun sendFanProgress(deviceObject : DeviceModelManual,type:String,progress:Double,isChecked: Boolean)
    fun showMenu(view: View?, isOffline: Boolean, pos: Int, type: Int,name : String,d1Type:String)
}