package com.smartyhome.app.main.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.rmindr.app.network.MyCustomeApi
import com.smartyhome.app.R
import com.smartyhome.app.main.login.LoginViewModel
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.NetworkUtils
import com.smartyhome.app.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.edtEmail
import kotlinx.android.synthetic.main.activity_register.edtPass
import kotlinx.android.synthetic.main.activity_register.ivEye
import kotlinx.android.synthetic.main.layout_progress_bar.*
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var regModel: RegistrationViewModel
    var passVisibleState = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        FirebaseApp.initializeApp(this)
        val myApi = MyCustomeApi()
        val _repository = ApiCallingRepository(myApi)
        val factory = ViewModelFactory(_repository)
        regModel = ViewModelProviders.of(this, factory).get(RegistrationViewModel::class.java)

        ivEye.setOnClickListener(this)
        tvSignIn.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
        observeRegResponse()
        observeEmailAddress()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            (R.id.tvSignIn) -> {
                finish()
            }
            (R.id.btnSignUp) -> {
                processSignUp()
            }
            (R.id.ivEye) -> {
                if (passVisibleState == 0) {
                    passVisibleState = 1
                    ivEye.setImageResource(R.drawable.ic_eye_icon_off)
                    edtPass.setTransformationMethod(null)
                } else {
                    passVisibleState = 0
                    ivEye.setImageResource(R.drawable.ic_eye_icon)
                    edtPass.setTransformationMethod(PasswordTransformationMethod())
                }
            }
        }
    }

    fun processSignUp() {
        if (edtFullName.text.toString().isNullOrEmpty())
            edtFullName.setError(resources.getString(R.string.required))
        else if (edtLastName.text.toString().isNullOrEmpty())
            edtLastName.setError(resources.getString(R.string.required))
        else if (edtEmail.text.toString().isNullOrEmpty())
            edtEmail.setError(resources.getString(R.string.required))
        else if (!regModel.verifyEmail(edtEmail.text.toString()))
            edtEmail.setError(resources.getString(R.string.invalid_email))
        else if (edtPass.text.toString().isNullOrEmpty())
            edtPass.setError(resources.getString(R.string.required))
        else if (edtPass.text.toString().length < 6)
            edtPass.setError(resources.getString(R.string.text_6_dig_pass_msg))
        else if (edtMobileNumber.text.toString().isNullOrEmpty())
            edtMobileNumber.setError(resources.getString(R.string.required))
        else if (edtMobileNumber.text?.trim().toString().length != 10)
            edtMobileNumber.setError(getString(R.string.text_invalid_number))
        else {
            /*rlProgressbar.visibility = View.VISIBLE
            val req = RegisterReqModel(
                "20/03/1991",
                edtEmail.text.toString(),
                edtFullName.text.toString(),
                edtLastName.text.toString(),
                edtPass.text.toString(),
                edtMobileNumber.text.toString()
            )
            regModel.doSignUp(req)*/
            if (NetworkUtils.isNetworkConnected(this)) {
                var intent = Intent(this, VerifyMobileActivity::class.java)
                intent.setAction("fromReg")
                intent.apply {
                    putExtra("dob", "")
                    putExtra("email", edtEmail.text.toString())
                    putExtra("fullname", edtFullName.text.toString())
                    putExtra("lastname", edtLastName.text.toString())
                    putExtra("pass", edtPass.text.toString())
                    putExtra("mobile", edtMobileNumber.text.toString())
                    startActivity(this)
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.connect_internet),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun observeRegResponse() {
        regModel.regResponse.observe(this, { response ->
            response?.let {
                rlProgressbar.visibility = View.GONE
                Log.e("TAG","reg response $response")

            }
        })
    }

    fun observeEmailAddress() {
        edtEmail.addTextChangedListener(textWatcher)
    }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (regModel.verifyEmail(s.toString()))
                ivTickEmail.visibility = View.VISIBLE
            else
                ivTickEmail.visibility = View.GONE
        }
    }


}