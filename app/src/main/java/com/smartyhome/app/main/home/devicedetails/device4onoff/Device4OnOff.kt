package com.smartyhome.app.main.home.devicedetails.device4onoff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.Data
import com.smartyhome.app.main.home.roomdevices.DeviceModelManual
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.SharedPreference
import com.smartyhome.app.utils.mqtt.MqttHelper
import kotlinx.android.synthetic.main.activity_device4_on_off.*
import kotlinx.android.synthetic.main.home_header.*
import kotlinx.android.synthetic.main.home_header.tvTitelText
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage

class Device4OnOff : AppCompatActivity(), View.OnClickListener {

    var deviceObject: DeviceModelManual? = null
    var mqttHelper: MqttHelper? = null
    var deviceObjectfrompref: Data? = null
    var preference: SharedPreference? = null
    val TAG = javaClass.simpleName
    var type : String = "d1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device4_on_off)
        getIntentData()
        init()
        handleSwitchOperation()
        setupListener()
    }

    fun getIntentData() {
        preference = SharedPreference(this)
        intent?.let {

            if(it.hasExtra("type")){
                type = it.getStringExtra("type")?:"d1"
            }

            if (it.hasExtra("deviceObject")) {
                val gson = Gson()
                val strObject = it.getStringExtra("deviceObject")
                deviceObject = gson.fromJson(strObject, DeviceModelManual::class.java)
                val strDeviceObj = preference?.getString(deviceObject?.dno!!) ?: ""
                deviceObjectfrompref = gson.fromJson(strDeviceObj, Data::class.java)
                if (type.equals("d1"))
                    tvTitelText.setText(deviceObject?.d1?.name ?: "")
                else if (type.equals("d2"))
                    tvTitelText.setText(deviceObject?.d2?.name ?: "")
                else if (type.equals("d3"))
                    tvTitelText.setText(deviceObject?.d3?.name ?: "")
                else if (type.equals("d4"))
                    tvTitelText.setText(deviceObject?.d4?.name ?: "")
                else if (type.equals("d5"))
                    tvTitelText.setText(deviceObject?.d5?.name ?: "")
                else if (type.equals("d6"))
                    tvTitelText.setText(deviceObject?.d6?.name ?: "")
                else if (type.equals("d7"))
                    tvTitelText.setText(deviceObject?.d7?.name ?: "")
                else if (type.equals("d8"))
                    tvTitelText.setText(deviceObject?.d8?.name ?: "")

                switchOnOff.isChecked = deviceObjectfrompref?.isOnline ?: false
            }
        }
    }

    fun init() {
        ivleftIcon.setImageResource(R.drawable.ic_back_arrow)
        ivRightIcon.visibility = View.GONE
        ivleftIcon.setOnClickListener(this)
        mqttHelper = MqttHelper()
    }

    override fun onClick(_view: View?) {
        when (_view?.id) {
            (R.id.ivleftIcon) -> {
                finish()
            }
        }
    }

    fun handleSwitchOnOff() {
        try {
            val _obj = JsonObject()
            _obj.addProperty("dno", deviceObject?.dno)
            _obj.addProperty("key", deviceObject?.key)
            _obj.addProperty("dtype", deviceObject?.dtype)
            val `object` = JsonObject()
            `object`.addProperty("state", switchOnOff.isChecked)
            _obj.add(type, `object`)

            val gson = Gson()
            val json: String = gson.toJson(_obj)
            Log.e("TURN_ON_OFF_DEVICE", "Object in String $json")
            mqttHelper?.publishMessage(
                Constant?.mqttClient!!,
                json,
                1,
                "d/${deviceObject?.dno}/sub"
            )
            preference?.setString(deviceObjectfrompref?.dno ?: "0", json)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAGGG", "Exception at TurnOnOffDevice Devices " + e.message, e)
        }
    }

    fun handleSwitchOperation() {
        switchOnOff.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            handleSwitchOnOff()
        })
    }

    fun setupListener() {
        Constant.mqttClient?.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {
                Log.e(TAG, "MQTT Cred user connect complete")
            }

            override fun connectionLost(throwable: Throwable) {
                Log.e(TAG, "MQTT Disconnected!")
            }

            @Throws(Exception::class)
            override fun messageArrived(s: String, mqttMessage: MqttMessage) {
                val msg = String(mqttMessage.payload)
                Log.e(TAG, "MQTT Cred user messageArrived $s <> \n $msg")
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {}
        })
    }
}