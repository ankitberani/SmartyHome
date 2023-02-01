package com.smartyhome.app.main.home.roomdevices

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.rmindr.app.network.MyCustomeApi
import com.smartyhome.app.R
import com.smartyhome.app.main.home.DeviceTypeModel
import com.smartyhome.app.main.home.Type
import com.smartyhome.app.main.home.devicedetails.Type15DeviceDetailActivity
import com.smartyhome.app.main.home.devicedetails.Type2DeviceDetailsActivity
import com.smartyhome.app.main.home.devicedetails.device4onoff.Device4OnOff
import com.smartyhome.app.main.home.devicedetails.deviceType8.DeviceType8Details
import com.smartyhome.app.main.home.devicedetails.type21.Type21DeviceDetailActivity
import com.smartyhome.app.main.home.movedevices.RoomModel
import com.smartyhome.app.main.home.movedevices.SingleAdapter
import com.smartyhome.app.main.home.schedule.ScheduleListing
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.NetworkUtils
import com.smartyhome.app.utils.SharedPreference
import com.smartyhome.app.utils.ViewModelFactory
import com.smartyhome.app.utils.mqtt.MqttHelper
import kotlinx.android.synthetic.main.activity_device_type2_details.*
import kotlinx.android.synthetic.main.activity_room_device_list.*
import kotlinx.android.synthetic.main.home_header.*
import kotlinx.android.synthetic.main.home_header.tvTitelText
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.android.synthetic.main.layout_room_device_list.*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONException
import org.json.JSONObject


