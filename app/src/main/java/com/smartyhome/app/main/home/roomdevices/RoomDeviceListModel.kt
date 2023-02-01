package com.smartyhome.app.main.home.roomdevices

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.SharedPreference
import org.json.JSONException

class RoomDeviceListModel(private val _repository: ApiCallingRepository) : ViewModel() {

    var deviceList: MutableLiveData<RoomDeviceResponse> = MutableLiveData()

    fun getDeviceList(
        roomId: String,
        userId: String,
        context: Context,
        sharedPreference: SharedPreference
    ) {
        var listFromPref = sharedPreference.getString(roomId)
//        if (!listFromPref.isNullOrEmpty()) {
//            val list = Gson().fromJson<RoomDeviceResponse>(listFromPref,RoomDeviceResponse::class.java)
//            deviceList.postValue(list)
//        } else {
            val _url =
                Constant.BASE_URL + "/api/Get/getRoomDevice?UID=" + userId + "&room=" + roomId
            Log.e("TAG","Get Room Device \n $_url")
            val stringRequest = StringRequest(Request.Method.GET, _url, { response ->
                try {
                    val gson = Gson()
                    val response =
                        gson.fromJson<RoomDeviceResponse>(response, RoomDeviceResponse::class.java)
                    deviceList.postValue(response)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error ->
            }
            val requestQueue: RequestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
//        }
    }

    fun storeDeviceListToPref(preference: SharedPreference, response: RoomDeviceResponse) {
        response.data?.let {
            val gson = Gson()
            for (deviceobject in response.data) {
                var deviceDataInStr = gson.toJson(deviceobject)
                preference.setString(deviceobject.dno, deviceDataInStr)
            }
        }
    }


}