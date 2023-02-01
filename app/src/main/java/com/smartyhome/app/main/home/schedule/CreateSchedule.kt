package com.smartyhome.app.main.home.schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.smartyhome.app.R
import com.smartyhome.app.utils.mqtt.MqttHelper
import kotlinx.android.synthetic.main.home_header.*

class CreateSchedule : AppCompatActivity(), View.OnClickListener {
    val TAG = "MQTT Logs"
    var mqttHelper: MqttHelper? = null

    var dno : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_schedule)
        init()
    }

    fun init() {
        tvTitelText.setText(getString(R.string.create_schedule))
        ivleftIcon.setImageResource(R.drawable.ic_back_arrow)
        ivRightIcon.visibility = View.GONE
        ivleftIcon.setOnClickListener(this)
        mqttHelper = MqttHelper()
        intent?.let {
            dno = intent.getStringExtra("dno")?:""
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivleftIcon -> {
                finish()
            }
        }
    }
}