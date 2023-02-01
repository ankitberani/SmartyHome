package com.smartyhome.app.main.home.devicedetails.type21

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartyhome.app.databinding.GroupSelectionItemBinding
import com.smartyhome.app.main.home.MainInterfaces


class GroupDeviceListAdapter(
    private val grpList: List<Scene>,
    private val _interface: type21Interface?
) :
    RecyclerView.Adapter<GroupDeviceListAdapter.CustomViewHolder>() {

    private lateinit var binding: GroupSelectionItemBinding


    class CustomViewHolder(
        private val binding: GroupSelectionItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(roomData: Scene) {
            binding.model = roomData
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomViewHolder {
        binding = GroupSelectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val grp = grpList[position]
        binding.cardGrp.setOnClickListener {
            _interface?.onGrpItemClicked(grpList.get(position),position)
        }
        binding.cb1.isChecked = grp.selected
        holder.bind(grp)
    }

    override fun getItemCount(): Int {
        return grpList.size
    }
}