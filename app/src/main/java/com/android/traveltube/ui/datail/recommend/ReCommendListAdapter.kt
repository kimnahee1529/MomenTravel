package com.android.traveltube.ui.datail.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.ItemRecommendListBinding
import com.android.traveltube.model.VideoDetailModel
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.UtilManager.loadImage

class ReCommendListAdapter(
    private val onItemClick: (VideoDetailModel) -> Unit
) : androidx.recyclerview.widget.ListAdapter<VideoDetailModel, ReCommendListAdapter.ViewHolder>(

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
            ItemRecommendListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemRecommendListBinding,
        private val onItemClick: ((VideoDetailModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoDetailModel) = with(binding) {
            item.thumbNailUrl?.let { ivRecommendListThumbnail.loadImage(it) }
            tvRecommendListTitle.text = item.title
            tvRecommendListChannel.text = item.channelTitle
            // tvRecommendListDate.text = item.publishTime?.dateFormatter()
        }
    }

}