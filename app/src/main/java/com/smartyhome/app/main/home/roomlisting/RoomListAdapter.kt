package com.smartyhome.app.main.home.roomlisting

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartyhome.app.R
import com.smartyhome.app.databinding.RoomItemListBinding
import com.smartyhome.app.main.home.MainInterfaces


class RoomListAdapter(
    private val roomList: List<Room>,
    private val _interface: MainInterfaces
) :
    RecyclerView.Adapter<RoomListAdapter.CustomViewHolder>() {

    private lateinit var binding: RoomItemListBinding


    class CustomViewHolder(
        private val binding: RoomItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(roomData: Room) {
            binding.model = roomData
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomViewHolder {
        binding = RoomItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val room = roomList[position]
        if (room.deviceCount == 0)
            binding.tvTotalDevice.visibility = View.INVISIBLE
        else
            binding.tvTotalDevice.setText(room.deviceCount.toString() + " Devices")
        binding.cardRoom.setOnClickListener {
            Log.e(
                "TAG",
                "Room Clicked $position <> ${roomList.get(holder.adapterPosition).name} hold pos ${holder.adapterPosition}"
            )
            _interface.onRoomClicked(roomList.get(holder.adapterPosition))
        }

        if (roomList.get(position).background.isNullOrEmpty()) {
            binding.cardRoom.setBackgroundResource(getDrawableByName(
                "",
                binding.cardRoom.context
            ))
            binding.ivRoomIcon.visibility = View.VISIBLE
        } else {
            binding.ivRoomIcon.visibility = View.INVISIBLE
            binding.cardRoom.setBackgroundResource(
                getDrawableByName(
                    roomList.get(position).background,
                    binding.cardRoom.context
                )
            )
        }
        binding.cardRoom.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                _interface.onRoomLongClicked(holder.adapterPosition)
                return true
            }
        })

        holder.bind(room)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    fun getDrawableByName(gradename: String?, context: Context): Int {
       var drawableName = gradename
        if (drawableName.isNullOrEmpty())
            drawableName = "gradzero"
        val resources = context.resources
        val resourceId = resources.getIdentifier(
            drawableName, "drawable",
            context.packageName
        )
        return resourceId
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}