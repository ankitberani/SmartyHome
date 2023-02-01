package com.smartyhome.app.main.home

import com.smartyhome.app.main.home.roomlisting.Room

interface MainInterfaces {

    fun setupLatLong(latitude:Double,longitude:Double)

    fun onRoomClicked(roomObject : Room)

    fun onRoomLongClicked(pos : Int)
}