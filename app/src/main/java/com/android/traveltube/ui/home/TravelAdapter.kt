package com.android.traveltube.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.RecyclerviewHomeSelectVideoBinding
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.DateManager.formatNumber
import com.android.traveltube.utils.UtilManager.loadChannelImage
import com.android.traveltube.utils.UtilManager.loadVideoImage

class TravelAdapter(private val onItemClicked: (VideoBasicModel) -> Unit) :
    ListAdapter<VideoBasicModel, TravelAdapter.Holder>(DocumentDiffCallback()) {
    class DocumentDiffCallback : DiffUtil.ItemCallback<VideoBasicModel>() {
        override fun areItemsTheSame(
            oldItem: VideoBasicModel,
            newItem: VideoBasicModel
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: VideoBasicModel,
            newItem: VideoBasicModel
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            RecyclerviewHomeSelectVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return Holder(binding)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position) // ListAdapter에서는 getItem() 메서드 사용
        holder.bind(item)
    }
    inner class Holder(private val binding: RecyclerviewHomeSelectVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: VideoBasicModel) {
            data.thumbNailUrl?.let {binding.ivThumbnail.loadVideoImage(it)}
            data.channelInfoModel?.channelThumbnail?.let {binding.ivChannelThumbnail.loadChannelImage(it)}
            binding.ivThumbnail.clipToOutline = true
            binding.tvTitle.text = data.title
            binding.tvChannelTitle.text = data.channelTitle  + "ㆍ"
            binding.root.setOnClickListener {
                onItemClicked(data)
            }
            binding.tvDate.text = data.publishTime?.dateFormatter()
            binding.tvViewCount.text = data.videoViewCountModel?.viewCount?.formatNumber()  + "ㆍ"
        }
    }
}