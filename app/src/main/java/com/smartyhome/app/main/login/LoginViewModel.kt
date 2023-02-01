package com.smartyhome.app.main.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.Constant.EMAIL_ADDRESS_PATTERN
import com.smartyhome.app.utils.coroutine

class LoginViewModel(private val repository: ApiCallingRepository) : ViewModel() {

    var loginResponse : MutableLiveData<LoginApiResponse> = MutableLiveData()
    fun verifyEmail(email: String): Boolean {
        return return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    fun doLogin(email: String, password: String) {
        val loginRequestModel = LoginRequestModel(password, email)
        coroutine.main {
            val response = repository.Login(loginRequestModel)
            if (response.body() != null) {
                var loginData: LoginApiResponse = response.body()!!
                loginData.success = true
                loginResponse.postValue(response.body())
            } else
                loginResponse.postValue(
                    LoginApiResponse(
                        success = false,
                        error = response.message()
                    )
                )
        }
    }

}