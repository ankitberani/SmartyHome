package com.smartyhome.app.main.home.roomlisting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartyhome.app.databinding.SceneListItemBinding
import com.smartyhome.app.main.home.scene.Scene


class SceneListAdapter(
    private val transactionList: List<Scene>
) :
    RecyclerView.Adapter<SceneListAdapter.CustomViewHolder>() {

    private lateinit var binding: SceneListItemBinding


    class CustomViewHolder(
        private val binding: SceneListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(_scene: Scene) {
            binding.model = _scene
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomViewHolder {

        binding = SceneListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val largeNews = transactionList[position]
        holder.bind(largeNews)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }
}