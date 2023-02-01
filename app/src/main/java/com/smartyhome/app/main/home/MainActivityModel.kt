package com.smartyhome.app.main.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request.Method.GET
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.smartyhome.app.main.home.fragment.home.UpdateRoomNameResponse
import com.smartyhome.app.main.home.roomlisting.RoomData
import com.smartyhome.app.main.home.scene.SceneData
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.WeatherResponse
import com.smartyhome.app.utils.coroutine
import org.json.JSONException

class MainActivityModel(private val repository: ApiCallingRepository) : ViewModel() {

    var renameRoomResponse: MutableLiveData<UpdateRoomNameResponse> = MutableLiveData()
    var weatherResponse: MutableLiveData<WeatherResponse> = MutableLiveData()
    var roomDataResponse: MutableLiveData<RoomData> = MutableLiveData()
    var sceneDataResponse: MutableLiveData<SceneData> = MutableLiveData()
    var deviceType : MutableLiveData<DeviceTypeModel>? = MutableLiveData()

    /*init {
        getDeviceType()
    }*/

    fun getWeather(latitude: String, longitude: String, context: Context) {
        val url =
            "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude.toString() + "&lon=" + longitude.toString() + "&units=metric&appid=c5cdf5c47c8dd7639c29385bcdb062ab"
        Log.e("TAG", "Weather Response $url")
        val stringRequest = StringRequest(GET, url, { response ->
            try {
                val gson = Gson()
                val response = gson.fromJson<WeatherResponse>(response, WeatherResponse::class.java)
                weatherResponse.postValue(response)
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("TAG", "Exception at getWeather " + e.message)
            }
        }) { error ->
            Log.e("TAG", "OnError Called at get weather " + error.toString())
        }
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    fun getRoomList(uID: String) {
        val url = Constant.BASE_URL + "/api/Get/getAppHome?UID=$uID"
        coroutine.main {
            val roomData = repository.GetRoomList(url)
            roomDataResponse.postValue(roomData.body())
        }
    }

    fun getSceneList(uID: String){
        val url = Constant.BASE_URL + "/api/Get/getScene?UID=$uID"
        coroutine.main {
            val sceneData = repository.GetSceneList(url)
            sceneDataResponse.postValue(sceneData.body())
        }
    }

    fun getDeviceType(){
        val url = Constant.BASE_URL + "/api/Get/DType"
        coroutine.main {
            val sceneData = repository.GetDeviceType(url)
            deviceType?.postValue(sceneData.body())
        }
    }


    fun updateRoomName(userID:String,roomID:String,roomName:String){
        val url = Constant.BASE_URL + "/api/Get/updRoom?UID=" + userID + "&roomID=" + roomID + "&RoomName=" + roomName
        coroutine.main {
            val response = repository.RenameRoomName(url)
            renameRoomResponse.postValue(response.body())
        }
    }

}