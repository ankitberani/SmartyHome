package com.smartyhome.app.main.home.roomdevices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartyhome.app.R
import com.smartyhome.app.main.home.roomdevices.devicetypeVH.DeviceType2VH
import com.smartyhome.app.main.home.roomdevices.devicetypeVH.ViewHolderGridDevice


class RoomDeviceAdapterGrid(
    private val deviceList: List<DeviceModelManual>,
    private val deviceActionInterface: DeviceActionInterface
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_grid, parent, false)
                ViewHolderGridDevice(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.device_item_grid, parent, false)
                ViewHolderGridDevice(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (deviceList!!.size == 0) {
            return
        }
        val viewHolder = holder as ViewHolderGridDevice
        viewHolder.bind(deviceList.get(position), deviceActionInterface)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun getItemViewType(position: Int): Int {
        return deviceList.get(position).dtype
    }
}