package com.smartyhome.app.main.home.roomdevices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.devicetypeVH.*


class RoomDeviceAdapterHorizontal(
    private val deviceList: List<DeviceModelManual>,
    private val deviceActionInterface: DeviceActionInterface
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            1, 2, 9, 18 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_2, parent, false)
                DeviceType2VH(view)
            }
            15, 8 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_8, parent, false)
                DeviceType8VH(view)
            }
            6 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_6, parent, false)
                DeviceType6VH(view)
            }
            12 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_12, parent, false)
                DeviceType12VH(view)
            }
            5,19 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_5_19, parent, false)
                DeviceType5VH(view)
            }
            20 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_20, parent, false)
                DeviceType20VH(view)
            }
            23 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_23, parent, false)
                DeviceType23VH(view)
            }24 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_24, parent, false)
                DeviceType24VH(view)
            } 25 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_25, parent, false)
                DeviceType25VH(view)
            }   27 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_27, parent, false)
                DeviceType27VH(view)
            }  14 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_14, parent, false)
                DeviceType14VH(view)
            } else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_list_type_2, parent, false)
                DeviceType2VH(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (deviceList!!.size == 0) {
            return
        }
        when (holder.itemViewType) {
            1,2,9,18 -> {
                val deviceType2VH = holder as DeviceType2VH
                deviceType2VH.bind(deviceList.get(position), deviceActionInterface)
            }
            15, 8 -> {
                val deviceType8VH = holder as DeviceType8VH
                deviceType8VH.bind(deviceList.get(position), deviceActionInterface)
            }
            6 -> {
                val deviceType6VH = holder as DeviceType6VH
                deviceType6VH.bind(deviceList.get(position), deviceActionInterface)
            }
            12 -> {
                val deviceType12VH = holder as DeviceType12VH
                deviceType12VH.bind(deviceList.get(position), deviceActionInterface)
            }
            5,19 -> {
                val deviceType5VH = holder as DeviceType5VH
                deviceType5VH.bind(deviceList.get(position), deviceActionInterface)
            }
            20 -> {
                val deviceType20VH = holder as DeviceType20VH
                deviceType20VH.bind(deviceList.get(position), deviceActionInterface)
            }
            23 -> {
                val deviceType25VH = holder as DeviceType23VH
                deviceType25VH.bind(deviceList.get(position), deviceActionInterface)
            } 24 -> {
                val deviceType25VH = holder as DeviceType24VH
                deviceType25VH.bind(deviceList.get(position), deviceActionInterface)
            }25 -> {
                val deviceType25VH = holder as DeviceType25VH
                deviceType25VH.bind(deviceList.get(position), deviceActionInterface)
            }
            14 -> {
                val deviceType14VH = holder as DeviceType14VH
                deviceType14VH.bind(deviceList.get(position), deviceActionInterface)
            }
            27 -> {
                val deviceType27VH = holder as DeviceType27VH
                deviceType27VH.bind(deviceList.get(position), deviceActionInterface)
            }

            else -> {
                val deviceType2VH = holder as DeviceType2VH
                deviceType2VH.bind(deviceList.get(position), deviceActionInterface)
            }
        }
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun getItemViewType(position: Int): Int {
        return deviceList.get(position).dtype
    }
}