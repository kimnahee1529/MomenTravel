package com.android.traveltube.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.RecyclerviewHomeSelectVideoBinding
import com.android.traveltube.model.db.VideoRecommendModel
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.DateManager.formatNumber
import com.android.traveltube.utils.UtilManager.loadChannelImage
import com.android.traveltube.utils.UtilManager.loadVideoImage

class SearchAdapter(private val onItemClicked: (VideoRecommendModel) -> Unit) :
    ListAdapter<VideoRecommendModel, SearchAdapter.Holder>(DocumentDiffCallback()) {
    class DocumentDiffCallback : DiffUtil.ItemCallback<VideoRecommendModel>() {
        override fun areItemsTheSame(
            oldItem: VideoRecommendModel,
            newItem: VideoRecommendModel
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: VideoRecommendModel,
            newItem: VideoRecommendModel
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
        fun bind(data: VideoRecommendModel) {
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