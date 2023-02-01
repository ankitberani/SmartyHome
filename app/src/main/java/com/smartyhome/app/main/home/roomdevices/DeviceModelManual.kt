package com.smartyhome.app.main.home.roomdevices

data class DeviceModelManual(
    val dno: String,
    val dtype: Int,
    val duser: String,
    val enable: String,
    val ip: String,
    val isOnline: Boolean,
    val key: String,
    val name: String,
    val room: String,
    val signal: Int,
    val state: Boolean,
    val version: Any,
    val br: Double,
    val deviceTypeName:String,
    val deviceTypeIcon : Int,
    val deviceStrengthIcon : Int,
    val d1 : D1?,
    val d2 : D2?,
    val d3 : D3?,
    val d4 : D4?,
    val d5 : D5?,
    val d6 : D6?,
    val d7 : D7?,
    val d8 : D8?
)