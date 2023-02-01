package com.smartyhome.app.utils.mqtt

import android.content.Context
import android.util.Log
import com.smartyhome.app.R
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.SharedPreference
import com.smartyhome.app.utils.mqtt.SocketFactory.SocketFactoryOptions
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.security.*
import java.security.cert.CertificateException


class MqttHelper {
    private val TAG = "PahoMqttClient"

    private var mqttAndroidClient: MqttAndroidClient? = null
    private var username: String? = null
    private var password: String? = null

    private fun getMqttConnectionOptionAthenticate(context: Context?): MqttConnectOptions {
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isCleanSession = false
        mqttConnectOptions.isAutomaticReconnect = true
        //mqttConnectOptions.setWill(Constants.PUBLISH_TOPIC, "I am going offline".getBytes(), 1, true);
        mqttConnectOptions.userName = username
        mqttConnectOptions.password = password!!.toCharArray()

        /**
         * SSL broker requires a certificate to authenticate their connection
         * Certificate can be found in resources folder /res/raw/
         */
            val socketFactoryOptions = SocketFactoryOptions()
            try {
                socketFactoryOptions.withCaInputStream(context!!.resources.openRawResource(R.raw.ca))
                mqttConnectOptions.setSocketFactory(SocketFactory(socketFactoryOptions))
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("mqtt:", "MqttException setSocketFactory $e")
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                Log.d("mqtt:", "MqttException setSocketFactory $e")
            } catch (e: KeyStoreException) {
                e.printStackTrace()
                Log.d("mqtt:", "MqttException setSocketFactory $e")
            } catch (e: CertificateException) {
                e.printStackTrace()
                Log.d("mqtt:", "MqttException setSocketFactory $e")
            } catch (e: KeyManagementException) {
                e.printStackTrace()
                Log.d("mqtt:", "MqttException setSocketFactory $e")
            } catch (e: UnrecoverableKeyException) {
                e.printStackTrace()
                Log.d("mqtt:", "MqttException setSocketFactory $e")
            }

        return mqttConnectOptions
    }

    @Throws(MqttException::class, UnsupportedEncodingException::class)
    fun publishMessage(client: MqttAndroidClient, msg: String, qos: Int, topic: String) {
        val encodedPayload: ByteArray
        encodedPayload = msg.toByteArray(charset("UTF-8"))
        val message = MqttMessage(encodedPayload)
        message.id = 320
        message.isRetained = false
        message.qos = qos
        client.publish(topic, message)
        Log.d(
            TAG,
            "publishMessage: $topic $message"
        )
    }

    @Throws(MqttException::class)
    fun subscribe(client: MqttAndroidClient, topic: String, qos: Int) {
        try {
            val ret = arrayOfNulls<String>(1)
            val token = client?.subscribe(topic, qos)
            if (token == null) {
                Log.d("TAGG", "Subscribe token null")
                return
            }
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(iMqttToken: IMqttToken) {
                    Log.d(
                        TAG,
                        "Subscribe Successfully $topic"
                    )
                    //                setLog("Subscribe Successfully " + topic);
                }

                override fun onFailure(iMqttToken: IMqttToken, throwable: Throwable) {
                    Log.d(
                        TAG,
                        "Subscribe onFailure $topic"
                    )
                }
            }
        } catch (e: Exception) {
            Log.d("TAGG", "Subscribe Exception topic <> $topic" + e.message)
        }
    }

    @Throws(MqttException::class)
    fun unSubscribe(client: MqttAndroidClient, topic: String) {
        val token = client.unsubscribe(topic)
        token.actionCallback = object : IMqttActionListener {
            override fun onSuccess(iMqttToken: IMqttToken) {
                Log.d(
                    TAG,
                    "UnSubscribe Successfully $topic"
                )
            }

            override fun onFailure(iMqttToken: IMqttToken, throwable: Throwable) {
                Log.d(
                    TAG,
                    "UnSubscribe Failed $topic"
                )
            }
        }
    }

    private fun getDisconnectedBufferOptions(): DisconnectedBufferOptions {
        val disconnectedBufferOptions = DisconnectedBufferOptions()
        disconnectedBufferOptions.isBufferEnabled = true
        disconnectedBufferOptions.bufferSize = 100
        disconnectedBufferOptions.isPersistBuffer = false
        disconnectedBufferOptions.isDeleteOldestMessages = false
        return disconnectedBufferOptions
    }

    fun getHomeMqttClientAuthenticate(
        context: Context?,
        brokerUrl: String?
    ): MqttAndroidClient? {
        val sharedPreference = SharedPreference(context!!);
        username = sharedPreference.getUDID()
        password = sharedPreference.getString(Constant.PASSWORD)
        val clientId: String = Constant.clientId
        Log.d(TAG, "getMqttClientAuthenticate: $clientId")
        mqttAndroidClient = MqttAndroidClient(context, brokerUrl?:"", clientId)
        try {
            val token = mqttAndroidClient!!.connect(getMqttConnectionOptionAthenticate(context))
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    mqttAndroidClient!!.setBufferOpts(getDisconnectedBufferOptions())
                    Log.d(
                        TAG,
                        "Success token:: ${asyncActionToken.toString()}"
                    )
                    userSuscribe(mqttAndroidClient!!, context)
                    //                    setLog("Success "+token.toString());
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.d(
                        TAG,
                        "failed authentication $exception \n ${exception.printStackTrace()} ${exception.cause}"
                    )
                }
            }
        } catch (e: MqttException) {
            Log.d(
                TAG,
                "Failure1 $e"
            )
            e.printStackTrace()
        }
        return mqttAndroidClient
    }

    fun userSuscribe(client: MqttAndroidClient, context: Context?) {
        try {
            val sharedPreference = SharedPreference(context!!);
            val userID = sharedPreference.getUDID()
            subscribe(
                client,
                "u/" + userID + "/sub",
                1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception at unscubscribe ${e.toString()}")
        }
    }
}