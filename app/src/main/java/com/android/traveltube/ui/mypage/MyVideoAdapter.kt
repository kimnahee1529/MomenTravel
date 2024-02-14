package com.android.traveltube.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.RecyclerviewMyvideoMyhistoryBinding
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.utils.DateManager.formatNumber
import com.android.traveltube.utils.UtilManager.loadVideoImage

class MyVideoAdapter(
    private val onItemClick: (VideoBasicModel) -> Unit,
    private val onItemLongClick: (VideoBasicModel, Int) -> Unit
) : ListAdapter<VideoBasicModel, MyVideoAdapter.MyViewHolder>(DiffCallback()) {

    inner class MyViewHolder(
        private val binding: RecyclerviewMyvideoMyhistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }

            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemLongClick(getItem(position), position)
                    return@setOnLongClickListener true
                }
                false
            }
        }

        fun bind(item: VideoBasicModel) {
            with(binding) {
                item.thumbNailUrl?.let { ivMyVideoHistoryImage.loadVideoImage(it) }
                tvTitle.text = item.title
                tvNickName.text = item.channelTitle
                tvViews.text = item.videoViewCountModel?.viewCount?.formatNumber()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewMyvideoMyhistoryBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun deleteItem(item: VideoBasicModel) {
        val newList = currentList.toMutableList()
        newList.remove(item)
        submitList(newList)
    }

    fun filter(query: String?) {
        val filterList = if (query.isNullOrEmpty()) {
            currentList.toList()
        } else {
            currentList.filter { it.title?.contains(query, ignoreCase = true) ?: false }
        }
        submitList(filterList)
    }

    class DiffCallback : DiffUtil.ItemCallback<VideoBasicModel>() {
        override fun areItemsTheSame(oldItem: VideoBasicModel, newItem: VideoBasicModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VideoBasicModel, newItem: VideoBasicModel): Boolean {
            return oldItem == newItem
        }
    }
}
