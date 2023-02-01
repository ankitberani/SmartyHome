package com.smartyhome.app.main.home.roomdevices.devicetypeVH

import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.DeviceActionInterface
import com.smartyhome.app.main.home.roomdevices.DeviceModelManual
import com.smartyhome.app.utils.common
import kotlinx.android.synthetic.main.device_item_list_type_8.view.*
import kotlinx.android.synthetic.main.device_item_list_type_8.view.cardDevices
import kotlinx.android.synthetic.main.device_item_list_type_8.view.ivDeviceSignal
import kotlinx.android.synthetic.main.device_item_list_type_8.view.ivDeviceStatusIcon

class DeviceType8VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ivDeviceSignal: ImageView = itemView.ivDeviceSignal
    private val ivDeviceStatusIcon: ImageView = itemView.ivDeviceStatusIcon
    private val tvDeviceName: TextView = itemView.tvDeviceName
    private val tvBrightness: TextView = itemView.tvBrightness
    private val tvDeviceTypeName: TextView = itemView.tvDeviceType
    private val switchOnOff: SwitchCompat = itemView.switchOnOff
    private val cardDevices: CardView = itemView.cardDevices
    private val brightnes: AppCompatSeekBar = itemView.seekbarBR
    private val ivInfoIcond1: ImageView = itemView.ivInfoIcon
    private val ivSettingIcond1:ImageView = itemView.ivSettingIcon
    fun bind(
        deviceModelVH: DeviceModelManual, deviceActionInterface: DeviceActionInterface
    ) {
        ivInfoIcond1.setOnClickListener({
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond1.context,
                deviceModelVH.dno
            )
        })

        ivSettingIcond1.setOnClickListener({
            deviceActionInterface?.showMenu(ivSettingIcond1,deviceModelVH?.isOnline,adapterPosition,deviceModelVH?.dtype,
                deviceModelVH?.d1?.name ?: "","d1")
        })

        if (!deviceModelVH.d1?.name.isNullOrEmpty())
            tvDeviceName.setText(deviceModelVH.d1?.name)
        else
            tvDeviceName.setText(deviceModelVH.name)
        tvDeviceTypeName.setText(deviceModelVH.deviceTypeName)
        switchOnOff.isChecked = deviceModelVH.state
        tvBrightness.setText(
            tvBrightness.context.getString(R.string.brightness) + " " + (deviceModelVH.br * 100).toInt()
                .toString() + "%"
        )
        brightnes.setProgress((deviceModelVH.br * 100).toInt())
        if (deviceModelVH.isOnline) {
            ivDeviceSignal.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcon.setImageResource(deviceModelVH.deviceTypeIcon)
            brightnes.isEnabled = true
        } else {
            brightnes.isEnabled = false
            ivDeviceSignal.setImageDrawable(
                ivDeviceSignal.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcon.setImageDrawable(
                ivDeviceSignal.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )
        }

        cardDevices.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onDeviceClicked(deviceModelVH,"d1")
        }


        if (!deviceModelVH?.enable.isNullOrEmpty()) {
            if (deviceModelVH.enable.toBoolean() == true) {
                switchOnOff.isEnabled = true
            } else
                switchOnOff.isEnabled = false
        } else {
            switchOnOff.isEnabled = false
        }
        switchOnOff.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(deviceModelVH, switchOnOff.isChecked,"d1")
            else {
                deviceActionInterface.showSnackbar(switchOnOff.context.getString(R.string.device_offline))
                switchOnOff.isChecked = !switchOnOff.isChecked
            }
        }

        brightnes?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (seekBar.progress < 1) {
                    brightnes.setProgress(1)
                }
                var speed = 25
                speed = if (seekBar.progress <= 25) {
                    25
                } else if (seekBar.progress > 25 && seekBar.progress <= 50) {
                    50
                } else if (seekBar.progress > 50 && seekBar.progress <= 75) {
                    75
                } else {
                    100
                }
                brightnes.setProgress(speed)
                tvBrightness.setText(
                    tvBrightness.context.getString(R.string.brightness) + " " + speed
                        .toString() + "%"
                )
                deviceActionInterface.sendFanProgress(
                    deviceModelVH,
                    "d1",
                    (seekBar.progress.toDouble().div(100)).toDouble(),
                    switchOnOff.isChecked
                )
            }
        })
    }
}