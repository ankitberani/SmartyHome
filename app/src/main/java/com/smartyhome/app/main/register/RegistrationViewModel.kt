package com.smartyhome.app.main.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.coroutine

class RegistrationViewModel(private val repository: ApiCallingRepository) : ViewModel() {

    var regResponse: MutableLiveData<RegisterResponse> = MutableLiveData()

    fun verifyEmail(email: String): Boolean {
        return return Constant.EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    fun doSignUp(reqModel: RegisterReqModel) {
        coroutine.main {
            val response = repository.Registration(reqModel)
            if (!response.isSuccessful && response.code() == 400) {
                var res = RegisterResponse("Already registered!!",false)
                regResponse.postValue(res)
            }else {
                regResponse.postValue(response.body())
            }
        }
    }
}