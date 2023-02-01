package com.smartyhome.app.main.home.fragment.home

import com.google.gson.annotations.SerializedName

data class UpdateRoomNameResponse(
    @SerializedName("success")
    var success: Boolean? = false

)