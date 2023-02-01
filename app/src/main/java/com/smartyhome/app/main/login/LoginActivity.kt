package com.smartyhome.app.main.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.rmindr.app.network.MyCustomeApi
import com.smartyhome.app.R
import com.smartyhome.app.main.forgetpass.Forgotpass
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.main.register.RegisterActivity
import com.smartyhome.app.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.smartyhome.app.main.home.menudrawer.MenuDrawerActivity
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.NetworkUtils
import com.smartyhome.app.utils.SharedPreference
import kotlinx.android.synthetic.main.layout_progress_bar.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var loginModel: LoginViewModel
    var passVisibleState = 0
    var preference: SharedPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val myApi = MyCustomeApi()
        val _repository = ApiCallingRepository(myApi)
        val factory = ViewModelFactory(_repository)
        loginModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        preference = SharedPreference(this)
        tvSignUp.setOnClickListener(this)
        btnSignIn.setOnClickListener(this)
        tvForgrotPass.setOnClickListener(this)
        ivEye.setOnClickListener(this)
        observeEmailAddress()
        observLogin()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            (R.id.tvSignUp) -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            (R.id.tvForgrotPass) -> {
                startActivity(Intent(this, Forgotpass::class.java))
            }
            (R.id.btnSignIn) -> {
                if (edtEmail.text.toString().isNullOrEmpty())
                    edtEmail.setError(getString(R.string.required))
                else if (!loginModel.verifyEmail(edtEmail.text.toString()))
                    edtEmail.setError(getString(R.string.invalid_email))
                else if (edtPass.text.toString().isNullOrEmpty())
                    edtPass.setError(getString(R.string.required))
                else {
                    if (NetworkUtils.isNetworkConnected(this)) {
                        rlProgressbar.visibility = View.VISIBLE
                        loginModel.doLogin(edtEmail.text.toString(), edtPass.text.toString())
                    } else {
                        Toast.makeText(
                            this,
                            getString(R.string.connect_internet),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
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

    fun observeEmailAddress() {
        edtEmail.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (loginModel.verifyEmail(s.toString()))
                ivTick.visibility = View.VISIBLE
            else
                ivTick.visibility = View.GONE
        }
    }

    fun observLogin() {
        loginModel.loginResponse.observe(this, { response ->
            response?.let {
                Log.e("TAG", "Login Response $response ${response.error} ${response.success}")
                rlProgressbar.visibility = View.GONE
                if (it.success) {
                    preference?.setString(Constant.PASSWORD, edtPass.text.toString())
                    preference?.saveProfileinfo(it)
                    startActivity(Intent(this, MenuDrawerActivity::class.java))
                    Snackbar.make(constraintMain, "success", Snackbar.LENGTH_LONG)
                        .show()
                    finish()
                } else {
                    Snackbar.make(constraintMain, it.error, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}