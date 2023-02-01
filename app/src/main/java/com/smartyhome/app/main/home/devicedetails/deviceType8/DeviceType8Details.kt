package com.smartyhome.app.main.home.devicedetails.deviceType8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.Data
import com.smartyhome.app.main.home.roomdevices.DeviceModelManual
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.SharedPreference
import com.smartyhome.app.utils.mqtt.MqttHelper
import kotlinx.android.synthetic.main.activity_device_type8_details.*
import kotlinx.android.synthetic.main.activity_device_type8_details.seekbarBR
import kotlinx.android.synthetic.main.activity_device_type8_details.switchOnOff
import kotlinx.android.synthetic.main.activity_device_type8_details.tvTitelbrvalue
import kotlinx.android.synthetic.main.home_header.*
import kotlinx.android.synthetic.main.home_header.tvTitelText
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class DeviceType8Details : AppCompatActivity(), View.OnClickListener {

    var deviceObject: DeviceModelManual? = null
    var mqttHelper: MqttHelper? = null

    var preference: SharedPreference? = null
    var type: String = "d1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_type8_details)
        getIntentData()
        init()
        handleSwitchOperation()
        setupSeekbar()
        setupListener()
    }

    fun getIntentData() {
        preference = SharedPreference(this)
        intent?.let {
            if (it.hasExtra("type")) {
                type = it.getStringExtra("type") ?: "d1"
            }
            if (it.hasExtra("deviceObject")) {
                val gson = Gson()
                val strObject = it.getStringExtra("deviceObject")
                deviceObject = gson.fromJson(strObject, DeviceModelManual::class.java)
                deviceObject?.let { device ->
                    var states = false
                    var progress = 0
                    var br = ""
                    if (type.equals("d1")) {
                        tvTitelText.setText(device?.d1?.name ?: "")
                        states = deviceObject?.d1?.state ?: false
                        if (deviceObject?.dtype == 8) {
                            progress = (device.d1?.br?.times(100)?.toInt() ?: 0)
                            br = (device.d1?.br?.times(100))?.toInt().toString() + "%"
                        } else {
                            seekbarBR.visibility = View.GONE
                            tvTitelbrvalue.visibility = View.GONE
                            bulbbrright.visibility = View.GONE
                            bulbbr.visibility = View.GONE
                            tvIntensityTitle.visibility = View.GONE
                        }
                    } else if (type.equals("d2")) {
                        tvTitelText.setText(device?.d2?.name ?: "")
                        states = deviceObject?.d2?.state ?: false
                        seekbarBR.visibility = View.GONE
                        tvTitelbrvalue.visibility = View.GONE
                        bulbbrright.visibility = View.GONE
                        bulbbr.visibility = View.GONE
                        tvIntensityTitle.visibility = View.GONE
                    } else if (type.equals("d3")) {
                        tvTitelText.setText(device?.d3?.name ?: "")
                        states = deviceObject?.d3?.state ?: false
                        seekbarBR.visibility = View.GONE
                        tvTitelbrvalue.visibility = View.GONE
                        bulbbrright.visibility = View.GONE
                        bulbbr.visibility = View.GONE
                        tvIntensityTitle.visibility = View.GONE
                    } else if (type.equals("d4")) {
                        tvTitelText.setText(device?.d4?.name ?: "")
                        states = device.d4?.state ?: false
                        progress = (device.d4?.br?.times(100)?.toInt()) ?: 0
                        br = (device.d4?.br?.times(100)?.toString() + "%")
                        if (deviceObject?.dtype == 23) {
                            seekbarBR.visibility = View.GONE
                            tvTitelbrvalue.visibility = View.GONE
                            bulbbrright.visibility = View.GONE
                            bulbbr.visibility = View.GONE
                            tvIntensityTitle.visibility = View.GONE
                        }

                    } else if (type.equals("d5")) {
                        tvTitelText.setText(device?.d5?.name ?: "")
                        states = device.d5?.state ?: false
                        if (deviceObject?.dtype == 23) {
                            progress = (device.d5?.br?.times(100)?.toInt()) ?: 0
                            br = (device.d5?.br?.times(100)).toString() + "%"
                            seekbarBR.visibility = View.VISIBLE
                            tvTitelbrvalue.visibility = View.VISIBLE
                        }
                    }
                    switchOnOff.isChecked = states
                    seekbarBR.setProgress(progress)
                    tvTitelbrvalue.text = br
                }
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
            preference?.setString(deviceObject?.dno ?: "0", json)
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

    fun setupSeekbar() {
        seekbarBR.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {

                if (seekBar.progress < 1) {
                    seekBar.setProgress(1)
                }
                var speed = 25
                speed = if (seekBar.progress <= 25) {
                    25
                } else if (seekBar.progress > 25 && seekBar.progress <= 50) {
                    50
                } else if (seekBar.progress > 50 && seekBar.progress <= 75) {
                    75
                } else {
                    100
                }
                seekBar.setProgress(speed)
                tvTitelbrvalue.setText(speed.toString() + "%")
                publishSeekbar((speed.toDouble() / 100))
            }
        })
    }

    fun publishSeekbar(speed: Double) {
        val jsonObjectD1 = JSONObject()
        val jsonObjectMain = JSONObject()
        var _json = ""
        try {
            jsonObjectD1.put("state", switchOnOff.isChecked)
            jsonObjectD1.put("br", speed)
            jsonObjectMain.put("dno", deviceObject?.dno)
            jsonObjectMain.put("key", deviceObject?.key)
            jsonObjectMain.put("dtype", deviceObject?.dtype)
            jsonObjectMain.put("d1", jsonObjectD1)
            _json = jsonObjectMain.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        preference?.setString(deviceObject?.dno ?: "0", jsonObjectMain.toString())
        try {
            mqttHelper?.publishMessage(
                Constant.mqttClient!!,
                _json,
                1, "d/" + deviceObject?.dno + "/sub"
            )
        } catch (e: MqttException) {
            Log.d("TAG", e.message!!)
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            Log.d("TAG", e.message!!)
            e.printStackTrace()
        }
    }

    fun setupListener() {
        Constant.mqttClient?.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {
                Log.e("TAG", "MQTT Cred user connect complete")
            }

            override fun connectionLost(throwable: Throwable) {
                Log.e("TAG", "MQTT Disconnected!")
            }

            @Throws(Exception::class)
            override fun messageArrived(s: String, mqttMessage: MqttMessage) {
                val msg = String(mqttMessage.payload)
                Log.e("TAG", "MQTT Cred user messageArrived $s <> \n $msg")
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {}
        })
    }
}