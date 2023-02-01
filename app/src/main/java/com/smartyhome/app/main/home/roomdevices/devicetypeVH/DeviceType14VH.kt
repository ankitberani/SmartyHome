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
import kotlinx.android.synthetic.main.device_item_list_type_14.view.*

class DeviceType14VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

    private val ivDeviceSignald5: ImageView = itemView.ivDeviceSignald5
    private val ivDeviceStatusIcond5: ImageView = itemView.ivDeviceStatusIcond5
    private val tvDeviceNamed5: TextView = itemView.tvDeviceNamed5
    private val tvDeviceTypeNamed5: TextView = itemView.tvDeviceTyped5
    private val switchOnOffd5: SwitchCompat = itemView.switchOnOffd5
    private val cardDevicesd5: ConstraintLayout = itemView.constraintd5
    private val ivInfoIcond5: ImageView = itemView.ivInfoIcond5


     private val ivDeviceSignald6: ImageView = itemView.ivDeviceSignald6
    private val ivDeviceStatusIcond6: ImageView = itemView.ivDeviceStatusIcond6
    private val tvDeviceNamed6: TextView = itemView.tvDeviceNamed6
    private val tvDeviceTypeNamed6: TextView = itemView.tvDeviceTyped6
    private val switchOnOffd6: SwitchCompat = itemView.switchOnOffd6
    private val cardDevicesd6: ConstraintLayout = itemView.constraintd6
    private val ivInfoIcond6: ImageView = itemView.ivInfoIcond6


    private val ivDeviceSignald7: ImageView = itemView.ivDeviceSignald7
    private val ivDeviceStatusIcond7: ImageView = itemView.ivDeviceStatusIcond7
    private val tvDeviceNamed7: TextView = itemView.tvDeviceNamed7
    private val tvDeviceTypeNamed7: TextView = itemView.tvDeviceTyped7
    private val switchOnOffd7: SwitchCompat = itemView.switchOnOffd7
    private val cardDevicesd7: ConstraintLayout = itemView.constraintd7
    private val ivInfoIcond7: ImageView = itemView.ivInfoIcond7

    private val ivDeviceSignald8: ImageView = itemView.ivDeviceSignald8
    private val ivDeviceStatusIcond8: ImageView = itemView.ivDeviceStatusIcond8
    private val tvDeviceNamed8: TextView = itemView.tvDeviceNamed8
    private val tvDeviceTypeNamed8: TextView = itemView.tvDeviceTyped8
    private val switchOnOffd8: SwitchCompat = itemView.switchOnOffd8
    private val cardDevicesd8: ConstraintLayout = itemView.constraintd8
    private val ivInfoIcond8: ImageView = itemView.ivInfoIcond8




    private val ivSettingIcond1:ImageView = itemView.ivSettingIcond1
    private val ivSettingIcond2:ImageView = itemView.ivSettingIcond2
    private val ivSettingIcond3:ImageView = itemView.ivSettingIcond3
    private val ivSettingIcond4:ImageView = itemView.ivSettingIcond4
    private val ivSettingIcond5:ImageView = itemView.ivSettingIcond5
    private val ivSettingIcond6:ImageView = itemView.ivSettingIcond6
    private val ivSettingIcond7:ImageView = itemView.ivSettingIcond7
    private val ivSettingIcond8:ImageView = itemView.ivSettingIcond8

    fun bind(
        deviceModelVH: DeviceModelManual, deviceActionInterface: DeviceActionInterface
    ) {

        ivInfoIcond8.setOnClickListener({
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond8.context,
                deviceModelVH?.dno
            )
        })
        ivInfoIcond7.setOnClickListener({
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond7.context,
                deviceModelVH?.dno
            )
        })
        ivInfoIcond6.setOnClickListener({
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond6.context,
                deviceModelVH?.dno
            )
        })
        ivInfoIcond5.setOnClickListener({
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond5.context,
                deviceModelVH?.dno
            )
        })
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

        ivSettingIcond5.setOnClickListener({
                deviceActionInterface?.showMenu(ivSettingIcond5,deviceModelVH?.isOnline,adapterPosition,deviceModelVH?.dtype,deviceModelVH?.d5?.name?:"","d5")
        })

        ivSettingIcond6.setOnClickListener({
                deviceActionInterface?.showMenu(ivSettingIcond6,deviceModelVH?.isOnline,adapterPosition,deviceModelVH?.dtype,deviceModelVH?.d6?.name?:"","d6")
        })

        ivSettingIcond7.setOnClickListener({
                deviceActionInterface?.showMenu(ivSettingIcond7,deviceModelVH?.isOnline,adapterPosition,deviceModelVH?.dtype,deviceModelVH?.d7?.name?:"","d7")
        })

        ivSettingIcond8.setOnClickListener({
                deviceActionInterface?.showMenu(ivSettingIcond8,deviceModelVH?.isOnline,adapterPosition,deviceModelVH?.dtype,deviceModelVH?.d8?.name?:"","d8")
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

        if (!deviceModelVH.d5?.name.isNullOrEmpty())
            tvDeviceNamed5.setText(deviceModelVH.d5?.name)
        else
            tvDeviceNamed5.setText(deviceModelVH.name)
        tvDeviceTypeNamed5.setText(deviceModelVH.deviceTypeName)
        switchOnOffd5.isChecked = deviceModelVH.d5?.state ?: false

        if (!deviceModelVH.d6?.name.isNullOrEmpty())
            tvDeviceNamed6.setText(deviceModelVH.d6?.name)
        else
            tvDeviceNamed6.setText(deviceModelVH.name)
        tvDeviceTypeNamed6.setText(deviceModelVH.deviceTypeName)
        switchOnOffd6.isChecked = deviceModelVH.d6?.state ?: false

        if (!deviceModelVH.d7?.name.isNullOrEmpty())
            tvDeviceNamed7.setText(deviceModelVH.d7?.name)
        else
            tvDeviceNamed7.setText(deviceModelVH.name)
        tvDeviceTypeNamed7.setText(deviceModelVH.deviceTypeName)
        switchOnOffd7.isChecked = deviceModelVH.d7?.state ?: false

        if (!deviceModelVH.d8?.name.isNullOrEmpty())
            tvDeviceNamed8.setText(deviceModelVH.d8?.name)
        else
            tvDeviceNamed8.setText(deviceModelVH.name)
        tvDeviceTypeNamed8.setText(deviceModelVH.deviceTypeName)
        switchOnOffd8.isChecked = deviceModelVH.d8?.state ?: false


        if (deviceModelVH.isOnline) {
            ivDeviceSignal.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcon.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald2.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond2.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald3.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond3.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald4.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond4.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald5.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond5.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald6.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond6.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald7.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond7.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald8.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond8.setImageResource(deviceModelVH.deviceTypeIcon)
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

            ivDeviceSignald5.setImageDrawable(
                ivDeviceSignald5.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond5.setImageDrawable(
                ivDeviceSignald5.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )
            ivDeviceSignald6.setImageDrawable(
                ivDeviceSignald6.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond6.setImageDrawable(
                ivDeviceSignald6.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )
            ivDeviceSignald7.setImageDrawable(
                ivDeviceSignald7.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond7.setImageDrawable(
                ivDeviceSignald7.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )
            ivDeviceSignald8.setImageDrawable(
                ivDeviceSignald8.context.resources.getDrawable(R.drawable.signal_zero, null)
            )
            ivDeviceStatusIcond8.setImageDrawable(
                ivDeviceSignald8.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
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
        cardDevicesd5.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onDeviceClicked(deviceModelVH,"d5")
        }
        cardDevicesd6.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onDeviceClicked(deviceModelVH,"d6")
        }
        cardDevicesd7.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onDeviceClicked(deviceModelVH,"d7")
        }
        cardDevicesd8.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onDeviceClicked(deviceModelVH,"d8")
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


        switchOnOffd5.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    switchOnOffd5.isChecked,
                    "d5"
                )
            else {
                deviceActionInterface.showSnackbar(switchOnOffd5.context.getString(R.string.device_offline))
                switchOnOffd5.isChecked = !switchOnOffd5.isChecked
            }
        }
        switchOnOffd6.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    switchOnOffd6.isChecked,
                    "d6"
                )
            else {
                deviceActionInterface.showSnackbar(switchOnOffd6.context.getString(R.string.device_offline))
                switchOnOffd6.isChecked = !switchOnOffd6.isChecked
            }
        }
        switchOnOffd7.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    switchOnOffd7.isChecked,
                    "d7"
                )
            else {
                deviceActionInterface.showSnackbar(switchOnOffd7.context.getString(R.string.device_offline))
                switchOnOffd7.isChecked = !switchOnOffd7.isChecked
            }
        }
        switchOnOffd8.setOnClickListener {
            if (deviceModelVH.isOnline)
                deviceActionInterface.onSwitchChecked(
                    deviceModelVH,
                    switchOnOffd8.isChecked,
                    "d8"
                )
            else {
                deviceActionInterface.showSnackbar(switchOnOffd8.context.getString(R.string.device_offline))
                switchOnOffd8.isChecked = !switchOnOffd8.isChecked
            }
        }
    }
}