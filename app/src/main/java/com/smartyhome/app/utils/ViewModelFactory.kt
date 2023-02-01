package com.smartyhome.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smartyhome.app.main.home.MainActivityModel
import com.smartyhome.app.main.home.devicedetails.type21.Type21ViewModel
import com.smartyhome.app.main.home.roomdevices.RoomDeviceListModel
import com.smartyhome.app.main.login.LoginViewModel
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.main.register.RegistrationViewModel

class ViewModelFactory(
    private val _repository: ApiCallingRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java!!)) {
            LoginViewModel(this._repository) as T
        }else if (modelClass.isAssignableFrom(RegistrationViewModel::class.java!!)) {
            RegistrationViewModel(this._repository) as T
        }else if (modelClass.isAssignableFrom(MainActivityModel::class.java!!)) {
            MainActivityModel(this._repository) as T
        }else if (modelClass.isAssignableFrom(RoomDeviceListModel::class.java!!)) {
            RoomDeviceListModel(this._repository) as T
        } else if (modelClass.isAssignableFrom(Type21ViewModel::class.java!!)) {
            Type21ViewModel(this._repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}