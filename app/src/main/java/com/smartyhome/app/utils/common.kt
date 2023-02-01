package com.smartyhome.app.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.smartyhome.app.R

object common {

   public fun showSnackBar(view : View, strMsg : String){
        Snackbar.make(view,strMsg,1000).show()
    }

    fun showInfoDialog(ip: String?, version: String, context: Context, dno: String) {
        val dialog = Dialog(context)
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.device_info_dialog, null, false)
        dialog.setContentView(view)
        val tv_ip = view.findViewById<TextView>(R.id.tv_ip_address)
        val tv_version = view.findViewById<TextView>(R.id.tv_version)
        val tv_dno = view.findViewById<TextView>(R.id.tv_dno)
        tv_ip.text = ip
        tv_version.text = version
        tv_dno.text = dno
        val tv_ok = view.findViewById<TextView>(R.id.tv_ok)
        tv_ok.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }
}