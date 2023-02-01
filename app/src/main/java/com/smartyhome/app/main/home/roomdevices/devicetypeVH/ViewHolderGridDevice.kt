package com.smartyhome.app.main.home.roomdevices.devicetypeVH

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.DeviceActionInterface
import com.smartyhome.app.main.home.roomdevices.DeviceModelManual
import kotlinx.android.synthetic.main.device_item_grid.view.*

class ViewHolderGridDevice(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvStatus: TextView = itemView.tvStatus
    private val tvDeviceName: TextView = itemView.tvDeviceName
    private val viewOnlineStatus: View = itemView.viewOnlineStatus
    private val cardDevices: CardView = itemView.cardDevices

    fun bind(
        deviceModelVH: DeviceModelManual, deviceActionInterface: DeviceActionInterface
    ) {
        tvDeviceName.setText(deviceModelVH.name)
        if (deviceModelVH.state) {
            tvStatus.setText(tvStatus.context.getString(R.string.status_on))
            viewOnlineStatus.setBackgroundResource(R.drawable.rounded_circle_on)
        } else {
            tvStatus.setText(tvStatus.context.getString(R.string.status_off))
            viewOnlineStatus.setBackgroundResource(R.drawable.rounded_circle)
        }

        if (deviceModelVH.isOnline) {
            viewOnlineStatus.visibility = View.GONE
            cardDevices.setCardBackgroundColor(Color.parseColor("#FFBD74"))
        } else {
            viewOnlineStatus.visibility = View.VISIBLE
            cardDevices.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        cardDevices.setOnClickListener {
            if (deviceModelVH.state)
                deviceActionInterface.onDeviceClicked(deviceModelVH,"d1")
        }
    }


}