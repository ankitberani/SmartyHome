package com.smartyhome.app.main.forgetpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.rmindr.app.network.MyCustomeApi
import com.smartyhome.app.R
import com.smartyhome.app.main.home.fragment.home.UpdateRoomNameResponse
import com.smartyhome.app.main.login.LoginActivity
import com.smartyhome.app.main.login.LoginApiResponse
import com.smartyhome.app.main.login.LoginRequestModel
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.SharedPreference
import com.smartyhome.app.utils.coroutine
import kotlinx.android.synthetic.main.activity_forgotpass.*
import kotlinx.android.synthetic.main.activity_forgotpass.ivBack
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_reset_pass.*
import kotlinx.android.synthetic.main.layout_progress_bar.*

class ResetPassActivity : AppCompatActivity(),View.OnClickListener {

    var mobile = ""
    var otpCode = ""
    var fromReset : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)
        ivBack.setOnClickListener(this)
        btnSave.setOnClickListener(this)

        intent?.let {
            mobile = it.getStringExtra("mobileNumber") ?: ""
            otpCode = it.getStringExtra("otpCode") ?: ""
            if (it.action.equals("fromReset"))
                fromReset = true
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            (R.id.ivBack) -> {
                finish()
            }
            (R.id.btnSave) -> {
                if (edtRePass.text.toString().isNullOrEmpty())
                    edtRePass.setError(getString(R.string.required))
                else if (edtRePass.text.toString().length < 6) {
                    edtRePass.setError(getString(R.string.text_6_dig_pass_msg))
                } else if (edtCnfmPass.text.toString().isNullOrEmpty())
                    edtCnfmPass.setError(getString(R.string.required))
                else if (edtCnfmPass.text.toString().length < 6) {
                    edtCnfmPass.setError(getString(R.string.text_6_dig_pass_msg))
                } else if (!edtRePass.text.toString().equals(edtCnfmPass.text.toString()))
                    edtCnfmPass.setError("Not match!")
                else
                    resetPass()
            }
        }
    }

    fun resetPass() {
        val myApi = MyCustomeApi()
        val _repository = ApiCallingRepository(myApi)
        rlProgressbar.visibility = View.VISIBLE
        coroutine.main {
            val url =
                Constant.BASE_URL.plus("/api/UserRegistration/VerifyRequestOTP?mobile=$mobile&OTPCode=$otpCode&pass=${edtRePass.text.toString()}")
            Log.e("TAG", "reset pass url $url")
            val response = _repository.ResetPassword(url)
            if (response.body() != null) {
                var response: UpdateRoomNameResponse = response.body()!!
                if (response?.success ?: false) {
                    var sharedPreference: SharedPreference =
                        SharedPreference(this@ResetPassActivity)
                    sharedPreference.setString(Constant.LOGIN_INFO, "")
                    sharedPreference?.setString(Constant.PASSWORD, "")
                    var _intent = Intent(this, LoginActivity::class.java)
                    _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(_intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@ResetPassActivity,
                        "Something went wrong! ${response?.success}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            } else {
                Toast.makeText(this@ResetPassActivity, "Something went wrong!", Toast.LENGTH_LONG)
                    .show()
            }
            rlProgressbar.visibility = View.GONE
        }
    }
}