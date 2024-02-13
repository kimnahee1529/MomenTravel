package com.android.traveltube.ui.datail.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.ItemChannelOtherVideoListBinding
import com.android.traveltube.model.db.VideoRecommendModel
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.DateManager.formatNumber
import com.android.traveltube.utils.UtilManager.loadVideoImage

class ChannelListAdapter(
    private val onItemClick: (VideoRecommendModel) -> Unit
) : androidx.recyclerview.widget.ListAdapter<VideoRecommendModel, ChannelListAdapter.ViewHolder>(

    object : DiffUtil.ItemCallback<VideoRecommendModel>() {

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
            return oldItem == newItem
        }
    }

) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemChannelOtherVideoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemChannelOtherVideoListBinding,
        private val onItemClick: ((VideoRecommendModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoRecommendModel) = with(binding) {
            item.thumbNailUrl?.let { ivChannelOtherVideoThumbnail.loadVideoImage(it) }
            tvChannelListTitle.text = item.title
            tvChannelViewCount.text = item.videoViewCountModel?.viewCount?.formatNumber()
            tvChannelListDate.text = item.publishTime?.dateFormatter()
        }
    }

}