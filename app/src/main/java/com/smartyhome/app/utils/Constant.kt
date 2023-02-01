package com.smartyhome.app.utils


//import org.eclipse.paho.android.service.MqttAndroidClient
import info.mqtt.android.service.MqttAndroidClient
import java.util.*
import java.util.regex.Pattern

object Constant {

    val SPLASH_DELAY: Long = 2000
    val DASH_SHOWN = "DASH_SHOWN"
    var BASE_URL = "http://smartyhome.in"

    var LOGIN_INFO = "LoginInfo"
    var PASSWORD = "Password"

    var mqttClient: MqttAndroidClient? = null

    var EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    const val MQTT_BROKER_URL = "ssl://209.58.164.151:8883"
    const val PUBLISH_TOPIC = "u/c14ecd7a-7869-4c5f-bdae-c93f107b5edf/pub"
    const val SUSCRIBE_TOPIC = "u/c14ecd7a-7869-4c5f-bdae-c93f107b5edf/sub"
    var clientId = UUID.randomUUID().toString()



    const val SHARED_PREFERENCES_NAME = "HomeAutomation"
    const val Group = "Group_"
    const val isViewTypeGrid = "isViewTypeGrid"
    const val deviceType = "deviceType"

}