package com.smartyhome.app.main.home.roomdevices.devicetypeVH

import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.DeviceActionInterface
import com.smartyhome.app.main.home.roomdevices.DeviceModelManual
import com.smartyhome.app.utils.common
import kotlinx.android.synthetic.main.device_item_list_type_23.view.*
import java.lang.StringBuilder

class DeviceType23VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ivDeviceSignal: ImageView = itemView.ivDeviceSignald1
    private val ivDeviceStatusIcon: ImageView = itemView.ivDeviceStatusIcond1
    private val tvDeviceName: TextView = itemView.tvDeviceNamed1
    private val tvDeviceTypeName: TextView = itemView.tvDeviceTyped1
    private val switchOnOff: SwitchCompat = itemView.switchOnOffd1
    private val cardDevicesd1: ConstraintLayout = itemView.constraintd1
    private val ivInfoIcond1: ImageView = itemView.ivInfoIcond1
    private val seekbarBRd1: AppCompatSeekBar = itemView.seekbarBRd1
    private val tvBrightnessd1: AppCompatTextView = itemView.tvBrightnessd1


    private val ivDeviceSignald2: ImageView = itemView.ivDeviceSignald2
    private val ivDeviceStatusIcond2: ImageView = itemView.ivDeviceStatusIcond2
    private val tvDeviceNamed2: TextView = itemView.tvDeviceNamed2
    private val tvDeviceTypeNamed2: TextView = itemView.tvDeviceTyped2
    private val switchOnOffd2: SwitchCompat = itemView.switchOnOffd2
    private val cardDevicesd2: ConstraintLayout = itemView.constraintd2
    private val ivInfoIcond2: ImageView = itemView.ivInfoIcond2
    private val seekbarBRd2: AppCompatSeekBar = itemView.seekbarBRd2
    private val tvBrightnessd2: AppCompatTextView = itemView.tvBrightnessd2


    private val ivDeviceSignald3: ImageView = itemView.ivDeviceSignald3
    private val ivDeviceStatusIcond3: ImageView = itemView.ivDeviceStatusIcond3
    private val tvDeviceNamed3: TextView = itemView.tvDeviceNamed3
    private val tvDeviceTypeNamed3: TextView = itemView.tvDeviceTyped3
    private val switchOnOffd3: SwitchCompat = itemView.switchOnOffd3
    private val cardDevicesd3: ConstraintLayout = itemView.constraintd3
    private val ivInfoIcond3: ImageView = itemView.ivInfoIcond3
    private val seekbarBRd3: AppCompatSeekBar = itemView.seekbarBRd3
    private val tvBrightnessd3: AppCompatTextView = itemView.tvBrightnessd3


    private val ivDeviceSignald4: ImageView = itemView.ivDeviceSignald4
    private val ivDeviceStatusIcond4: ImageView = itemView.ivDeviceStatusIcond4
    private val tvDeviceNamed4: TextView = itemView.tvDeviceNamed4
    private val tvDeviceTypeNamed4: TextView = itemView.tvDeviceTyped4
    private val switchOnOffd4: SwitchCompat = itemView.switchOnOffd4
    private val cardDevicesd4: ConstraintLayout = itemView.constraintd4
    private val ivInfoIcond4: ImageView = itemView.ivInfoIcond4
    private val seekbarBRd4: AppCompatSeekBar = itemView.seekbarBRd4
    private val tvBrightnessd4: AppCompatTextView = itemView.tvBrightnessd4

    private val ivDeviceSignald5: ImageView = itemView.ivDeviceSignald5
    private val ivDeviceStatusIcond5: ImageView = itemView.ivDeviceStatusIcond5
    private val tvDeviceNamed5: TextView = itemView.tvDeviceNamed5
    private val tvDeviceTypeNamed5: TextView = itemView.tvDeviceTyped5
    private val switchOnOffd5: SwitchCompat = itemView.switchOnOffd5
    private val seekbarBRd5: AppCompatSeekBar = itemView.seekbarBRd5
    private val tvBrightnessd5: AppCompatTextView = itemView.tvBrightnessd5
    private val cardDevicesd5: ConstraintLayout = itemView.constraintd5
    private val ivInfoIcond5: ImageView = itemView.ivInfoIcond5



    private val ivSettingIcond1:ImageView = itemView.ivSettingIcond1
    private val ivSettingIcond2:ImageView = itemView.ivSettingIcond2
    private val ivSettingIcond3:ImageView = itemView.ivSettingIcond3
    private val ivSettingIcond4:ImageView = itemView.ivSettingIcond4
    private val ivSettingIcond5:ImageView = itemView.ivSettingIcond5
    fun bind(
        deviceModelVH: DeviceModelManual, deviceActionInterface: DeviceActionInterface
    ) {

        ivInfoIcond5.setOnClickListener {
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond5.context,
                deviceModelVH?.dno
            )
        }
        ivInfoIcond4.setOnClickListener {
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond4.context,
                deviceModelVH?.dno
            )
        }
        ivInfoIcond3.setOnClickListener {
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond3.context,
                deviceModelVH?.dno
            )
        }
        ivInfoIcond2.setOnClickListener {
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond2.context,
                deviceModelVH?.dno
            )
        }
        ivInfoIcond1.setOnClickListener {
            common.showInfoDialog(
                deviceModelVH?.ip,
                deviceModelVH?.version.toString(),
                ivInfoIcond1.context,
                deviceModelVH?.dno
            )
        }

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


        var progess: Int? = 0
        setVisibility(null,null)
        if (deviceModelVH.d1?.br != -0.0) {
            progess = ((deviceModelVH.d1?.br)?.times(100))?.toInt()
            seekbarBRd1.setProgress(progess ?: 0)
            tvBrightnessd1.setText(
                tvBrightnessd1.context.getString(R.string.brightness).plus(" $progess %")
            )
            setVisibility( tvBrightnessd1, seekbarBRd1)
        } else if (deviceModelVH.d2?.br != -0.0) {
            progess = ((deviceModelVH.d2?.br)?.times(100))?.toInt()
            seekbarBRd2.setProgress(progess ?: 0)
            tvBrightnessd2.setText(
                tvBrightnessd2.context.getString(R.string.brightness).plus(" $progess %")
            )
            setVisibility( tvBrightnessd2, seekbarBRd2)
        } else if (deviceModelVH.d3?.br != -0.0) {
            progess = ((deviceModelVH.d3?.br)?.times(100))?.toInt()
            seekbarBRd3.setProgress(progess ?: 0)
            tvBrightnessd3.setText(
                tvBrightnessd3.context.getString(R.string.brightness).plus(" $progess %")
            )
            setVisibility( tvBrightnessd3, seekbarBRd3)
        } else if (deviceModelVH.d4?.br != -0.0) {
            progess = ((deviceModelVH.d4?.br)?.times(100))?.toInt()
            seekbarBRd4.setProgress(progess ?: 0)
            tvBrightnessd4.setText(
                tvBrightnessd4.context.getString(R.string.brightness).plus(" $progess %")
            )
            setVisibility( tvBrightnessd4, seekbarBRd4)
        } else if (deviceModelVH.d5?.br != -0.0) {
            progess = ((deviceModelVH.d5?.br)?.times(100))?.toInt()
            seekbarBRd5.setProgress(progess ?: 0)
            tvBrightnessd5.setText(
                tvBrightnessd5.context.getString(R.string.brightness).plus(" $progess %")
            )
            setVisibility( tvBrightnessd5, seekbarBRd5)
        }
        if (deviceModelVH.isOnline) {
            ivDeviceSignal.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcon.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald2.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond2.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald3.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond3.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceStatusIcond4.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald4.setImageResource(deviceModelVH.deviceStrengthIcon)
            ivDeviceStatusIcond5.setImageResource(deviceModelVH.deviceTypeIcon)
            ivDeviceSignald5.setImageResource(deviceModelVH.deviceStrengthIcon)
            seekbarBRd5.isEnabled = true
            seekbarBRd4.isEnabled = true
            seekbarBRd3.isEnabled = true
            seekbarBRd2.isEnabled = true
            seekbarBRd1.isEnabled = true
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
            ivDeviceStatusIcond5.setImageDrawable(
                ivDeviceSignald5.context.resources.getDrawable(R.drawable.ic_wifi_off, null)
            )
            seekbarBRd5.isEnabled = false
            seekbarBRd4.isEnabled = false
            seekbarBRd3.isEnabled = false
            seekbarBRd2.isEnabled = false
            seekbarBRd1.isEnabled = false
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


        seekbarBRd1?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {

                if (seekBar.progress < 1) {
                    seekbarBRd1.setProgress(1)
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

                tvBrightnessd1.setText(
                    tvBrightnessd1.context.getString(R.string.brightness) + " " + speed
                        .toString() + "%"
                )
                seekbarBRd1.setProgress(speed)
                deviceActionInterface.sendFanProgress(
                    deviceModelVH,
                    "d1",
                    (seekBar.progress.toDouble().div(100)).toDouble(),
                    switchOnOff.isChecked
                )
            }
        })
        seekbarBRd2?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {

                if (seekBar.progress < 1) {
                    seekbarBRd2.setProgress(1)
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

                tvBrightnessd2.setText(
                    tvBrightnessd2.context.getString(R.string.brightness) + " " + speed
                        .toString() + "%"
                )
                seekbarBRd2.setProgress(speed)
                deviceActionInterface.sendFanProgress(
                    deviceModelVH,
                    "d2",
                    (seekBar.progress.toDouble().div(100)).toDouble(),
                    switchOnOffd2.isChecked
                )
            }
        })
        seekbarBRd3?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {

                if (seekBar.progress < 1) {
                    seekbarBRd3.setProgress(1)
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

                tvBrightnessd3.setText(
                    tvBrightnessd3.context.getString(R.string.brightness) + " " + speed
                        .toString() + "%"
                )
                seekbarBRd3.setProgress(speed)
                deviceActionInterface.sendFanProgress(
                    deviceModelVH,
                    "d3",
                    (seekBar.progress.toDouble().div(100)).toDouble(),
                    switchOnOffd3.isChecked
                )
            }
        })
        seekbarBRd4?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {

                if (seekBar.progress < 1) {
                    seekbarBRd4.setProgress(1)
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

                tvBrightnessd4.setText(
                    tvBrightnessd4.context.getString(R.string.brightness) + " " + speed
                        .toString() + "%"
                )
                seekbarBRd4.setProgress(speed)
                deviceActionInterface.sendFanProgress(
                    deviceModelVH,
                    "d4",
                    (seekBar.progress.toDouble().div(100)).toDouble(),
                    switchOnOffd4.isChecked
                )
            }
        })
        seekbarBRd5?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {

                if (seekBar.progress < 1) {
                    seekbarBRd5.setProgress(1)
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

                tvBrightnessd5.setText(
                    tvBrightnessd5.context.getString(R.string.brightness) + " " + speed
                        .toString() + "%"
                )
                seekbarBRd5.setProgress(speed)
                deviceActionInterface.sendFanProgress(
                    deviceModelVH,
                    "d5",
                    (seekBar.progress.toDouble().div(100)).toDouble(),
                    switchOnOffd5.isChecked
                )
            }
        })
    }


    fun setVisibility(tvBrightness:TextView?,seekbar:AppCompatSeekBar?){
        seekbarBRd1.visibility = View.GONE
        seekbarBRd2.visibility = View.GONE
        seekbarBRd3.visibility = View.GONE
        seekbarBRd4.visibility = View.GONE
        seekbarBRd5.visibility = View.GONE
        tvBrightnessd1.visibility = View.GONE
        tvBrightnessd2.visibility = View.GONE
        tvBrightnessd3.visibility = View.GONE
        tvBrightnessd4.visibility = View.GONE
        tvBrightnessd5.visibility = View.GONE
        tvBrightness?.visibility = View.VISIBLE
        seekbar?.visibility = View.VISIBLE
    }

}