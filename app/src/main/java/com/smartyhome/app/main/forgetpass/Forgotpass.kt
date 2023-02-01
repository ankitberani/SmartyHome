package com.smartyhome.app.main.forgetpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.smartyhome.app.R
import com.smartyhome.app.main.register.VerifyMobileActivity
import kotlinx.android.synthetic.main.activity_forgotpass.*

class Forgotpass : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpass)
        btnContinue.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            (R.id.btnContinue) -> {
                edtMobileNumber?.let {
                    if (edtMobileNumber.text.isNullOrEmpty() || it.text?.length ?: 0 < 10) {
                        Toast.makeText(this, "Enter valid mobile number", Toast.LENGTH_LONG).show()
                        return
                    }
                    var intent = Intent(this, VerifyMobileActivity::class.java)
                    intent.setAction("fromForget")
                    intent.putExtra("mobile", edtMobileNumber.text.toString())
                    startActivity(intent)
                }
            }
            (R.id.ivBack) -> {
                finish()
            }
        }
    }
}