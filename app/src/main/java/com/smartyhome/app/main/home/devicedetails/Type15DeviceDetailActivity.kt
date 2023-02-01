package com.smartyhome.app.main.home.devicedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sdsmdg.harjot.crollerTest.Croller
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.Data
import com.smartyhome.app.main.home.roomdevices.DeviceModelManual
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.mqtt.MqttHelper
import com.smartyhome.app.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_type15_device_detail.*
import kotlinx.android.synthetic.main.home_header.*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class Type15DeviceDetailActivity : AppCompatActivity(),View.OnClickListener {

    var deviceObject: DeviceModelManual? = null
    var mqttHelper: MqttHelper? = null
    var deviceObjectfrompref: Data? = null
    var preference: SharedPreference? = null
    var br = 0.0
    var _dno = ""
    var key:String? = ""
    var state:Boolean = false
    val TAG = "MQTT Logs"
    var type : String = "d1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type15_device_detail)
        getIntentData()
        initData()
    }

    fun getIntentData() {
        intent?.let {
            if (it.hasExtra("deviceObject")) {
                preference  = SharedPreference(this)
                val gson = Gson()
                val strObject = it.getStringExtra("deviceObject")
                deviceObject = gson.fromJson(strObject, DeviceModelManual::class.java)
                val strDeviceObj = preference?.getString(deviceObject?.dno!!) ?: ""
                deviceObjectfrompref = gson.fromJson(strDeviceObj, Data::class.java)

                _dno = deviceObjectfrompref?.dno?:""
                key = deviceObjectfrompref?.key?:""
                state = deviceObjectfrompref?.d1?.state?:false
                br = deviceObjectfrompref?.d1?.br?:0.0
                if (state) {
                    Glide.with(this)
                        .load(R.drawable.animated_fan)
                        .into(iv_fan)
                    switch1.setChecked(true)
                } else {
                    iv_fan.setImageResource(R.drawable.animated_fan)
                    switch1.setChecked(false)
                }
            }
            if(it.hasExtra("type")){
                type = it.getStringExtra("type")?:"d1"
            }
        }
    }

    fun initData() {
        mqttHelper = MqttHelper()
        ivleftIcon.setImageResource(R.drawable.ic_back_arrow)
        ivRightIcon.visibility = View.GONE
        tvTitelText.setText(deviceObject?.name ?: "")
        ivleftIcon.setOnClickListener(this)
        switch1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            Log.e("TAG", "Switch State $b")
            state = b
            TurnOnOffDevice()
        })
        tv_4.setOnClickListener(View.OnClickListener { view: View? ->
            this.onClick(
                view
            )
        })
        tv_3.setOnClickListener(View.OnClickListener { view: View? ->
            this.onClick(
                view
            )
        })
        tv_2.setOnClickListener(View.OnClickListener { view: View? ->
            this.onClick(
                view
            )
        })
        tv_1.setOnClickListener(View.OnClickListener { view: View? ->
            this.onClick(
                view
            )
        })

        var progress = 0
        progress = if (br == 1.0) {
            4
        } else if (br == 0.75) {
            3
        } else if (br == 0.50) {
            2
        } else 1

        croller.progress = progress
        croller.setOnCrollerChangeListener(object : OnCrollerChangeListener {
            override fun onProgressChanged(croller: Croller, progress: Int) {
                // use the progress
                tv_progress.text = progress.toString() + ""
                Log.e("TAG", "onProgressChanged progress $progress")
            }

            override fun onStartTrackingTouch(croller: Croller) {
                // tracking started
            }

            override fun onStopTrackingTouch(croller: Croller) {
                // tracking stopped
                publishSeekbar()
            }
        })
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


    fun publishSeekbar() {
        val jsonObjectD1 = JSONObject()
        val jsonObjectMain = JSONObject()
        var _json = ""
        try {
            jsonObjectD1.put("state", state)
            jsonObjectD1.put("br", getProgress())
            jsonObjectMain.put("dno", _dno)
            jsonObjectMain.put("key", key)
            jsonObjectMain.put("dtype", "15")
            jsonObjectMain.put(type, jsonObjectD1)
            _json = jsonObjectMain.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        preference?.setString(deviceObjectfrompref?.dno?:"0",jsonObjectMain.toString())
        try {
            mqttHelper?.publishMessage(
                Constant?.mqttClient!!,
                _json,
                1,
                "d/$_dno/sub"
            )
        } catch (e: MqttException) {
            Log.e("TAG", e.message!!)
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            Log.e("TAG", e.message!!)
            e.printStackTrace()
        }
    }

    fun getProgress(): Double {
        if (croller.progress == 4) {
            return 1.0
        } else if (croller.progress == 3) {
            return 0.75
        } else if (croller.progress == 2) {
            return 0.50
        } else if (croller.progress == 1) {
            return 0.25
        }
        return 0.25
    }

    fun TurnOnOffDevice() {

        //showProgressDialog(getResources().getString(R.string.please_wait));
        try {
            val _obj = JsonObject()
            _obj.addProperty("dno", _dno)
            _obj.addProperty("key", key)
            _obj.addProperty("dtype", "15")
            val `object` = JsonObject()
            `object`.addProperty("state", state)
            _obj.add(type, `object`)

            /*filteredList.getObjData().get(pos).getObjd1().setState(state);
            _device_adapter.notifyItemChanged(pos);*/
            val gson = Gson()
            val json = gson.toJson(_obj)
            Log.e("TURN_ON_OFF_DEVICE", "Object in String $json")
           mqttHelper?.publishMessage(
               Constant?.mqttClient!!,
                json,
                1,
                "d/$_dno/sub"
            )
            if (state) {
                Glide.with(this)
                    .load(R.drawable.animated_fan)
                    .into(iv_fan)
            } else {
                iv_fan.setImageResource(R.drawable.animated_fan)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e("TAGGG", "Exception at TurnOnOffDevice Devices " + e.message, e)
        }
    }

    override fun onClick(view: View?) {

        when(view?.id){
            (R.id.tv_1) -> {
                croller.progress = 1
                publishSeekbar()
            }
            (R.id.tv_2) -> {
                croller.progress = 2
                publishSeekbar()
            }
            (R.id.tv_3) -> {
                croller.progress = 3
                publishSeekbar()
            }
            (R.id.tv_4) -> {
                croller.progress = 4
                publishSeekbar()
            }
            (R.id.ivleftIcon) -> {
                finish()
            }
        }
    }

}