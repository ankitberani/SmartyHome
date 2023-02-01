package com.smartyhome.app.main.login

data class LoginApiResponse(
    val UID: String = "",
    val dob: String = "",
    val email: String = "",
    val energyCost: Int = 0,
    val fname: String = "",
    val lat: String = "",
    val lname: String = "",
    val lon: String = "",
    val mobile: String = "",
    val error: String = "",
    var success: Boolean = false,
    val timeZone: Double = 0.0
)