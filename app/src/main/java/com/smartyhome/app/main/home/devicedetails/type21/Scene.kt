package com.smartyhome.app.main.home.devicedetails.type21

data class Scene(
    val Devices: List<String>,
    val ID: String,
    val Name: String,
    val UID: String,
    var drawable: Int = 0,
    var value_r: Int = 0,
    var value_g: Int = 0,
    var value_b: Int = 0,
    var scene_name: String? = "",
    var selected: Boolean = false,
    var isStaticScene: Boolean = false,
    var groupType: Int = 0
)