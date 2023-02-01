package com.rmindr.app.network


import com.smartyhome.app.main.home.DeviceTypeModel
import com.smartyhome.app.main.home.devicedetails.type21.Type21ResponseModel
import com.smartyhome.app.main.home.fragment.home.UpdateRoomNameResponse
import com.smartyhome.app.main.home.fragment.profile.UpdateProfileResponse
import com.smartyhome.app.main.home.fragment.profile.profilemodel
import com.smartyhome.app.main.home.roomlisting.RoomData
import com.smartyhome.app.main.home.scene.SceneData
import com.smartyhome.app.main.login.LoginApiResponse
import com.smartyhome.app.main.login.LoginRequestModel
import com.smartyhome.app.main.register.RegisterReqModel
import com.smartyhome.app.main.register.RegisterResponse
import com.smartyhome.app.utils.Constant
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface MyCustomeApi {


    @POST("/api/Login/userLogin")
    suspend fun Login(@Body loginApiRequest: LoginRequestModel): Response<LoginApiResponse>

    @POST("/api/UserRegistration/newUserRegistration")
    suspend fun Registration(@Body reqModel: RegisterReqModel): Response<RegisterResponse>

    @GET
    suspend fun GetRoomList(@Url getRoomUrl: String): Response<RoomData>

    @GET
    suspend fun GetSceneList(@Url getSceneUrl: String): Response<SceneData>

    @GET
    suspend fun GetGroupList(@Url getGrpUrl: String): Response<Type21ResponseModel>

    @GET
    suspend fun GetDeviceType(@Url url: String): Response<DeviceTypeModel>


    @GET
    suspend fun UpdateRoomName(@Url url: String): Response<UpdateRoomNameResponse>


    @POST("/api/Login/userUpdate")
    suspend fun UpdateProfile(@Body loginApiRequest: profilemodel): Response<UpdateProfileResponse>

    @GET
    suspend fun ResetPassword(@Url url: String): Response<UpdateRoomNameResponse>


    companion object {
        operator fun invoke(): MyCustomeApi {
            val _httpclient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(_httpclient)
                .build()
                .create(MyCustomeApi::class.java)
        }
    }
}