package com.smartyhome.app.main.network

import com.rmindr.app.network.MyCustomeApi
import com.smartyhome.app.main.home.fragment.profile.profilemodel
import com.smartyhome.app.main.login.LoginRequestModel
import com.smartyhome.app.main.register.RegisterReqModel

class ApiCallingRepository(
    private val myCustomeApi: MyCustomeApi
) {


    suspend fun Login(loginApiRequest: LoginRequestModel) = myCustomeApi.Login(loginApiRequest)
    suspend fun Registration(requestModel: RegisterReqModel) =
        myCustomeApi.Registration(requestModel)

    suspend fun GetRoomList(requestUrl: String) = myCustomeApi.GetRoomList(requestUrl)
    suspend fun GetSceneList(requestUrl: String) = myCustomeApi.GetSceneList(requestUrl)
    suspend fun GetGroupSceneList(requestUrl: String) = myCustomeApi.GetGroupList(requestUrl)
    suspend fun GetDeviceType(requestUrl: String) = myCustomeApi.GetDeviceType(requestUrl)
    suspend fun RenameRoomName(requestUrl: String) = myCustomeApi.UpdateRoomName(requestUrl)
    suspend fun UpdateProfile(model: profilemodel) = myCustomeApi.UpdateProfile(model)
    suspend fun ResetPassword(requestUrl: String) = myCustomeApi.ResetPassword(requestUrl)
}