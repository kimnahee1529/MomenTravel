package com.android.traveltube.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.RecyclerviewHomeShortsVideoBinding
import com.android.traveltube.model.db.VideoRecommendModel
import com.android.traveltube.utils.UtilManager.loadVideoImage

class ShortsAdapter(private val onItemClicked: (VideoRecommendModel) -> Unit) :
    ListAdapter<VideoRecommendModel, ShortsAdapter.Holder>(DocumentDiffCallback()) {
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
            RecyclerviewHomeShortsVideoBinding.inflate(
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
    inner class Holder(private val binding: RecyclerviewHomeShortsVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: VideoRecommendModel) {
            data.thumbNailUrl?.let {binding.ivThumbnail.loadVideoImage(it)}
            binding.tvShortsTitle.text = data.title
            binding.root.setOnClickListener {
                onItemClicked(data)
            }
        }
    }
}