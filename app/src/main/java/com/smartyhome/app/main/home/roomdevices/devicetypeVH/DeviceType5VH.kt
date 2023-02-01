package com.smartyhome.app.main.home.roomdevices.devicetypeVH

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.DeviceActionInterface
import com.smartyhome.app.main.home.roomdevices.DeviceModelManual
import com.smartyhome.app.utils.common
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.constraintd1
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.constraintd2
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.ivDeviceSignald1
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.ivDeviceSignald2
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.ivDeviceStatusIcond1
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.ivDeviceStatusIcond2
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.ivInfoIcond1
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.ivInfoIcond2
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.ivSettingIcond1
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.ivSettingIcond2
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.switchOnOffd1
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.switchOnOffd2
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.tvDeviceNamed1
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.tvDeviceNamed2
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.tvDeviceTyped1
import kotlinx.android.synthetic.main.device_item_list_type_5_19.view.tvDeviceTyped2

class DeviceType5VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ivDeviceSignal: ImageView = itemView.ivDeviceSignald1
    private val ivDeviceStatusIcon: ImageView = itemView.ivDeviceStatusIcond1
    private val tvDeviceName: TextView = itemView.tvDeviceNamed1
    private val tvDeviceTypeName: TextView = itemView.tvDeviceTyped1
    private val switchOnOff: SwitchCompat = itemView.switchOnOffd1
    private val cardDevicesd1: ConstraintLayout = itemView.constraintd1
    private val ivInfoIcond1: ImageView = itemView.ivInfoIcond1

     private val ivDeviceSignald2: ImageView = itemView.ivDeviceSignald2
    private val ivDeviceStatusIcond2: ImageView = itemView.ivDeviceStatusIcond2
    private val tvDeviceNamed2: TextView = itemView.tvDeviceNamed2
    private val tvDeviceTypeNamed2: TextView = itemView.tvDeviceTyped2
    private val switchOnOffd2: SwitchCompat = itemView.switchOnOffd2
    private val cardDevicesd2: ConstraintLayout = itemView.constraintd2
    private val ivInfoIcond2: ImageView = itemView.ivInfoIcond2

    private val ivSettingIcond1:ImageView = itemView.ivSettingIcond1
    private val ivSettingIcond2:ImageView = itemView.ivSettingIcond2

    fun bind(
        deviceModelVH: DeviceModelManual, deviceActionInterface: DeviceActionInterface
    ) {

        ivInfoIcond2.setOnClickListener({
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond2.context,
                deviceModelVH?.dno
            )
        })

        ivInfoIcond1.setOnClickListener({
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond1.context,
                deviceModelVH?.dno
            )
        })


        ivSettingIcond1.setOnClickListener({
            deviceActionInterface?.showMenu(ivSettingIcond1,deviceModelVH?.isOnline,adapterPosition,deviceModelVH?.dtype,deviceModelVH?.d1?.name?:"","d1")
        })

        ivSettingIcond2.setOnClickListener({
            deviceActionInterface?.showMenu(ivSettingIcond2,deviceModelVH?.isOnline,adapterPosition,deviceModelVH?.dtype,deviceModelVH?.d2?.name?:"","d2")
        })

        if (!deviceModelVH.d1?.name.isNullOrEmpty())
            tvDeviceName.setText(deviceModelVH.d1?.name)
        else
            tvDeviceName.setText(deviceModelVH.name)
        tvDeviceTypeName.setText(deviceModelVH.deviceTypeName)

        if (!deviceModelVH.d2?.name.isNullOrEmpty())
            tvDeviceNamed2.setText(deviceModelVH.d2?.name)
        else
            tvDeviceNamed2.setText(deviceModelVH.name)
        tvDeviceTypeNamed2.setText(deviceModelVH.deviceTypeName)
        switchOnOff.isChecked = deviceModelVH.d1?.state ?: false
        switchOnOffd2.isChecked = deviceModelVH.d2?.state ?: false

        if (deviceModelVH.isOnline) {
            ivDeviceSignal.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcon.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald2.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond2.setImageResource(deviceModelVH.deviceTypeIcon)
        } else {
            ivDeviceSignal.setImageDrawable(
                ivDeviceSignal.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcon.setImageDrawable(
                ivDeviceSignal.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )

            ivDeviceSignald2.setImageDrawable(
                ivDeviceSignald2.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond2.setImageDrawable(
                ivDeviceSignald2.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )
        }

        cardDevicesd1.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onDeviceClicked(deviceModelVH,"d1")
        }

        cardDevicesd2.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onDeviceClicked(deviceModelVH,"d2")
        }

        if (!deviceModelVH?.enable.isNullOrEmpty()) {
            if (deviceModelVH.enable.toBoolean() == true) {
                switchOnOff.isEnabled = true
                switchOnOffd2.isEnabled = true
            } else {
                switchOnOff.isEnabled = false
                switchOnOffd2.isEnabled = false
            }
        } else {
            switchOnOff.isEnabled = false
            switchOnOffd2.isEnabled = false
        }

        switchOnOff.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    switchOnOff.isChecked,
                    "d1"
                )
            else {
                deviceActionInterface.showSnackbar(switchOnOff.context.getString(R.string.device_offline))
                switchOnOff.isChecked = !switchOnOff.isChecked
            }
        }

        switchOnOffd2.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    if (deviceModelVH.d2?.state ?: false) false else true,
                    "d2"
                )
            else {
                deviceActionInterface.showSnackbar(switchOnOffd2.context.getString(R.string.device_offline))
                switchOnOffd2.isChecked = !switchOnOffd2.isChecked
            }
        }
    }
}