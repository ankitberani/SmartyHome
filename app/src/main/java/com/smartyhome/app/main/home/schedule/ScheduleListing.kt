package com.smartyhome.app.main.home.schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.smartyhome.app.R
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.mqtt.MqttHelper
import kotlinx.android.synthetic.main.activity_schedule_listing.*
import kotlinx.android.synthetic.main.home_header.*
import kotlinx.android.synthetic.main.layout_room_list.*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject
import java.lang.Exception

class ScheduleListing : AppCompatActivity() , View.OnClickListener{

    val TAG = "MQTT Logs"
    var mqttHelper: MqttHelper? = null

    var dno : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_listing)
        init()
        getSchedules()

    }

    fun init() {
        tvTitelText.setText(getString(R.string.schedule))
        ivleftIcon.setImageResource(R.drawable.ic_back_arrow)
        ivleftIcon.setOnClickListener(this)
        btn_add_new_schedule.setOnClickListener(this)

        mqttHelper = MqttHelper()
        intent?.let {
//            dno = intent.getStringExtra("dno")?:""
            dno = "84F3EBFBE183"
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivleftIcon ->{
                finish()
            }
            R.id.btn_add_new_schedule ->{
                val intent = Intent(this@ScheduleListing, CreateSchedule::class.java)
                intent.putExtra("dno",dno)
                startActivity(intent)
            }

        }
    }

    fun getSchedules() {
        try {
            Log.e("TAGG", "MQTT getSchedule Called dno $dno")
            val _obj = JSONObject()
            _obj.put("getSch", 1)
            mqttHelper?.publishMessage(
                Constant?.mqttClient!!,
                _obj.toString(),
                1,
                "d/$dno/pub"
            )
            Log.e("TAGG", "MQTT getSchedule ${_obj.toString()}")
            setupListener()
        } catch (e: Exception) {
            Log.e("TAGG", "Exception at getSchedule " + e.message, e)
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
                Log.e("TAG", "MQTT Cred user messageArrived \n $msg")
                try {
                } catch (e: Exception) {
                    Log.e("TAG", "Exceptin while read data ${e.toString()}")
                }
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {}
        })
    }
}