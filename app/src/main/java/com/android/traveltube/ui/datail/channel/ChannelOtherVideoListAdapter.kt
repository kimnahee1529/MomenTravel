package com.android.traveltube.ui.datail.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.ItemChannelOtherVideoListBinding
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.DateManager.formatNumber
import com.android.traveltube.utils.UtilManager.loadVideoImage

class ChannelOtherVideoListAdapter(
    private val onItemClick: (VideoBasicModel) -> Unit
) : androidx.recyclerview.widget.ListAdapter<VideoBasicModel, ChannelOtherVideoListAdapter.ViewHolder>(

    object : DiffUtil.ItemCallback<VideoBasicModel>() {

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
        private val onItemClick: ((VideoBasicModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VideoBasicModel) = with(binding) {
            item.thumbNailUrl?.let { ivListThumbnail.loadVideoImage(it) }
            tvListTitle.text = item.title
            tvListViewCount.text = "조회수 ${item.videoViewCountModel?.viewCount?.formatNumber()}"
            tvListDate.text = item.publishTime?.dateFormatter()
            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

}