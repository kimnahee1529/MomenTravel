package com.android.traveltube.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.ItemFavoriteListBinding
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.DateManager.formatNumber
import com.android.traveltube.utils.UtilManager.loadVideoImage

class FavoriteListAdapter(
    private val onItemClick: (VideoBasicModel) -> Unit
) : androidx.recyclerview.widget.ListAdapter<VideoBasicModel, FavoriteListAdapter.ViewHolder>(

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
            ItemFavoriteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemFavoriteListBinding,
        private val onItemClick: ((VideoBasicModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoBasicModel) = with(binding) {
            item.thumbNailUrl?.let { ivFavoriteVideoThumbnail.loadVideoImage(it) }
            tvFavoriteVideoTitle.text = item.title
            tvFavoriteVideoDate.text = item.publishTime?.dateFormatter()
            tvFavoriteVideoViewCount.text = item.videoViewCountModel?.viewCount?.formatNumber()
            tvFavoriteChannelName.text = item.channelTitle
            item.channelInfoModel?.channelThumbnail?.let { ivFavoriteChannelThumbnail.loadVideoImage(it) }
        }
    }

}