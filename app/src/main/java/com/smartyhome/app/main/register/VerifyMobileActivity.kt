package com.smartyhome.app.main.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.rmindr.app.network.MyCustomeApi
import com.smartyhome.app.R
import com.smartyhome.app.main.forgetpass.ResetPassActivity
import com.smartyhome.app.main.login.LoginActivity
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.NetworkUtils
import com.smartyhome.app.utils.ViewModelFactory
import com.smartyhome.app.utils.coroutine
import kotlinx.android.synthetic.main.activity_verify_mobile.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import java.util.concurrent.TimeUnit

class VerifyMobileActivity : AppCompatActivity(), View.OnClickListener {

    var dob = ""
    var email = ""
    var fullname = ""
    var lastname = ""
    var pass = ""
    var mobile = ""
    var mobileWithoutCode = ""
    var verificationId = ""
    private var mAuth: FirebaseAuth? = null
    private lateinit var regModel: RegistrationViewModel
    var isFromReg = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_mobile)
        mAuth = FirebaseAuth.getInstance()
        intent?.let {
            if (it.action.equals("fromReg")) {
                isFromReg= true
                dob = it.getStringExtra("dob") ?: ""
                email = it.getStringExtra("email") ?: ""
                fullname = it.getStringExtra("fullname") ?: ""
                lastname = it.getStringExtra("lastname") ?: ""
                pass = it.getStringExtra("pass") ?: ""
            }
            mobile = it.getStringExtra("mobile") ?: ""
            mobileWithoutCode = mobile
            if (!mobile.startsWith("+91"))
                mobile = "+91".plus(mobile)
            rlProgressbar.visibility = View.VISIBLE
            sendVerificationCode(mobile)
        }
        val myApi = MyCustomeApi()
        val _repository = ApiCallingRepository(myApi)
        val factory = ViewModelFactory(_repository)
        regModel = ViewModelProviders.of(this, factory).get(RegistrationViewModel::class.java)
        observeRegResponse()
        ivBack.setOnClickListener(this)
        btnValidate.setOnClickListener(this)
        tvResendCode.setOnClickListener(this)
    }

    private fun sendVerificationCode(number: String) {
        val phoneAuthOptions = PhoneAuthOptions.newBuilder()
            .setPhoneNumber(number)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .setActivity(this)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            rlProgressbar.visibility = View.GONE
            Toast.makeText(
                this@VerifyMobileActivity,
                "onVerificationCompleted called",
                Toast.LENGTH_LONG
            ).show()
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in response to invalid requests for
            // verification, like an incorrect phone number.
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }
            rlProgressbar.visibility = View.GONE
            // Show a message and update the UI
            // ...
            Toast.makeText(this@VerifyMobileActivity, "Failed!", Toast.LENGTH_LONG)
                .show()
            finish()
        }

        override fun onCodeSent(
            s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken
        ) {
            verificationId = s
            tvHead.setText(getString(R.string.a_verification_code_sent, mobile))
            rlProgressbar.visibility = View.GONE
            // The SMS verification code has been sent to the provided phone number.
            // We now need to ask the user to enter the code and then construct a
            // credential by combining the code with a verification ID.
            // Save the verification ID and resending token for later use.
            Toast.makeText(this@VerifyMobileActivity, "Code Sent Successfully", Toast.LENGTH_LONG)
                .show()
            btnValidate.isEnabled = true
            btnValidate.isClickable = true
            tvCodeTimer.visibility = View.VISIBLE
            tvResendCode.visibility = View.INVISIBLE
            setupTimer()
        }
    }

    fun setupTimer() {
        object : CountDownTimer(30000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                tvCodeTimer.setText("00:" + millisUntilFinished / 1000)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                tvCodeTimer.visibility = View.INVISIBLE
                tvResendCode.visibility = View.VISIBLE
            }
        }.start()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvResendCode -> {
                if (NetworkUtils.isNetworkConnected(this)) {
                    edtEnterCode.setText("")
                    rlProgressbar.visibility = View.VISIBLE
                    sendVerificationCode(mobile)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.connect_internet),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            R.id.btnValidate -> {
                if (NetworkUtils.isNetworkConnected(this)) {
                    if (!edtEnterCode.text.toString().trim().isNullOrEmpty()) {
                        val credential =
                            PhoneAuthProvider.getCredential(
                                verificationId,
                                edtEnterCode.text.toString()
                            )
                        tvCodeTimer.visibility = View.INVISIBLE
                        rlProgressbar.visibility = View.VISIBLE
                        signInWithCredential(credential)
                    } else {
                        edtEnterCode.setError(getString(R.string.required))
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.connect_internet),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            R.id.ivBack -> {
                finish()
            }
        }
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(OnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    if (isFromReg)
                        createUser()
                    else {
                        var intent = Intent(this, ResetPassActivity::class.java)
                        intent.putExtra("mobileNumber", mobileWithoutCode)
                        intent.putExtra("otpCode", edtEnterCode.text.toString())
                        startActivity(intent)
                    }
                    Toast.makeText(this@VerifyMobileActivity, "Verified!!", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        this@VerifyMobileActivity,
                        task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                rlProgressbar.visibility = View.GONE
            })
    }

    private fun createUser() {
        //            rlProgressbar.visibility = View.VISIBLE
        rlProgressbar.visibility = View.VISIBLE
        val req = RegisterReqModel(
            "20/03/1991",
            email,
            fullname,
            lastname,
            pass,
            mobileWithoutCode
        )
        regModel.doSignUp(req)
    }

    fun observeRegResponse() {
        regModel.regResponse.observe(this, { response ->
            response?.let {
                rlProgressbar.visibility = View.GONE
                Log.e("TAG", "Reg Response $response")
                if (response.success) {
                    var _intent = Intent(this, LoginActivity::class.java)
                    _intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(_intent)
                } else {
                    Toast.makeText(
                        this@VerifyMobileActivity,
                        "${response.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}