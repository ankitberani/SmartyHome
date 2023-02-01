package com.smartyhome.app.main.home.roomdevices.devicetypeVH

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.DeviceActionInterface
import com.smartyhome.app.main.home.roomdevices.DeviceModelManual
import com.smartyhome.app.utils.common
import kotlinx.android.synthetic.main.device_item_list_type_6.view.*
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivDeviceSignald1
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivDeviceSignald2
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivDeviceSignald3
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivDeviceSignald4
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivDeviceStatusIcond1
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivDeviceStatusIcond2
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivDeviceStatusIcond3
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivDeviceStatusIcond4
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivInfoIcond1
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivInfoIcond2
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivInfoIcond3
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivInfoIcond4
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivSettingIcond1
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivSettingIcond2
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivSettingIcond3
import kotlinx.android.synthetic.main.device_item_list_type_6.view.ivSettingIcond4
import kotlinx.android.synthetic.main.device_item_list_type_6.view.switchOnOffd1
import kotlinx.android.synthetic.main.device_item_list_type_6.view.switchOnOffd2
import kotlinx.android.synthetic.main.device_item_list_type_6.view.switchOnOffd3
import kotlinx.android.synthetic.main.device_item_list_type_6.view.switchOnOffd4
import kotlinx.android.synthetic.main.device_item_list_type_6.view.tvDeviceNamed1
import kotlinx.android.synthetic.main.device_item_list_type_6.view.tvDeviceNamed2
import kotlinx.android.synthetic.main.device_item_list_type_6.view.tvDeviceNamed3
import kotlinx.android.synthetic.main.device_item_list_type_6.view.tvDeviceNamed4
import kotlinx.android.synthetic.main.device_item_list_type_6.view.tvDeviceTyped1
import kotlinx.android.synthetic.main.device_item_list_type_6.view.tvDeviceTyped2
import kotlinx.android.synthetic.main.device_item_list_type_6.view.tvDeviceTyped3
import kotlinx.android.synthetic.main.device_item_list_type_6.view.tvDeviceTyped4

