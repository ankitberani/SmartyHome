package com.smartyhome.app.main.home.fragment.home

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.rmindr.app.network.MyCustomeApi
import com.smartyhome.app.R
import com.smartyhome.app.main.home.MainActivityModel
import com.smartyhome.app.main.home.MainInterfaces
import com.smartyhome.app.main.home.roomdevices.RoomDeviceList
import com.smartyhome.app.main.home.roomdevices.RoomDeviceResponse
import com.smartyhome.app.main.home.roomlisting.Room
import com.smartyhome.app.main.home.roomlisting.RoomListAdapter
import com.smartyhome.app.main.home.roomlisting.SceneListAdapter
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.*
import com.smartyhome.app.utils.mqtt.MqttHelper
import kotlinx.android.synthetic.main.activity_device_type8_details.*
import kotlinx.android.synthetic.main.activity_room_device_list.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.layout_room_list.*
import kotlinx.android.synthetic.main.layout_scene_list.*
import kotlinx.android.synthetic.main.layout_weather.*
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), MainInterfaces, View.OnClickListener {

    var locationUtils: LocationUtils? = null
    lateinit var mainModel: MainActivityModel
    var _interface: MainInterfaces? = null
    var sharedPreference: SharedPreference? = null
    var allRoomList: ArrayList<Room>? = null
    var tvAddRoom: TextView? = null
    var fmMain: FrameLayout? = null
    private var progressDialog: ProgressDialog? = null
    val TAG = "MQTT Logs"

    var mqttHelper: MqttHelper? = null
    var updateRoomPos = -1;
    var updateRoomName = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        tvAddRoom = view.findViewById(R.id.tvAddRoom)
        fmMain = view.findViewById(R.id.fmMain)
        tvAddRoom?.setOnClickListener(this)
        initModel()
        observeDeviceType()
        startgps()
        observeScenes()
        authenticateMQTT()
        progressDialog = ProgressDialog(requireContext())
        progressDialog?.requestWindowFeature(1)
        mqttHelper = MqttHelper()
        observerRename()
        setupListener()
        return view
    }

    fun initModel() {
        sharedPreference = SharedPreference(requireContext())
        _interface = this
        val myApi = MyCustomeApi()
        val _repository = ApiCallingRepository(myApi)
        val factory = ViewModelFactory(_repository)
        mainModel = ViewModelProviders.of(this, factory).get(MainActivityModel::class.java)
        if (NetworkUtils.isNetworkConnected(activity)) {
            mainModel.getDeviceType()
        } else {
            showSnackbar()
        }
    }

    fun startgps(): String? {
        locationUtils = LocationUtils(requireContext(), _interface)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                "android.permission.ACCESS_FINE_LOCATION"
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                requireContext(),
                "android.permission.ACCESS_COARSE_LOCATION"
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationUtils?.fn_getlocation()
        }
        if (VERSION.SDK_INT >= 23) {
            requestPermissions(
                arrayOf(
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.INTERNET"
                ), 10
            )
        }
        return "NA"
    }


    fun observeWeather() {
        if (NetworkUtils.isNetworkConnected(activity)) {
            mainModel?.weatherResponse.observe(requireActivity(), Observer { weatherResponse ->
                weatherResponse?.let {
                    tvOutsideTemp.setText(it.main.temp.toString())
                    val ic: String = weatherResponse.weather.get(0).icon
                    val IconUrl = "http://openweathermap.org/img/wn/$ic@4x.png"
                    Glide.with(this).load(IconUrl).into(ivwethericon)
                }
            })
        } else {
            showSnackbar()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                "android.permission.ACCESS_FINE_LOCATION"
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                requireContext(),
                "android.permission.ACCESS_COARSE_LOCATION"
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (NetworkUtils.isNetworkConnected(requireContext())) {
                mainModel.getWeather(
                    latitude = locationUtils?.lat.toString(),
                    longitude = locationUtils?.longi.toString(),
                    requireContext()
                )
            } else {
                showSnackbar()
            }
            observeWeather()
        }
    }

    override fun setupLatLong(latitude: Double, longitude: Double) {
        if (NetworkUtils.isNetworkConnected(activity)) {
            mainModel.getWeather(
                latitude = latitude.toString(),
                longitude = longitude.toString(),
                requireContext()
            )
        }else{
            showSnackbar()
        }
        observeWeather()
    }

    override fun onRoomClicked(roomObject: Room) {
        val _intent = Intent(requireContext(), RoomDeviceList::class.java)
        _intent.apply {
            putExtra("roomID", roomObject?.ID)
            putExtra("roomName", roomObject?.name)
        }
        startActivity(_intent)
    }

    override fun onRoomLongClicked(pos: Int) {
        updateRoom(allRoomList?.get(pos)?.ID ?: "", allRoomList?.get(pos)?.name ?: "", pos)
    }

    fun observeRoomList() {
        val uID = sharedPreference?.getUDID() ?: ""
        mainModel.getRoomList(uID)
        if (isAdded)
            mainModel.roomDataResponse?.observe(requireActivity(), Observer {
                it?.let {
                    setRoomAdapter(it.rooms)
                }
            })
    }

    fun setRoomAdapter(roomList: ArrayList<Room>) {
        allRoomList = roomList
        rvRoomList.also {
            it.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            it.setHasFixedSize(true)
            for (roomObj in roomList) {
                val deviceListFromPref = sharedPreference?.getString(roomObj.ID)
                if (!deviceListFromPref.isNullOrEmpty()) {
                    val deviceCount = Gson().fromJson<RoomDeviceResponse>(
                        deviceListFromPref,
                        RoomDeviceResponse::class.java
                    )?.data?.size ?: 0
                    roomObj.deviceCount = deviceCount
                }
                val roomId = roomObj.ID + "_clr"
                roomObj.background =
                    sharedPreference?.getString(roomId ?: "")
            }
            it.adapter = RoomListAdapter(roomList, _interface!!)
        }
    }

    fun observeScenes() {
        if (NetworkUtils.isNetworkConnected(activity)) {
            val uID = sharedPreference?.getUDID() ?: ""
            mainModel.getSceneList(uID)
            mainModel.sceneDataResponse.observe(requireActivity(), Observer {
                setSceneAdapter(it.Scene)
                observeRoomList()
            })
        } else {
            showSnackbar()
        }
    }

    fun setSceneAdapter(sceneList: List<com.smartyhome.app.main.home.scene.Scene>) {
        rvSceneList.also {
            it?.let {
                it.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                it.setHasFixedSize(true)
                it.adapter = SceneListAdapter(sceneList)
            }
        }
    }

    fun authenticateMQTT() {
        if(NetworkUtils.isNetworkConnected(activity)) {
            val mqttHelp = MqttHelper()
            Constant.mqttClient =
                mqttHelp.getHomeMqttClientAuthenticate(requireContext(), Constant.MQTT_BROKER_URL)
        }else{
            showSnackbar()
        }
    }

    fun observeDeviceType() {
        if(NetworkUtils.isNetworkConnected(requireContext())) {
            mainModel.deviceType?.observe(requireActivity(), Observer {
                val str = Gson().toJson(it)
                sharedPreference?.setString(Constant.deviceType, str)
            })
        }else{
            showSnackbar()
        }
    }

    private inner class ViewHolder internal constructor() {
        val mDrawerLayout: DuoDrawerLayout
        val mDuoMenuView: DuoMenuView

        init {
            mDrawerLayout = view?.findViewById<View>(R.id.drawer) as DuoDrawerLayout
            mDuoMenuView = mDrawerLayout.menuView as DuoMenuView
        }
    }


    fun addRoom() {
        val dialog = Dialog(requireContext())
        val view1 =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_addroom, null, false)

        val tvSelectClr = view1.findViewById<TextView>(R.id.tvSelectClr)
        val ll_clr_1 = view1.findViewById<LinearLayout>(R.id.ll_clr_1)
        val ll_clr_2 = view1.findViewById<LinearLayout>(R.id.ll_clr_2)

        tvSelectClr.visibility = View.GONE
        ll_clr_1.visibility = View.GONE
        ll_clr_2.visibility = View.GONE

        val editText = view1.findViewById<EditText>(R.id.diaEditName)
        val textView = view1.findViewById<TextView>(R.id.roomName)

        cardRoom_dialog = view1.findViewById<RelativeLayout>(R.id.cardRoom)
        setOnClick(view1)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                textView.text = s.toString()
                //                dia_name[0] = s.toString();
            }
        })
        val dia_add = view1.findViewById<Button>(R.id.dia_update)
        val dia_close = view1.findViewById<Button>(R.id.dia_close)
        dia_add.text = "ADD"
        dia_add.setOnClickListener { v: View? ->
            if (isNameExist(editText.text.toString().trim { it <= ' ' }, -1)) {
                return@setOnClickListener
            }
            if (editText.text.toString().trim { it <= ' ' }.isEmpty()) {
                editText.error = "Required!"
                return@setOnClickListener
            }
            if (editText.text.toString().trim { it <= ' ' } == "null") {
                Toast.makeText(requireContext(), "Invalid Name!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val view2 =
                LayoutInflater.from(requireContext()).inflate(R.layout.card_rooms, null, false)
            val textView1 = view2.findViewById<TextView>(R.id.roomName)
            textView1.text = editText.text.toString().trim { it <= ' ' }
            addRoom(editText.text.toString())
            dialog.dismiss()
        }
        dialog.setContentView(view1)
        dia_close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun isNameExist(roomName: String?, position: Int): Boolean {
        allRoomList?.let {
            for (i in 0 until it.size) {
                if (position != i && it.get(i).name.equals(roomName, true)
                ) {
                    Toast.makeText(requireContext(), "Name Already Exist!", Toast.LENGTH_SHORT)
                        .show()
                    return true
                }
            }
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvAddRoom -> {
                addRoom()
            }
            R.id.gradone,
            R.id.gradtwo,
            R.id.gradthree,
            R.id.gradfour,
            R.id.gradfive,
            R.id.gradsix,
            R.id.gradseven,
            R.id.gradeight,
            R.id.gradnine,
            R.id.gradten ->
                getDradient(v)
        }
    }

    var _position_dialog = 0

    fun updateRoom(Id: String?, roomName: String, position: Int) {
        val builder: AlertDialog.Builder
        builder = if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) AlertDialog.Builder(
            requireContext(),
            android.R.style.Theme_Material_Dialog_Alert
        ) else AlertDialog.Builder(requireContext())
        _position_dialog = position
        builder.setTitle("Attention !")
            .setMessage("Do you want to Edit or Remove $roomName Room ?")
            .setPositiveButton("Remove") { dialog, which ->
                if (allRoomList?.size == 0) {
                    Toast.makeText(requireContext(), "Operation failed!", Toast.LENGTH_SHORT)
                        .show()
                    return@setPositiveButton
                }
                removeRoom(Id)
                dialog.dismiss()
            }
            .setNegativeButton("Edit") { dialog, which ->
                editRoom(Id ?: "", roomName, position)
                dialog.dismiss()
            }
            .setNeutralButton(
                "cancel",
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                    _position_dialog = 0
                })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    var cardRoom_dialog: RelativeLayout? = null
    var _drawable_nm = ""
    var newly_added_room_name = ""

    private fun editRoom(roomId: String, roomName: String, position: Int) {
        Log.e("TAG", "Position of item $position <> $roomName  <> $roomId")
        val dialog = Dialog(requireContext())
        val dia_name = arrayOfNulls<String>(1)
        val view1 =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_addroom, null, false)
        setOnClick(view1)
        cardRoom_dialog = view1.findViewById<RelativeLayout>(R.id.cardRoom)
        cardRoom_dialog?.setTag(allRoomList?.get(position)?.background)
        if (allRoomList?.get(position)?.background == null)
            cardRoom_dialog?.setBackgroundDrawable(
                resources.getDrawable(R.drawable.gradtwo)
            ) else cardRoom_dialog?.setBackgroundDrawable(
            getDrawableByName(allRoomList?.get(position)?.background, requireContext())
        )
        _drawable_nm = ""
        val editText = view1.findViewById<EditText>(R.id.diaEditName)
        editText.setText(roomName)
        dia_name[0] = roomName
        val textView = view1.findViewById<TextView>(R.id.roomName)
        textView.text = roomName
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                textView.text = s.toString()
                dia_name[0] = s.toString()
            }
        })
        val dia_update = view1.findViewById<Button>(R.id.dia_update)
        val dia_close = view1.findViewById<Button>(R.id.dia_close)
        dia_close.setOnClickListener {
            dialog.dismiss()
        }
        dia_update.setOnClickListener { v: View? ->
            if (!isNameExist(editText.text.toString().trim { it <= ' ' }, position)) {
                showProgressDialog("Updating...")
                updateRoomName(
                    roomId,
                    editText.text.toString().trim { it <= ' ' },
                    position,
                    _drawable_nm
                )
                Log.e(
                    "TAG",
                    "Edit Room RoomID $roomId _drawable_nm $_drawable_nm"
                )
                dialog.dismiss()
            }
        }
        dialog.setContentView(view1)
        dialog.show()
    }

    fun showProgressDialog(msg: String?) {
        try {
            if (this.progressDialog != null && this.progressDialog?.isShowing() ?: false) {
                this.progressDialog?.setMessage(msg)
                progressDialog?.setCanceledOnTouchOutside(false)
                //                progressDialog.setCancelable(false);
                this.progressDialog?.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getDradient(view: View) {
        try {
            val dia_backdrawable = getDrawableByName(view.tag.toString(), requireContext())
            cardRoom_dialog!!.background = dia_backdrawable
            cardRoom_dialog!!.tag = dia_backdrawable
            _drawable_nm = view.tag.toString()
        } catch (e: Exception) {
            Log.e("TAG", "Exception e " + e.message)
        }
    }

    fun setOnClick(view: View) {
        val gradone = view.findViewById<View>(R.id.gradone)
        gradone.setOnClickListener(this)

        val gradtwo = view.findViewById<View>(R.id.gradtwo)
        gradtwo.setOnClickListener(this)

        val gradthree = view.findViewById<View>(R.id.gradthree)
        gradthree.setOnClickListener(this)

        val gradfour = view.findViewById<View>(R.id.gradfour)
        gradfour.setOnClickListener(this)

        val gradfive = view.findViewById<View>(R.id.gradfive)
        gradfive.setOnClickListener(this)

        val gradsix = view.findViewById<View>(R.id.gradsix)
        gradsix.setOnClickListener(this)

        val gradseven = view.findViewById<View>(R.id.gradseven)
        gradseven.setOnClickListener(this)

        val gradeight = view.findViewById<View>(R.id.gradeight)
        gradeight.setOnClickListener(this)

        val gradnine = view.findViewById<View>(R.id.gradnine)
        gradnine.setOnClickListener(this)

        val gradten = view.findViewById<View>(R.id.gradten)
        gradten.setOnClickListener(this)

    }

    fun updateRoomName(
        _id: String,
        name: String,
        position: Int,
        _drawable_nm: String
    ) {
        updateRoomPos = position
        updateRoomName = name
        if (!_drawable_nm.isNullOrEmpty()) {
            Log.e("TAG", "Drawable name $_drawable_nm")
            sharedPreference?.setString(_id + "_clr", _drawable_nm)
            allRoomList?.forEachIndexed { index, room ->
                if (room.ID.equals(_id)) {
                    room.background = _drawable_nm
                    rvRoomList.adapter?.notifyItemRangeChanged(0, allRoomList?.size?.minus(1) ?: 0)
                }
            }
        }
        mainModel.updateRoomName(sharedPreference?.getUDID() ?: "", _id, name)
    }

    fun getDrawableByName(gradename: String?, context: Context): Drawable? {
        val resources = context.resources
        val resourceId = resources.getIdentifier(
            gradename, "drawable",
            context.packageName
        )
        return resources.getDrawable(resourceId)
    }

    fun observerRename() {
        mainModel.renameRoomResponse.observe(requireActivity(), Observer { result ->
            if (result.success ?: false) {
                allRoomList?.get(updateRoomPos)?.name = updateRoomName
//                rvRoomList.adapter?.notifyItemRangeChanged(0,allRoomList?.size?.minus(1)?:0)
                allRoomList?.let {
                    setRoomAdapter(it)
                    Handler(Looper.getMainLooper()).post {
                        rvRoomList.smoothScrollToPosition(updateRoomPos)
                    }
                }
            }
        })
    }

    fun removeRoom(roomId: String?){
        try {
            val _obj = JsonObject()
            _obj.addProperty("method", "deleteRoom")

            val `object` = JsonObject()
            `object`.addProperty("room", roomId)
            _obj.add("data", `object`)
            val gson = Gson()
            val json: String = gson.toJson(_obj)
            Log.e("removeRoom", "removeRoom Object in String $json")
            mqttHelper?.publishMessage(
                Constant?.mqttClient!!,
                json,
                1,
                "u/" + sharedPreference?.getUDID() + "/pub"
            )

            allRoomList?.forEachIndexed { index, room ->
                if (room.ID.equals(roomId)) {
                    rvRoomList.adapter?.notifyItemRemoved(index)
                    allRoomList?.removeAt(index)
                    return
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAGGG", "Exception at TurnOnOffDevice Devices " + e.message, e)
        }
    }

    fun addRoom(roomName: String){
        try {
            val _obj = JsonObject()
            _obj.addProperty("method", "addRoom")

            val `object` = JsonObject()
            `object`.addProperty("name", roomName)
            _obj.add("data", `object`)
            val gson = Gson()
            val json: String = gson.toJson(_obj)
            Log.e("addRoom", "addRoom Object in String $json")
            mqttHelper?.publishMessage(
                Constant?.mqttClient!!,
                json,
                1,
                "u/" + sharedPreference?.getUDID() + "/pub"
            )
            val uID = sharedPreference?.getUDID() ?: ""
            mainModel.getRoomList(uID)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAGGG", "Exception at TurnOnOffDevice Devices " + e.message, e)
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
                    val jsonObject = JSONObject(msg)
                    if (jsonObject.has("method")) {
                        if (jsonObject.getString("method").equals("deleteRoom")) {
                            if (jsonObject.has("data")) {
                                val data = jsonObject.getJSONObject("data")
                                if (data.has("room")) {
                                    val roomID = data.getString("room")
                                    allRoomList?.forEachIndexed { index, room ->
                                        if (room.ID.equals(roomID)) {
                                            rvRoomList.adapter?.notifyItemRemoved(index)
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "Exceptin while read data ${e.toString()}")
                }
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {}
        })
    }

    var snackbar: Snackbar?=null
    fun showSnackbar(){
        if (snackbar == null) {
            snackbar =
                Snackbar.make(
                    fmMain!!,
                    getString(R.string.connect_internet),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("RETRY") {
                    if (NetworkUtils.isNetworkConnected(activity)) {
                        mainModel.getWeather(
                            latitude = locationUtils?.lat.toString(),
                            longitude = locationUtils?.longi.toString(),
                            requireContext()
                        )
                        mainModel.getDeviceType()
                        authenticateMQTT()
                        observeWeather()
                        observeScenes()
                        snackbar?.dismiss()
                    }
                }

            snackbar?.show()
        } else if (snackbar != null && snackbar?.isShown == false) {
            snackbar?.show()
        }
    }
}