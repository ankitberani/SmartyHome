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
import kotlinx.android.synthetic.main.device_item_list_type_20.view.constraintd1
import kotlinx.android.synthetic.main.device_item_list_type_20.view.constraintd2
import kotlinx.android.synthetic.main.device_item_list_type_20.view.constraintd3
import kotlinx.android.synthetic.main.device_item_list_type_20.view.constraintd4
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivDeviceSignald1
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivDeviceSignald2
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivDeviceSignald3
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivDeviceSignald4
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivDeviceStatusIcond1
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivDeviceStatusIcond2
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivDeviceStatusIcond3
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivDeviceStatusIcond4
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivInfoIcond1
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivInfoIcond2
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivInfoIcond3
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivInfoIcond4
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivSettingIcond1
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivSettingIcond2
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivSettingIcond3
import kotlinx.android.synthetic.main.device_item_list_type_20.view.ivSettingIcond4
import kotlinx.android.synthetic.main.device_item_list_type_20.view.switchOnOffd1
import kotlinx.android.synthetic.main.device_item_list_type_20.view.switchOnOffd2
import kotlinx.android.synthetic.main.device_item_list_type_20.view.switchOnOffd3
import kotlinx.android.synthetic.main.device_item_list_type_20.view.switchOnOffd4
import kotlinx.android.synthetic.main.device_item_list_type_20.view.tvDeviceNamed1
import kotlinx.android.synthetic.main.device_item_list_type_20.view.tvDeviceNamed2
import kotlinx.android.synthetic.main.device_item_list_type_20.view.tvDeviceNamed3
import kotlinx.android.synthetic.main.device_item_list_type_20.view.tvDeviceNamed4
import kotlinx.android.synthetic.main.device_item_list_type_20.view.tvDeviceTyped1
import kotlinx.android.synthetic.main.device_item_list_type_20.view.tvDeviceTyped2
import kotlinx.android.synthetic.main.device_item_list_type_20.view.tvDeviceTyped3
import kotlinx.android.synthetic.main.device_item_list_type_20.view.tvDeviceTyped4

class DeviceType20VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

    private val ivDeviceSignald3: ImageView = itemView.ivDeviceSignald3
    private val ivDeviceStatusIcond3: ImageView = itemView.ivDeviceStatusIcond3
    private val tvDeviceNamed3: TextView = itemView.tvDeviceNamed3
    private val tvDeviceTypeNamed3: TextView = itemView.tvDeviceTyped3
    private val switchOnOffd3: SwitchCompat = itemView.switchOnOffd3
    private val cardDevicesd3: ConstraintLayout = itemView.constraintd3
    private val ivInfoIcond3: ImageView = itemView.ivInfoIcond3

     private val ivDeviceSignald4: ImageView = itemView.ivDeviceSignald4
    private val ivDeviceStatusIcond4: ImageView = itemView.ivDeviceStatusIcond4
    private val tvDeviceNamed4: TextView = itemView.tvDeviceNamed4
    private val tvDeviceTypeNamed4: TextView = itemView.tvDeviceTyped4
    private val switchOnOffd4: SwitchCompat = itemView.switchOnOffd4
    private val cardDevicesd4: ConstraintLayout = itemView.constraintd4
    private val ivInfoIcond4: ImageView = itemView.ivInfoIcond4

    private val ivSettingIcond1:ImageView = itemView.ivSettingIcond1
    private val ivSettingIcond2:ImageView = itemView.ivSettingIcond2
    private val ivSettingIcond3:ImageView = itemView.ivSettingIcond3
    private val ivSettingIcond4:ImageView = itemView.ivSettingIcond4

    fun bind(
        deviceModelVH: DeviceModelManual, deviceActionInterface: DeviceActionInterface
    ) {

        ivInfoIcond4.setOnClickListener({
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond4.context,
                deviceModelVH?.dno
            )
        })
        ivInfoIcond3.setOnClickListener({
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond3.context,
                deviceModelVH?.dno
            )
        })
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

        ivSettingIcond3.setOnClickListener({
            deviceActionInterface?.showMenu(ivSettingIcond3,deviceModelVH?.isOnline,adapterPosition,deviceModelVH?.dtype,deviceModelVH?.d3?.name?:"","d3")
        })

        ivSettingIcond4.setOnClickListener({
            deviceActionInterface?.showMenu(ivSettingIcond4,deviceModelVH?.isOnline,adapterPosition,deviceModelVH?.dtype,deviceModelVH?.d4?.name?:"","d4")
        })

        if (!deviceModelVH.d1?.name.isNullOrEmpty())
            tvDeviceName.setText(deviceModelVH.d1?.name)
        else
            tvDeviceName.setText(deviceModelVH.name)
        tvDeviceTypeName.setText(deviceModelVH.deviceTypeName)
        switchOnOff.isChecked = deviceModelVH.d1?.state ?: false


        if (!deviceModelVH.d2?.name.isNullOrEmpty())
            tvDeviceNamed2.setText(deviceModelVH.d2?.name)
        else
            tvDeviceNamed2.setText(deviceModelVH.name)
        tvDeviceTypeNamed2.setText(deviceModelVH.deviceTypeName)
        switchOnOffd2.isChecked = deviceModelVH.d2?.state ?: false

        if (!deviceModelVH.d3?.name.isNullOrEmpty())
            tvDeviceNamed3.setText(deviceModelVH.d3?.name)
        else
            tvDeviceNamed3.setText(deviceModelVH.name)

        tvDeviceTypeNamed3.setText(deviceModelVH.deviceTypeName)
        switchOnOffd3.isChecked = deviceModelVH.d3?.state ?: false

        if (!deviceModelVH.d4?.name.isNullOrEmpty())
            tvDeviceNamed4.setText(deviceModelVH.d4?.name)
        else
            tvDeviceNamed4.setText(deviceModelVH.name)
        tvDeviceTypeNamed4.setText(deviceModelVH.deviceTypeName)
        switchOnOffd4.isChecked = deviceModelVH.d4?.state ?: false


        if (deviceModelVH.isOnline) {

            ivDeviceSignal.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcon.setImageResource(deviceModelVH.deviceTypeIcon)

            ivDeviceSignald2.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond2.setImageResource(deviceModelVH.deviceTypeIcon)

            ivDeviceSignald3.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond3.setImageResource(deviceModelVH.deviceTypeIcon)

            ivDeviceSignald4.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond4.setImageResource(deviceModelVH.deviceTypeIcon)

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

             ivDeviceSignald3.setImageDrawable(
                ivDeviceSignald3.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond3.setImageDrawable(
                ivDeviceSignald3.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )

             ivDeviceSignald4.setImageDrawable(
                ivDeviceSignald4.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond4.setImageDrawable(
                ivDeviceSignald4.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
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

        cardDevicesd3.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onDeviceClicked(deviceModelVH,"d3")
        }

        cardDevicesd4.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onDeviceClicked(deviceModelVH,"d4")
        }

        switchOnOff.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    if (deviceModelVH.d1?.state ?: false) false else true,
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

        switchOnOffd3.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    if (deviceModelVH.d3?.state ?: false) false else true,
                    "d3"
                )
            else {
                deviceActionInterface.showSnackbar(switchOnOffd3.context.getString(R.string.device_offline))
                switchOnOffd3.isChecked = !switchOnOffd3.isChecked
            }
        }

        switchOnOffd4.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    switchOnOffd4.isChecked,
                    "d4"
                )
            else {
                deviceActionInterface.showSnackbar(switchOnOffd4.context.getString(R.string.device_offline))
                switchOnOffd4.isChecked = !switchOnOffd4.isChecked
            }
        }

    }
}