class DeviceType6VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ivDeviceSignald1: ImageView = itemView.ivDeviceSignald1
    private val ivDeviceStatusIcond1: ImageView = itemView.ivDeviceStatusIcond1
    private val tvDeviceNamed1: TextView = itemView.tvDeviceNamed1
    private val tvDeviceTypeNamed1: TextView = itemView.tvDeviceTyped1
    private val switchOnOffd1: SwitchCompat = itemView.switchOnOffd1
    private val cardDevicesd1: CardView = itemView.cardDevicesd1
    private val ivInfoIcond1: ImageView = itemView.ivInfoIcond1

    private val ivDeviceSignald2: ImageView = itemView.ivDeviceSignald2
    private val ivDeviceStatusIcond2: ImageView = itemView.ivDeviceStatusIcond2
    private val tvDeviceNamed2: TextView = itemView.tvDeviceNamed2
    private val tvDeviceTypeNamed2: TextView = itemView.tvDeviceTyped2
    private val switchOnOffd2: SwitchCompat = itemView.switchOnOffd2
    private val cardDevicesd2: CardView = itemView.cardDevicesd2
    private val ivInfoIcond2: ImageView = itemView.ivInfoIcond2

    private val ivDeviceSignald3: ImageView = itemView.ivDeviceSignald3
    private val ivDeviceStatusIcond3: ImageView = itemView.ivDeviceStatusIcond3
    private val tvDeviceNamed3: TextView = itemView.tvDeviceNamed3
    private val tvDeviceTypeNamed3: TextView = itemView.tvDeviceTyped3
    private val switchOnOffd3: SwitchCompat = itemView.switchOnOffd3
    private val cardDevicesd3: CardView = itemView.cardDevicesd3
    private val ivInfoIcond3: ImageView = itemView.ivInfoIcond3

    private val ivDeviceSignald4: ImageView = itemView.ivDeviceSignald4
    private val ivDeviceStatusIcond4: ImageView = itemView.ivDeviceStatusIcond4
    private val tvDeviceNamed4: TextView = itemView.tvDeviceNamed4
    private val tvDeviceTypeNamed4: TextView = itemView.tvDeviceTyped4
    private val switchOnOffd4: SwitchCompat = itemView.switchOnOffd4
    private val cardDevicesd4: CardView = itemView.cardDevicesd4
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
            deviceActionInterface?.showMenu(
                ivSettingIcond1,
                deviceModelVH?.isOnline,
                adapterPosition,
                deviceModelVH?.dtype,
                deviceModelVH?.d1?.name ?: "","d1"
            )
        })

        ivSettingIcond2.setOnClickListener({
            deviceActionInterface?.showMenu(
                ivSettingIcond2,
                deviceModelVH?.isOnline,
                adapterPosition,
                deviceModelVH?.dtype,
                deviceModelVH?.d2?.name ?: "","d2"
            )
        })

        ivSettingIcond3.setOnClickListener({
            deviceActionInterface?.showMenu(
                ivSettingIcond3,
                deviceModelVH?.isOnline,
                adapterPosition,
                deviceModelVH?.dtype,
                deviceModelVH?.d3?.name ?: "","d3"
            )
        })

        ivSettingIcond4.setOnClickListener({
            deviceActionInterface?.showMenu(
                ivSettingIcond4,
                deviceModelVH?.isOnline,
                adapterPosition,
                deviceModelVH?.dtype,
                deviceModelVH?.d4?.name ?: "","d4"
            )
        })

        if (!deviceModelVH.d1?.name.isNullOrEmpty())
            tvDeviceNamed1.setText(deviceModelVH.d1?.name)
        else
            tvDeviceNamed1.setText(deviceModelVH.name)
        tvDeviceTypeNamed1.setText(deviceModelVH.deviceTypeName)
        switchOnOffd1.isChecked = deviceModelVH.d1?.state ?: false
        if (deviceModelVH.d1?.state ?: false) {
            switchOnOffd1.isChecked = true
            ivDeviceSignald1.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond1.setImageResource(deviceModelVH.deviceTypeIcon)
        } else {
            ivDeviceSignald1.setImageDrawable(
                ivDeviceSignald1.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond1.setImageDrawable(
                ivDeviceSignald1.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )
            switchOnOffd1.isChecked = false
        }


        /* device d2 configuration*/
        if (!deviceModelVH.d2?.name.isNullOrEmpty())
            tvDeviceNamed2.setText(deviceModelVH.d2?.name)
        else
            tvDeviceNamed2.setText(deviceModelVH.name)
        tvDeviceTypeNamed2.setText(deviceModelVH.deviceTypeName)
        switchOnOffd2.isChecked = deviceModelVH.d2?.state ?: false
        if (deviceModelVH.d2?.state ?: false) {
            switchOnOffd2.isChecked = true
            ivDeviceSignald2.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond2.setImageResource(deviceModelVH.deviceTypeIcon)
        } else {
            ivDeviceSignald2.setImageDrawable(
                ivDeviceSignald2.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond2.setImageDrawable(
                ivDeviceSignald2.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )
            switchOnOffd2.isChecked = false
        }
        /* device d3 configuration*/
        if (!deviceModelVH.d3?.name.isNullOrEmpty())
            tvDeviceNamed3.setText(deviceModelVH.d3?.name)
        else
            tvDeviceNamed3.setText(deviceModelVH.name)
        tvDeviceTypeNamed3.setText(deviceModelVH.deviceTypeName)
        switchOnOffd3.isChecked = deviceModelVH.d3?.state ?: false

        if (deviceModelVH.d3?.state ?: false) {
            switchOnOffd3.isChecked = true
            ivDeviceSignald3.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond3.setImageResource(deviceModelVH.deviceTypeIcon)
        } else {
            ivDeviceSignald3.setImageDrawable(
                ivDeviceSignald3.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond3.setImageDrawable(
                ivDeviceSignald3.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )
            switchOnOffd3.isChecked = false
        }
        /* device d4 configuration*/
        if (!deviceModelVH.d4?.name.isNullOrEmpty())
            tvDeviceNamed4.setText(deviceModelVH.d4?.name)
        else
            tvDeviceNamed4.setText(deviceModelVH?.name)
        tvDeviceTypeNamed4.setText(deviceModelVH.deviceTypeName)
        switchOnOffd4.isChecked = deviceModelVH.d4?.state ?: false
        if (deviceModelVH.d4?.state ?: false) {
            switchOnOffd4.isChecked = true
            ivDeviceSignald4.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond4.setImageResource(deviceModelVH.deviceTypeIcon)
        } else {
            ivDeviceSignald4.setImageDrawable(
                ivDeviceSignald4.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond4.setImageDrawable(
                ivDeviceSignald4.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )
            switchOnOffd4.isChecked = false
        }

        if (!deviceModelVH?.enable.isNullOrEmpty()) {
            if (deviceModelVH.enable.toBoolean() == true) {
                switchOnOffd1.isEnabled = true
                switchOnOffd2.isEnabled = true
                switchOnOffd3.isEnabled = true
                switchOnOffd4.isEnabled = true
            } else {
                switchOnOffd1.isEnabled = false
                switchOnOffd2.isEnabled = false
                switchOnOffd3.isEnabled = false
                switchOnOffd4.isEnabled = false
            }
        } else {
            switchOnOffd1.isEnabled = false
            switchOnOffd2.isEnabled = false
            switchOnOffd3.isEnabled = false
            switchOnOffd4.isEnabled = false
        }
        switchOnOffd1.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    switchOnOffd1.isChecked,
                    "d1"
                )
            else {
                deviceActionInterface.showSnackbar(switchOnOffd1.context.getString(R.string.device_offline))
                switchOnOffd1.isChecked = !switchOnOffd1.isChecked
            }
        }
        switchOnOffd2.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    switchOnOffd2.isChecked,
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
                    switchOnOffd3.isChecked,
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