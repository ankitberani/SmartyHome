package com.smartyhome.app.main.forgetpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.smartyhome.app.R
import kotlinx.android.synthetic.main.activity_forgotpass.*
import kotlinx.android.synthetic.main.activity_forgotpass.btnContinue
import kotlinx.android.synthetic.main.activity_forgotpass.ivBack
import kotlinx.android.synthetic.main.activity_otp_verification.*
import kotlinx.android.synthetic.main.activity_verify_mobile.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import java.util.concurrent.TimeUnit

class OtpVerification : AppCompatActivity(),View.OnClickListener {

    var mobile = ""
    var mobileWithoutCode = ""
    var verificationId = ""
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        ivBack.setOnClickListener(this)
        btnContinue.setOnClickListener(this)

        intent?.let {
            mobile = it.getStringExtra("mobileNumber") ?: ""
            mobileWithoutCode = mobile
            if (!mobile.startsWith("+91"))
                mobile = "+91".plus(mobile)
            rlProgressbar.visibility = View.VISIBLE
            sendVerificationCode(mobile)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            (R.id.ivBack) -> {
                finish()
            }
            (R.id.btnContinue) -> {
                startActivity(Intent(this, ResetPassActivity::class.java))
            }
        }
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
        }

        override fun onVerificationFailed(e: FirebaseException) {
            rlProgressbar.visibility = View.GONE
            Toast.makeText(this@OtpVerification, "Failed ${e.toString()}", Toast.LENGTH_LONG)
                .show()
        }

        override fun onCodeSent(
            s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken
        ) {
            verificationId = s
            tvVerifyEmailHead.setText(getString(R.string.a_verification_code_sent, mobile))
            rlProgressbar.visibility = View.GONE
            // The SMS verification code has been sent to the provided phone number.
            // We now need to ask the user to enter the code and then construct a
            // credential by combining the code with a verification ID.
            // Save the verification ID and resending token for later use.
            Toast.makeText(this@OtpVerification, "Code Sent Successfully", Toast.LENGTH_LONG)
                .show()
            btnContinue.isEnabled = true
            btnContinue.isClickable = true
            tvdidntreceivemail.visibility = View.VISIBLE
            setupTimer()
        }
    }

    fun setupTimer() {
        object : CountDownTimer(30000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                tvdidntreceivemail.setText("00:" + millisUntilFinished / 1000)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                tvdidntreceivemail.setText(getString(R.string.didn_t_receive_email))
                tvResendMobile.visibility = View.VISIBLE
            }
        }.start()
    }
}