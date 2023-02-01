package com.smartyhome.app.main.home.devicedetails.type21

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.SharedPreference
import com.smartyhome.app.utils.coroutine

class Type21ViewModel(private val repository: ApiCallingRepository) : ViewModel() {

    var grpDataResponse: MutableLiveData<Type21ResponseModel> = MutableLiveData()
    var preference : SharedPreference?=null
    var dno  = ""
    fun getGroupSceneList(uID: String, pref: SharedPreference, deviceNumber: String) {
        preference = pref
        dno = deviceNumber
        val url = Constant.BASE_URL + "/api/Get/getGroup?UID=$uID"
        coroutine.main {
            val roomData = repository.GetGroupSceneList(url)
            grpDataResponse.postValue(roomData.body())
        }
    }

    fun setSelected(pos : Int){
        val response = grpDataResponse.value
        if (response?.Scene?.get(pos)?.selected ?: false)
            response?.Scene?.get(pos)?.selected = false
        else
            response?.Scene?.get(pos)?.selected = true
        storeSelectedGrpID(response?.Scene!!)
        grpDataResponse.postValue(response)
    }

    fun storeSelectedGrpID(sceneList : List<Scene>) {
        var strArray = ArrayList<String>()
        sceneList.let { list ->
            for (obj in list!!) {
                if (obj.selected) {
                    strArray.add(obj.ID)
                }
            }
        }
        val stringArray: String = strArray.toString()
        Log.e("TAG", "Stored In Array $stringArray")
        preference?.setString(Constant.Group + "${dno}", stringArray)
    }
}