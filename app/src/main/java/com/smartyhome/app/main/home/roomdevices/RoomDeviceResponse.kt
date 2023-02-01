package com.smartyhome.app.main.home.roomdevices

data class RoomDeviceResponse(
    val `data`: List<Data>,
    val data_method: String,
    val groups: String,
    val rooms: List<RoomX>
)