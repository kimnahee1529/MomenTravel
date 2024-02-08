package com.android.traveltube.ui.datail.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.ItemChannelOtherVideoListBinding
import com.android.traveltube.model.VideoDetailModel
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.UtilManager.loadImage

class ChannelListAdapter(
    private val onItemClick: (VideoDetailModel) -> Unit
) : androidx.recyclerview.widget.ListAdapter<VideoDetailModel, ChannelListAdapter.ViewHolder>(

    object : DiffUtil.ItemCallback<VideoDetailModel>() {

        override fun areItemsTheSame(
            oldItem: VideoDetailModel,
            newItem: VideoDetailModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: VideoDetailModel,
            newItem: VideoDetailModel
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
        private val onItemClick: ((VideoDetailModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoDetailModel) = with(binding) {
            item.thumbNailUrl?.let { ivChannelOtherVideoThumbnail.loadImage(it) }
            // TODO 채널 thumbnail
            tvChannelListTitle.text = item.title
            tvChannelListName.text = item.channelTitle
            // tvChannelListDate.text = item.publishTime?.dateFormatter()
        }
    }

}