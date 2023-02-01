package com.smartyhome.app.main.home.devicedetails

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.larswerkman.holocolorpicker.ColorPicker
import com.larswerkman.holocolorpicker.ColorPicker.OnColorSelectedListener
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.Data
import com.smartyhome.app.main.home.roomdevices.DeviceModelManual
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.mqtt.MqttHelper
import com.smartyhome.app.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_device_type2_details.*
import kotlinx.android.synthetic.main.home_header.*
import kotlinx.android.synthetic.main.home_header.tvTitelText
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class Type2DeviceDetailsActivity : AppCompatActivity(), View.OnClickListener {

    var deviceObject: DeviceModelManual? = null
    var mqttHelper: MqttHelper? = null
    val TAG = "MQTT Logs"

    var blue = 0
    var green = 0
    var red = 0
    var warm_white = 0
    var white = 0
    var brightness = 0
    private var colorpic = 0
    var picker: ColorPicker? = null
    var deviceObjectfrompref: Data? = null
    var preference: SharedPreference? = null
    var seekbar_clr_picker: SeekBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_type2_details)
        preference = SharedPreference(this)
        getIntentData()
        init()
        initDiy()
        rgbdialog()
        setupClrPickerChange()
        handleClrSeekBar()
    }

    fun getIntentData() {
        intent?.let {
            if (it.hasExtra("deviceObject")) {
                val gson = Gson()
                val strObject = it.getStringExtra("deviceObject")
                deviceObject = gson.fromJson(strObject, DeviceModelManual::class.java)
                val strDeviceObj = preference?.getString(deviceObject?.dno!!) ?: ""
                deviceObjectfrompref = gson.fromJson(strDeviceObj, Data::class.java)
            }
        }
    }

    fun init() {
        ivleftIcon.setImageResource(R.drawable.ic_back_arrow)
        ivRightIcon.visibility = View.GONE
        tvTitelText.setText(deviceObject?.name ?: "")
        ivleftIcon.setOnClickListener(this)
        deviceObjectfrompref?.let { device ->
            switchOnOff.isEnabled = deviceObjectfrompref?.enable?.toString().toBoolean()
            switchOnOff.isChecked = deviceObjectfrompref?.d1?.state ?: false
            tvTitelbrvalue.text = ((device.d1.br * 100)).toInt().toString() + "%"
            seekbarBR.setProgress((device.d1.br * 100).toInt())
        }
        mqttHelper = MqttHelper()
        handleSwitchOperation()
    }

    override fun onResume() {
        super.onResume()
        mqttHelper?.let { mqttHelper ->
            Constant.mqttClient?.let {
                mqttHelper.subscribe(it, "d/" + deviceObject?.dno + "/#", 1)
            }
            setupListener()
        }
    }

    override fun onStop() {
        super.onStop()
        mqttHelper?.let {
            Constant.mqttClient?.let {
                mqttHelper?.unSubscribe(it, "d/" + deviceObject?.dno + "/#")
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            (R.id.ivleftIcon) -> finish()
            (R.id.diy2Red) -> {
                red = 255
                green = 0
                blue = 0
                white = 0
                warm_white = 0
                brightness = seekbarBR.progress

                setRBG()
                picker!!.color =
                    Color.argb(255, red, green, blue)
                publishRBGcolor()
            }
            (R.id.diy3Blue) -> {
                red = 0
                green = 0
                blue = 255
                white = 0
                warm_white = 0
                brightness = seekbarBR.progress

                setRBG()
                picker!!.color =
                    Color.argb(255, red, green, blue)
                publishRBGcolor()
            }
            (R.id.diy4Green) -> {
                red = 0
                green = 255
                blue = 0
                white = 0
                warm_white = 0
                brightness = seekbarBR.progress

                setRBG()
                picker!!.color =
                    Color.argb(255, red, green, blue)
                publishRBGcolor()
            }
            (R.id.diy5Yellow) -> {
                red = 255
                green = 255
                blue = 0
                white = 0
                warm_white = 0
                brightness = seekbarBR.progress

                setRBG()
                picker!!.color =
                    Color.argb(255, red, green, blue)
                publishRBGcolor()
            }
        }
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

    fun handleSwitchOnOff() {
        try {
            val _obj = JsonObject()
            _obj.addProperty("dno", deviceObject?.dno)
            _obj.addProperty("key", deviceObject?.key)
            _obj.addProperty("dtype", deviceObject?.dtype)
            val `object` = JsonObject()
            `object`.addProperty("state", switchOnOff.isChecked)
            _obj.add("d1", `object`)

            val gson = Gson()
            val json: String = gson.toJson(_obj)
            Log.e("TURN_ON_OFF_DEVICE", "Object in String $json")
            mqttHelper?.publishMessage(
                Constant?.mqttClient!!,
                json,
                1,
                "d/${deviceObject?.dno}/sub"
            )
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

    fun publishRBGcolor() {
        val jsonObjectD1 = JSONObject()
        val jsonObjectMain = JSONObject()
        try {
            jsonObjectD1.put("state", switchOnOff.isChecked)
            jsonObjectD1.put("r", red)
            jsonObjectD1.put("g", green)
            jsonObjectD1.put("b", blue)
            jsonObjectD1.put("w", white)
            jsonObjectD1.put("ww", warm_white)
            jsonObjectD1.put("br", brightness.toDouble() / 100)
            jsonObjectMain.put("dno", deviceObject?.dno)
            jsonObjectMain.put("key", deviceObject?.key)
            jsonObjectMain.put("dtype", deviceObject?.dtype)
            jsonObjectMain.put("d1", jsonObjectD1)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d(TAG, "publishRBGcolor: $jsonObjectMain")
        preference?.setString(deviceObjectfrompref?.dno?:"0",jsonObjectMain.toString())
        try {
            mqttHelper?.publishMessage(
                Constant.mqttClient!!,
                jsonObjectMain.toString(),
                1,
                "d/${deviceObject?.dno}/sub"
            )
        } catch (e: MqttException) {
            Log.d(TAG, e.message!!)
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            Log.d(TAG, e.message!!)
            e.printStackTrace()
        }
    }

    fun initDiy() {
        val diy2Red = findViewById<CardView>(R.id.diy2Red)
        val diy3Blue = findViewById<CardView>(R.id.diy3Blue)
        val diy4Green = findViewById<CardView>(R.id.diy4Green)
        val diy5Yellow = findViewById<CardView>(R.id.diy5Yellow)

        diy2Red.setOnClickListener(this)
        diy3Blue.setOnClickListener(this)
        diy4Green.setOnClickListener(this)
        diy5Yellow.setOnClickListener(this)
        diy2Red.setBackgroundColor(Color.RED)
        diy3Blue.setBackgroundColor(Color.BLUE)
        diy4Green.setBackgroundColor(Color.GREEN)
        diy5Yellow.setBackgroundColor(Color.YELLOW)

        picker = findViewById(R.id.picker11_new)
        picker?.setShowOldCenterColor(false)
        colorpic = picker?.getColor() ?: 0
        picker?.setTouchAnywhereOnColorWheelEnabled(true)

    }

    private fun getJsonString(): String? {
        val jsonObjectD1 = JSONObject()
        try {
            // jsonObjectD1.put("state",state);
            jsonObjectD1.put("r", red)
            jsonObjectD1.put("g", green)
            jsonObjectD1.put("b", blue)
            jsonObjectD1.put("w", white)
            jsonObjectD1.put("ww", warm_white)
            jsonObjectD1.put("br", brightness)
            jsonObjectD1.put("color", colorpic)
        } catch (e: Exception) {
        }
        return jsonObjectD1.toString()
    }

    fun jsonObjectreader(json: String?, name: String?): String? {
        return try {
            val jsonObject = JSONObject(json)
            if (jsonObject.has(name)) jsonObject[name].toString() else "NA"
        } catch (e: JSONException) {
            e.printStackTrace()
            //            return "error";
            "NA"
        }
    }

    private fun getJsonStringWhite(): String? {
        val jsonObjectD1 = JSONObject()
        try {
            //jsonObjectD1.put("state",state);
            jsonObjectD1.put("r", 255)
            jsonObjectD1.put("g", 255)
            jsonObjectD1.put("b", 255)
            jsonObjectD1.put("w", 0)
            jsonObjectD1.put("ww", 255)
            jsonObjectD1.put("br", brightness)
            jsonObjectD1.put("color", 0)
        } catch (e: Exception) {
        }
        return jsonObjectD1.toString()
    }

    fun setRBG() {
        val r: TextView
        val b: TextView
        val g: TextView
        r = findViewById(R.id.red)
        b = findViewById(R.id.blue)
        g = findViewById(R.id.green)
        r.text = "R " + java.lang.String.valueOf(red)
        b.text = "B " + java.lang.String.valueOf(blue)
        g.text = "G " + java.lang.String.valueOf(green)
        r.setBackgroundColor(Color.argb(255, red, 0, 0))
        b.setBackgroundColor(Color.argb(255, 0, 0, blue))
        g.setBackgroundColor(Color.argb(255, 0, green, 0))
    }

    fun rgbdialog() {
        red = deviceObjectfrompref?.d1?.r ?: 0
        green=
            deviceObjectfrompref?.d1?.g ?: 0
        blue= deviceObjectfrompref?.d1?.b ?: 0
        brightness = seekbarBR.progress
        warm_white = deviceObjectfrompref?.d1?.ww?.toInt() ?: 0
        Log.e(TAG, "rgbdialog:  v " + brightness)
        val colorId = Color.argb(255, red, green, blue)
        val dia_name = arrayOfNulls<String>(1)
        picker?.let {
            it.color = colorId
            it.setColor(colorId);
            colorpic = it.getColor();
            it.setOnColorChangedListener(null);
        }

        seekbarBR.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var progress = progress
                if (progress == 0) progress = 1
                brightness = seekbarBR.progress
                if (progress < 10) {
                    seekbarBR.setProgress(10)
                }
                tvTitelbrvalue.setText(brightness.toString()+"%")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                white = 0
                warm_white = 0
                publishRBGcolor()
            }
        })
        setRBG()
    }

    fun setupClrPickerChange() {
        picker!!.onColorSelectedListener = OnColorSelectedListener { color: Int ->
            try {
                colorpic = color
                red = Color.red(color)
                green = Color.green(color)
                blue = Color.blue(color)
                setRBG()
                white = 0
                warm_white = 0
                publishRBGcolor()
            } catch (e: java.lang.Exception) {
                Log.e("TAGGG", "Exception at on color " + e.message, e)
            }
        }
    }

    fun handleClrSeekBar(){
        seekbar_clr_picker = findViewById(R.id.seekbar_clr_picker)
        seekbar_clr_picker?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                setColor(seekBar.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                publishRBGcolor()
            }
        })
    }

    fun setColor(progress: Int) {
        val redValue: Int
        val greenValue: Int
        val blueValue: Int
        if (progress <= 18) {
            redValue = 254
            greenValue = 242
            blueValue = 54
        } else if (progress == 19) {
            redValue = 254
            greenValue = 244
            blueValue = 81
        } else if (progress == 20) {
            redValue = 254
            greenValue = 247
            blueValue = 99
        } else if (progress == 21) {
            redValue = 254
            greenValue = 249
            blueValue = 112
        } else if (progress == 22) {
            redValue = 254
            greenValue = 251
            blueValue = 123
        } else if (progress == 23) {
            redValue = 254
            greenValue = 253
            blueValue = 132
        } else if (progress == 24) {
            redValue = 254
            greenValue = 254
            blueValue = 139
        } else if (progress == 25) {
            redValue = 252
            greenValue = 254
            blueValue = 145
        } else if (progress == 26) {
            redValue = 251
            greenValue = 254
            blueValue = 149
        } else if (progress == 27) {
            redValue = 250
            greenValue = 254
            blueValue = 153
        } else if (progress == 28) {
            redValue = 248
            greenValue = 254
            blueValue = 157
        } else if (progress == 29) {
            redValue = 247
            greenValue = 254
            blueValue = 161
        } else if (progress == 30) {
            redValue = 246
            greenValue = 254
            blueValue = 164
        } else if (progress == 31) {
            redValue = 245
            greenValue = 254
            blueValue = 167
        } else if (progress == 32) {
            redValue = 244
            greenValue = 254
            blueValue = 169
        } else if (progress == 33) {
            redValue = 244
            greenValue = 254
            blueValue = 172
        } else if (progress == 34) {
            redValue = 243
            greenValue = 254
            blueValue = 174
        } else if (progress == 35) {
            redValue = 242
            greenValue = 254
            blueValue = 176
        } else if (progress == 36) {
            redValue = 241
            greenValue = 254
            blueValue = 178
        } else if (progress == 37) {
            redValue = 241
            greenValue = 254
            blueValue = 180
        } else if (progress == 38) {
            redValue = 240
            greenValue = 253
            blueValue = 182
        } else if (progress == 39) {
            redValue = 240
            greenValue = 253
            blueValue = 184
        } else if (progress == 40) {
            redValue = 239
            greenValue = 253
            blueValue = 185
        } else if (progress == 41) {
            redValue = 239
            greenValue = 253
            blueValue = 187
        } else if (progress == 42) {
            redValue = 238
            greenValue = 253
            blueValue = 188
        } else if (progress == 43) {
            redValue = 238
            greenValue = 253
            blueValue = 190
        } else if (progress == 44) {
            redValue = 237
            greenValue = 253
            blueValue = 191
        } else if (progress == 45) {
            redValue = 237
            greenValue = 253
            blueValue = 192
        } else if (progress == 46) {
            redValue = 237
            greenValue = 253
            blueValue = 193
        } else if (progress == 47) {
            redValue = 236
            greenValue = 253
            blueValue = 194
        } else if (progress == 48) {
            redValue = 236
            greenValue = 253
            blueValue = 195
        } else if (progress == 49) {
            redValue = 235
            greenValue = 253
            blueValue = 197
        } else if (progress == 50) {
            redValue = 235
            greenValue = 253
            blueValue = 198
        } else if (progress >= 51 && progress <= 60) {
            redValue = 233
            greenValue = 254
            blueValue = 204
        } else if (progress >= 61 && progress <= 70) {
            redValue = 232
            greenValue = 254
            blueValue = 209
        } else if (progress >= 71 && progress <= 80) {
            redValue = 231
            greenValue = 254
            blueValue = 213
        } else if (progress >= 81 && progress <= 90) {
            redValue = 230
            greenValue = 254
            blueValue = 217
        } else {
            redValue = 229
            greenValue = 254
            blueValue = 220
        }
        white = 0
        warm_white = 0
        red = redValue
        green = greenValue
        blue = blueValue
        picker!!.color = Color.rgb(redValue, greenValue, blueValue)
        setRBG()
    }
}