class RoomDeviceList : AppCompatActivity(), View.OnClickListener, DeviceActionInterface {
    val TAG = "MQTT Logs"
    var mqttHelper: MqttHelper? = null
    lateinit var mainModel: RoomDeviceListModel
    var sharedPreference: SharedPreference? = null
    val gson = Gson()
    var roomID = ""
    var roomName = ""
    var deviceInterface: DeviceActionInterface? = null
    var viewType = 1
    var deviceManual: ArrayList<DeviceModelManual>? = null
    var lstDeviceType: List<Type>? = null
    var selectedType = ""
    private val listItems = ArrayList<RoomModel>()
    private var adapter: SingleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_device_list)
        deviceInterface = this
        mqttHelper = MqttHelper()
        getIntentData()
        init()
        initModel()
        if (!roomID.isNullOrEmpty()) {
            getRoomDevices()
            setupListener()
        }
        getDeviceType()
    }

    fun getIntentData() {
        if (intent != null && intent.hasExtra("roomID"))
            roomID = intent.getStringExtra("roomID") ?: ""
        if (intent != null && intent.hasExtra("roomName"))
            roomName = intent.getStringExtra("roomName") ?: ""
    }

    fun initModel() {
        sharedPreference = SharedPreference(this)
        if (sharedPreference?.getBoolean(Constant.isViewTypeGrid) == true) {
            ivChangeView.setImageResource(R.drawable.ic_change_view)
            viewType = 0
        } else {
            ivChangeView.setImageResource(R.drawable.ic_row_view)
            viewType = 1
        }
        val myApi = MyCustomeApi()
        val _repository = ApiCallingRepository(myApi)
        val factory = ViewModelFactory(_repository)
        mainModel = ViewModelProviders.of(this, factory).get(RoomDeviceListModel::class.java)
    }

    fun init() {
        ivleftIcon.setImageResource(R.drawable.ic_back_arrow)
        ivRightIcon.setImageResource(R.drawable.ic_settings)
        tvTitelText.setText(roomName)

        ivleftIcon.setOnClickListener(this)
        ivRightIcon.setOnClickListener(this)
        ivChangeView.setOnClickListener(this)
        pullToRefresh.setOnRefreshListener {
            getRoomDevices()
            pullToRefresh.isRefreshing = false
        }
    }

    fun getRoomDevices() {
        if(NetworkUtils.isNetworkConnected(this)) {
            rlProgressbar.visibility = View.VISIBLE
            mainModel.getDeviceList(
                roomID,
                sharedPreference?.getUDID() ?: "",
                this,
                sharedPreference!!
            )
            mainModel.deviceList.observe(this, Observer { roomDeviceList ->
                val responseInStr = gson.toJson(roomDeviceList)
                sharedPreference?.setString(roomID, responseInStr)
                mainModel.storeDeviceListToPref(sharedPreference!!, roomDeviceList)
                rlProgressbar.visibility = View.GONE
                unsubscribeAllDevice()
                setRoomAdapter(roomDeviceList)
            })
        } else {
            showSnackbar()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            (R.id.ivleftIcon) -> {
                finish()
            }
            (R.id.ivRightIcon) -> {
            }
            (R.id.ivChangeView) -> {
                if (viewType == 1)
                    viewType = 0
                else
                    viewType = 1
                setAdapter()
            }
        }
    }

    fun setRoomAdapter(responseData: RoomDeviceResponse) {
        deviceManual = ArrayList<DeviceModelManual>()
        var isType12Found = false
        for (data in responseData.data) {

            var version : Any = 0
            if(data?.version is Int){
                version = data?.version as Int
            }else if(data?.version is String)
                version = data?.version as String
        else if(data?.version is Double)
                version = data?.version as Double

            val data = DeviceModelManual(
                data?.dno ?: "",
                data?.dtype ?: 0,
                data?.duser ?: "",
                data?.enable?.toString() ?: "",
                data?.ip ?: "",
                data?.isOnline ?: false,
                data?.key ?: "",
                data?.name?:"",
                data?.room ?: "",
                data?.signal ?: 0,
                data?.d1?.state ?: false,
                version,
                data?.d1?.br ?: 0.0,
                getDeviceTypeName(data?.dtype),
                getDrawableIcon(data?.dtype.toString()),
                setSignal(data?.signal),
                data?.d1 ?: null,
                data?.d2 ?: null,
                data?.d3 ?: null,
                data?.d4 ?: null,
                data?.d5 ?: null,
                data?.d6 ?: null,
                data?.d7 ?: null,
                data?.d8 ?: null
            )
            deviceManual?.add(data)
            if (!isType12Found && data?.dtype == 12) {
                ll_power_usage.visibility = View.VISIBLE
            }
        }
        setAdapter()
        subscribeAllDevice()
    }

    fun setAdapter() {
        rvDeviceList.also {
            it.setHasFixedSize(true)
            deviceManual?.let { list ->
                if (viewType == 1) {
                    it.layoutManager = LinearLayoutManager(this)
                    ivChangeView.setImageResource(R.drawable.ic_change_view)
                    it.adapter = RoomDeviceAdapterHorizontal(list, deviceInterface!!)
                    sharedPreference?.setBoolean(Constant.isViewTypeGrid, false)
                } else {
                    it.layoutManager = GridLayoutManager(this, 2)
                    ivChangeView.setImageResource(R.drawable.ic_row_view)
                    it.adapter = RoomDeviceAdapterGrid(list, deviceInterface!!)
                    sharedPreference?.setBoolean(Constant.isViewTypeGrid, true)
                }

            }
        }
    }

    override fun onDeviceClicked(deviceObject: DeviceModelManual,type: String) {
        openDeviceDetail(deviceObject,type)
    }

    override fun onSwitchChecked(
        deviceObject: DeviceModelManual,
        isChecked: Boolean,
        type: String
    ) {
        Log.e("TAG", "Switch Checked $isChecked")
        selectedType = type
        handleSwitchOnOff(deviceObject, isChecked, type)
    }

    override fun showSnackbar(msg: String) {
        Snackbar.make(framlayout1, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun sendFanProgress(deviceObject: DeviceModelManual, type: String, progress: Double,isChecked: Boolean) {
        try {
            val _obj = JsonObject()
            _obj.addProperty("dno", deviceObject?.dno)
            _obj.addProperty("key", deviceObject?.key)
            _obj.addProperty("dtype", deviceObject?.dtype)
            val d4Object = JsonObject()
            d4Object.addProperty("state", isChecked)
            d4Object.addProperty("br", progress)
            d4Object.addProperty("name", deviceObject.d4?.name)
            _obj.add(type, d4Object)

            val gson = Gson()
            val json: String = gson.toJson(_obj)
            Log.e("command", "Send Object in String $json")
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

    fun openDeviceDetail(deviceObject: DeviceModelManual,type:String) {
        when (deviceObject?.dtype) {
            2 -> {
                val intent = Intent(this, Type2DeviceDetailsActivity::class.java)
                startActivityWithIntent(intent, deviceObject,type)
            }
            15 -> {
                val intent = Intent(this, Type15DeviceDetailActivity::class.java)
                startActivityWithIntent(intent, deviceObject,type)
            }
            21 -> {
                val intent = Intent(this, Type21DeviceDetailActivity::class.java)
                startActivityWithIntent(intent, deviceObject,type)
            }
            1, 9, 18, 19, 14, 24, 20, 5 -> {
                val intent = Intent(this, Device4OnOff::class.java)
                startActivityWithIntent(intent, deviceObject,type)
            }
            8, 25,23 -> {
                val intent = Intent(this, DeviceType8Details::class.java)
                startActivityWithIntent(intent, deviceObject,type)
            }
            else -> {
                Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun startActivityWithIntent(intent: Intent, deviceObject: DeviceModelManual,type: String) {
        val gson = Gson()
        val deviceObject: String = gson.toJson(deviceObject)
        intent.putExtra("deviceObject", deviceObject)
        intent.putExtra("type", type)
        startActivity(intent)
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
                Log.e("command", "\n\nReceived \n $msg")
                try {
                    val jsonObject = JSONObject(msg)
                    if (jsonObject.has("dno")) {
                        val dno = jsonObject.getString("dno")
                        updateLocalData(dno, jsonObject)
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "Exceptin while read data ${e.toString()}")
                }
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {}
        })
    }

    fun getDeviceType() {
        val str = sharedPreference?.getString(Constant.deviceType)
        val deviceModel = gson.fromJson<DeviceTypeModel>(str, DeviceTypeModel::class.java)
        lstDeviceType = deviceModel?.Types
    }

    fun getDeviceTypeName(dType: Int): String {
        lstDeviceType?.let {
            for (type in it) {
                if (type.ID == dType)
                    return type?.Name ?: ""
            }
        }
        return ""
    }

    fun getDrawableIcon(dtype: String?): Int {
        var resource = 0
        when (dtype) {
            "1", "5" -> resource = R.drawable.power_socket
            "2" -> resource = R.drawable.rgb_bulb
            "10" -> resource = R.drawable.d_10_icon
            "11" -> resource = R.drawable.open_door
            "12" -> resource = R.drawable.icon_multi_sensor
            else -> resource = R.drawable.smart_device
        }
        return resource
    }

    fun setSignal(strength: Int): Int {
        var signalDawables = R.drawable.signal_zero
        when (strength) {
            0 -> signalDawables = R.drawable.signal_zero
            1 -> signalDawables = R.drawable.signal_one
            2 -> signalDawables = R.drawable.signal_two
            3 -> signalDawables = R.drawable.signal_three
            4 -> signalDawables = R.drawable.signal_four
        }
        return signalDawables
    }

    fun handleSwitchOnOff(deviceObject: DeviceModelManual, isChecked: Boolean, type: String) {
        try {
            val _obj = JsonObject()
            _obj.addProperty("dno", deviceObject?.dno)
            _obj.addProperty("key", deviceObject?.key)
            _obj.addProperty("dtype", deviceObject?.dtype)
            val `object` = JsonObject()
            if (deviceObject.dtype == 25 && type.equals("d4")) {
                `object`.addProperty("br", deviceObject.d4?.br)
            }
            `object`.addProperty("state", isChecked)
            _obj.add(type, `object`)

            val gson = Gson()
            val json: String = gson.toJson(_obj)
            Log.e("command", "Sending \n $json")
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

    override fun onRestart() {
        super.onRestart()
        Log.e("TAG", "Lifecycle onRestart called")
        subscribeAllDevice()
    }

    override fun onStop() {
        super.onStop()
        unsubscribeAllDevice()
    }

    fun subscribeAllDevice() {
        mqttHelper?.let { mqttHelper ->
            Constant.mqttClient?.let { client ->
                deviceManual?.let {
                    for (device in it) {
                        mqttHelper.subscribe(client, "d/" + device.dno + "/pub", 1)
                    }
                }
            }
        }
    }

    fun unsubscribeAllDevice() {
        mqttHelper?.let { mqttHelper ->
            Constant.mqttClient?.let { client ->
                deviceManual?.let {
                    for (device in it) {
                        mqttHelper?.unSubscribe(client, "d/" + device?.dno + "/pub")
                    }
                }
            }
        }
    }

    fun updateLocalData(dno: String, objects: JSONObject) {
       var indexGlobal = 0
        deviceManual?.let {
            it.forEachIndexed { index, element ->
                if (dno == element.dno) {
                    indexGlobal = index
                    if (objects.has("d1")) {
                        var d1 = objects.get("d1")
                        val json = JSONObject(d1.toString())
                        if (json.has("state")) {
                            element.d1?.state = json.getBoolean("state")
                            deviceManual?.get(index)?.d1?.state =json.getBoolean("state")
                        }
                        if(json.has("name")) {
                            element.d1?.name = json.getString("name")
                            deviceManual?.get(index)?.d1?.name = json.getString("name")
                        }
                        if (json.has("br")) {
                            element.d1?.br = json.getDouble("br")
                            deviceManual?.get(index)?.d1?.br = json.getDouble("br")
                        }
                    }
                    if (objects.has("d2")) {
                        val d2 = objects.get("d2")
                        val json = JSONObject(d2.toString())
                        if (json.has("state")) {
                            element.d2?.state = json.getBoolean("state")
                        }
                        if (json.has("name")) {
                            element.d2?.name = json.getString("name")
                        }
                        if (json.has("br")) {
                            element.d2?.br = json.getDouble("br")
                            deviceManual?.get(index)?.d2?.br = json.getDouble("br")
                        }
                    }
                    if (objects.has("d3")) {
                        val d3 = objects.get("d3")
                        val json = JSONObject(d3.toString())
                        if (json.has("state")) {
                            element.d3?.state = json.getBoolean("state")
                        }
                        if (json.has("name")) {
                            element.d3?.name = json.getString("name")
                        }
                        if (json.has("br")) {
                            element.d3?.br = json.getDouble("br")
                            deviceManual?.get(index)?.d3?.br = json.getDouble("br")
                        }
                    }
                    if (objects.has("d4")) {
                        val d4 = objects.get("d4")
                        val json = JSONObject(d4.toString())
                        if (json.has("state")) {
                            element.d4?.state = json.getBoolean("state")
                        }
                        if (json.has("name")) {
                            element.d4?.name = json.getString("name")
                        }
                        if (json.has("br")) {
                            element.d4?.br = json.getDouble("br")
                            deviceManual?.get(index)?.d4?.br = json.getDouble("br")
                        }
                    }
                    if (objects.has("d5")) {
                        val d5 = objects.get("d5")
                        val json = JSONObject(d5.toString())
                        if (json.has("state")) {
                            element.d5?.state = json.getBoolean("state")
                        }
                        if (json.has("name")) {
                            element.d5?.name = json.getString("name")
                        }
                        if (json.has("br")) {
                            element.d5?.br = json.getDouble("br")
                            deviceManual?.get(index)?.d5?.br = json.getDouble("br")
                        }
                    }
                    if (objects.has("d6")) {
                        val d6 = objects.get("d6")
                        val json = JSONObject(d6.toString())
                        if (json.has("state")) {
                            element.d6?.state = json.getBoolean("state")
                        }
                        if (json.has("name")) {
                            element.d6?.name = json.getString("name")
                        }
                        if (json.has("br")) {
                            element.d6?.br = json.getDouble("br")
                            deviceManual?.get(index)?.d6?.br = json.getDouble("br")
                        }
                    }
                    if (objects.has("d7")) {
                        val d7 = objects.get("d7")
                        val json = JSONObject(d7.toString())
                        if (json.has("state")) {
                            element.d7?.state = json.getBoolean("state")
                        }
                        if (json.has("name")) {
                            element.d7?.name = json.getString("name")
                        }
                        if (json.has("br")) {
                            element.d7?.br = json.getDouble("br")
                            deviceManual?.get(index)?.d7?.br = json.getDouble("br")
                        }
                    }
                    if (objects.has("d8")) {
                        val d8 = objects.get("d8")
                        val json = JSONObject(d8.toString())
                        if (json.has("state")) {
                            element.d8?.state = json.getBoolean("state")
                        }
                        if (json.has("name")) {
                            element.d8?.name = json.getString("name")
                        }
                        if (json.has("br")) {
                            element.d8?.br = json.getDouble("br")
                            deviceManual?.get(index)?.d8?.br = json.getDouble("br")
                        }
                    }
                    Log.d("TAG", "Notified data state " + deviceManual?.get(index)?.d1?.state + " name " +deviceManual?.get(index)?.d1?.name)
                    return@forEachIndexed
                }
            }
            Log.d("TAG","Notified data " + deviceManual?.get(indexGlobal)?.d1?.state + " name " +deviceManual?.get(indexGlobal)?.d1?.name)
            rvDeviceList?.adapter?.notifyItemRangeChanged(0, (deviceManual?.size ?: 1 - 1))
        }
    }

    @Override
    override fun showMenu(view: View?, isOffline: Boolean, pos: Int, type: Int,name : String,d1Type:String) {
        val popup = PopupMenu(view?.context, view)
        //Inflating the Popup using xml file
        popup.menuInflater
            .inflate(R.menu.device_setting_menu, popup.menu)

        //registering popup with OnMenuItemClickListener


        /* Intent intent = new Intent(context, rf_remote.class);

        intent.putExtra("dno","84F3EBFBA20C");
        intent.putExtra("type","rf");
        intent.putExtra("mode","0");// "1" for working remote
        context.startActivity(intent);*/popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.delete_d -> {
                }
                R.id.rename_d -> {
                    deviceManual?.let {
                        rename(
                            name,
                            it.get(pos).dno,
                            it.get(pos).key,
                            it.get(pos).dtype,d1Type
                        )
                    }
                }//                        rename(view);
                R.id.move_d -> {
                    moveDevice(pos)
                } //
                R.id.scheduled_d ->{
                    val intent = Intent(this@RoomDeviceList, ScheduleListing::class.java)
                    intent.putExtra("dno", deviceManual?.get(pos)?.dno)
                    startActivity(intent)
                } //objInterface.scheduleDevice(pos, type)
                R.id.menu_logs -> {}
//                    objInterface.view_logs(
//                    pos,
//                    allData.getObjData().get(pos).getDno()
//                )
                R.id.add_in_group ->{}
                /*{
                    val intent = Intent(context, Type21Activity::class.java)
                    intent.putExtra("dno", allData.getObjData().get(pos).getDno())
                    context.startActivity(intent)
                }*/
            }
            true
        }
//        if (allData.getObjData().get(pos).getDtype() !== 21) {
//            popup.menu.removeItem(R.id.add_in_group)
//        }
        /*if (!isOffline) {
            popup.getMenu().removeItem(R.id.move_d);
            popup.getMenu().removeItem(R.id.rename_d);
            popup.getMenu().removeItem(R.id.scheduled_d);
        }
        popup.getMenu().removeItem(R.id.edit_grp);*/
        popup.show()
    }

     fun rename(name : String,dno: String,key:String,dType: Int,d1Type:String) {
        val dialog = Dialog(this)
        val view1: View =
            LayoutInflater.from(this).inflate(R.layout.rename_device_name, null, false)
        dialog.setContentView(view1)
        val deviceName = view1.findViewById<EditText>(R.id.diaEditName)
        val change = view1.findViewById<TextView>(R.id.dia_change)
        val cancel = view1.findViewById<TextView>(R.id.dia_cancel)
        val title = view1.findViewById<TextView>(R.id.tv_title_dialog)
        title.setText(getString(R.string.rename_device))
        deviceName.setText(name)
        cancel.setOnClickListener { v: View? -> dialog.dismiss() }
        change.setOnClickListener { v: View? ->
            try {
                var jsonObject = JsonObject()
                jsonObject.addProperty("dno",dno)
                jsonObject.addProperty("key",key)
                jsonObject.addProperty("dtype",dType)

                var jsonD1 = JsonObject()
                jsonD1.addProperty("name",deviceName.text.toString().trim())
                jsonObject.add(d1Type,jsonD1)

                val gson = Gson()
                val json: String = gson.toJson(jsonObject)
                Log.e("TAG","Json while rename $json")
                mqttHelper?.publishMessage(
                    Constant?.mqttClient!!,
                    json,
                    1,
                    "d/$dno/sub"
                )
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    fun moveDevice(pos: Int){
        if (!NetworkUtils.isNetworkConnected(this)) {
            Toast.makeText(this, getString(R.string.connect_internet), Toast.LENGTH_LONG).show()
            return
        }
        if (deviceManual?.get(pos)?.isOnline?:false) {
            val dno = deviceManual?.get(pos)?.dno
            val factory = LayoutInflater.from(this)
            val dialogView = factory.inflate(R.layout.dialog_move_to, null)
            val dialog = AlertDialog.Builder(this).create()
            dialog.window!!.setBackgroundDrawableResource(android.R.color.white)
            dialog.setView(dialogView)
            val pbar = dialogView.findViewById<ProgressBar>(R.id.pbar)
            createList(deviceManual?.get(pos)?.room ?: "",pbar)
            val recyclerCities: RecyclerView = dialogView.findViewById(R.id.recycler_bottom_cities)
            val btnSubmit = dialogView.findViewById<Button>(R.id.btn_loc_okay)
            val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
            assert(recyclerCities != null)
            recyclerCities.layoutManager = LinearLayoutManager(this)
               adapter = SingleAdapter(this, listItems)
            recyclerCities.adapter = adapter
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnSubmit.setOnClickListener { v: View? ->
                if (adapter?.getSelected() != null) {
                    val jsonObject = JsonObject()
                    jsonObject.addProperty("room", adapter?.getSelected()?.id?:"")
                    val gson = Gson()
                    val json: String = gson.toJson(jsonObject)
                    Log.e("TAG","Json while rename $json")
                    mqttHelper?.publishMessage(
                        Constant?.mqttClient!!,
                        json,
                        1,
                        "d/$dno/sub"
                    )
                    dialog.dismiss()
                    deviceManual?.removeAt(pos)
                    rvDeviceList.adapter?.notifyItemRemoved(pos)
                } else {
                    Toast.makeText(this, "Make at least one selection!", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.show()
        } else {
            Toast.makeText(this, "Device is Offline!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createList(roomID: String,progressBar: ProgressBar) {
        val requestQueue = Volley.newRequestQueue(this)
        val userID: String = sharedPreference?.getUDID() ?: ""
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            "http://209.58.164.151:88/api/Get/getAppHome?UID=$userID",
            null, { response: JSONObject ->
                try {
                    val jsonObject = JSONObject(response.toString())
                    val jsonArray = jsonObject.getJSONArray("rooms")
                    listItems.clear()
                    for (i in 0 until jsonArray.length()) {
                        val listItem = jsonArray.getJSONObject(i)
                        val roomModel = RoomModel()
                        roomModel.setId(listItem.getString("ID"))
                        roomModel.setName(listItem.getString("name"))
                        if (listItem.getString("ID") != roomID) {
                            listItems.add(roomModel)
                        }
                    }
                    adapter?.setEmployees(listItems)
                    progressBar.visibility = View.GONE
                } catch (e: JSONException) {
                    e.printStackTrace()
                    progressBar.visibility = View.GONE
                }
            }) { obj: VolleyError -> obj.printStackTrace() }
        requestQueue.add(jsonObjectRequest)
    }

    var snackbar: Snackbar?=null
    fun showSnackbar(){
        if (snackbar == null) {
            snackbar =
                Snackbar.make(
                    framlayout1!!,
                    getString(R.string.connect_internet),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("RETRY") {
                    if (NetworkUtils.isNetworkConnected(this)) {
                        snackbar?.dismiss()
                        getRoomDevices()
                    }
                }

            snackbar?.show()
        } else if (snackbar != null && snackbar?.isShown == false) {
            snackbar?.show()
        }
    }
}