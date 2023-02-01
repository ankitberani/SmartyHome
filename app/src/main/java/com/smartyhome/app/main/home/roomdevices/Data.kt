package com.smartyhome.app.main.home.roomdevices

data class Data(
    val arm: Int,
    var d1: D1,
    val d2: D2,
    val d3: D3,
    val d4: D4,
    val d5: D5,
    val d6: D6,
    val d7: D7,
    val d8: D8,
    val dno: String,
    val dtype: Int,
    val duser: String,
    val enable: Any,
    val ip: String,
    val ir: List<Any>,
    val isOnline: Boolean,
    val key: String,
    val name: String,
    val rf: List<Any>,
    val room: String,
    val signal: Int,
    val version: Any
)