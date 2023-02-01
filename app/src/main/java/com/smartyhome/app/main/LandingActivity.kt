package com.smartyhome.app.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.smartyhome.app.R
import com.smartyhome.app.main.login.LoginActivity
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity(), View.OnClickListener {

    var currentState = 0
    lateinit var sharedPrefe: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        tvSkip.setOnClickListener(this)
        ivRightArrow.setOnClickListener(this)
        sharedPrefe = SharedPreference(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            (R.id.ivRightArrow) -> {
                changeView()
            }
            (R.id.tvSkip) -> {
                nevigate()
            }
        }
    }

    fun changeView() {
        if (currentState == 0) {
            tvtextfirst.visibility = View.GONE
            currentState += 1
            view1.setImageResource(R.drawable.dot)
            view2.setImageResource(R.drawable.rounded_corner)
            ivinstructionLogo.setImageResource(R.drawable.infor_2)
        } else if (currentState == 1) {
            currentState += 1
            view2.setImageResource(R.drawable.dot)
            view3.setImageResource(R.drawable.rounded_corner)
            ivinstructionLogo.setImageResource(R.drawable.infor_3)
        } else if (currentState == 2) {
            nevigate()
        }
    }

    fun nevigate(){
        sharedPrefe.setBoolean(Constant.DASH_SHOWN, true)
        var _intent = Intent(this, LoginActivity::class.java)
        startActivity(_intent)
        finish()
    }
}