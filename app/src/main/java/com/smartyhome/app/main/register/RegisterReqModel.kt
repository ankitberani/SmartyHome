package com.smartyhome.app.main.register

data class RegisterReqModel(
    val DOB: String,
    val Email: String,
    val FirstName: String,
    val LastName: String,
    val Password: String,
    val Phone: String
)