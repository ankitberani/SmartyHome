package com.smartyhome.app.main.home.devicedetails.type21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rmindr.app.network.MyCustomeApi
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.Data
import com.smartyhome.app.main.home.roomdevices.DeviceModelManual
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.mqtt.MqttHelper
import com.smartyhome.app.utils.SharedPreference
import com.smartyhome.app.utils.ViewModelFactory
import com.smartyhome.app.utils.common
import kotlinx.android.synthetic.main.activity_type21_device_detail.*
import kotlinx.android.synthetic.main.home_header.*
import kotlinx.android.synthetic.main.home_header.tvTitelText
import kotlinx.android.synthetic.main.layout_progress_bar.*
import org.eclipse.paho.client.mqttv3.MqttException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.lang.Exception

class Type21DeviceDetailActivity : AppCompatActivity(), View.OnClickListener, type21Interface {
    lateinit var mainModel: Type21ViewModel
    var deviceObject: DeviceModelManual? = null
    var mqttHelper: MqttHelper? = null
    var deviceObjectfrompref: Data? = null
    var preference: SharedPreference? = null
    var type : String = "d1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type21_device_detail)
        initModel()
        getIntentData()
        init()
        observeGroupScenes()
    }


    fun initModel() {
        preference = SharedPreference(this)
        val myApi = MyCustomeApi()
        val _repository = ApiCallingRepository(myApi)
        val factory = ViewModelFactory(_repository)
        mainModel = ViewModelProviders.of(this, factory).get(Type21ViewModel::class.java)
//        rlProgressbar.visibility = View.VISIBLE
    }

    fun getIntentData() {
        intent?.let {
            if (it.hasExtra("deviceObject")) {
                val gson = Gson()
                val strObject = it.getStringExtra("deviceObject")
                deviceObject = gson.fromJson(strObject, DeviceModelManual::class.java)
                val strDeviceObj = preference?.getString(deviceObject?.dno!!) ?: ""
                deviceObjectfrompref = gson.fromJson(strDeviceObj, Data::class.java)
                tvTitelText.setText(deviceObject?.name ?: "")
            }
            if(it.hasExtra("type")){
                type = it.getStringExtra("type")?:"d1"
            }
        }
    }

    fun init() {
        ivleftIcon.setImageResource(R.drawable.ic_back_arrow)
        ivRightIcon.setImageResource(R.drawable.ic_check_mark)
        ivleftIcon.setOnClickListener(this)
        ivRightIcon.setOnClickListener(this)
        mqttHelper = MqttHelper()
    }

    override fun onClick(_view: View?) {
        when (_view?.id) {
            (R.id.ivleftIcon) -> {
                finish()
            }
            (R.id.ivRightIcon) -> {
                sendTomqtt()
                common.showSnackBar(constraintMain, getString(R.string.success))
            }
        }
    }

    fun observeGroupScenes() {
        val uID = preference?.getUDID() ?: ""
        mainModel.getGroupSceneList(uID, preference!!, deviceObjectfrompref?.dno!!)
        mainModel.grpDataResponse?.observe(this, Observer {
            setGrpDeviceAdapter(it.Scene)
        })
    }

    fun setGrpDeviceAdapter(sceneLst: List<Scene>) {
        rvGroupsSelected.also {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(true)
            //Gson _gson = new Gson();
            val _array = getSelectedGrpFromPref()
            for (i in 0 until sceneLst.size) {
                for (j in 0 until _array.size) {
                    if (sceneLst.get(i).ID.equals(_array.get(j))) {
                        sceneLst.get(i).selected = true
                    }
                }
            }
            it.adapter = GroupDeviceListAdapter(sceneLst, this)
        }
//        rlProgressbar.visibility = View.GONE
    }

    override fun onGrpItemClicked(objScene: Scene, position: Int) {
        mainModel.setSelected(position)
    }



    fun getSelectedGrpFromPref(): ArrayList<String> {
        val stringFromPref = preference?.getString(Constant.Group + "${deviceObjectfrompref?.dno}")
        val gson = Gson()
        if (stringFromPref.isNullOrEmpty()) {
            return ArrayList()
        } else {
            val arrayList = gson.fromJson<ArrayList<String>>(stringFromPref, object :
                TypeToken<ArrayList<String>>() {}.type)
            return arrayList
        }
    }

    fun sendTomqtt(){
        val _object_main = JSONObject()
        val _object_d1 = JSONObject()
        var _data = ""
        try {
            _object_d1.put("groups", getSelectedGrpFromPref())
            _object_main.put(type, _object_d1)
            _data = _object_main.toString()
            postNewGroups(_data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun postNewGroups(infos: String) {
        Log.e("TAG", "postNewGroups At Infor $infos")
        try {
            mqttHelper?.publishMessage(
                Constant.mqttClient!!, infos,
                1,
                "d/${deviceObjectfrompref?.dno}/sub"
            )
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        } catch (e: MqttException) {
            Log.e("TAG", e.message!!)
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            Log.d("TAG", e.message!!)
            e.printStackTrace()
        }
    }
}