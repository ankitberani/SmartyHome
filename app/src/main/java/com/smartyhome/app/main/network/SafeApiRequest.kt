package com.rmindr.app.network


import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.lang.StringBuilder

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        var response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val msg = StringBuilder()
            error?.let {
                try {
                    msg.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                }

                msg.append("\n")
            }
            msg.append(error + "\n")
            msg.append("Error Code : ${response.code()}")

            throw ApiException(msg.toString())
        }
    }

}