package com.android.traveltube.ui.datail.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.ItemRecommendListBinding
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.UtilManager.loadVideoImage

class ReCommendListAdapter(
    private val onItemClick: (VideoBasicModel) -> Unit
) : androidx.recyclerview.widget.ListAdapter<VideoBasicModel, ReCommendListAdapter.ViewHolder>(

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
            ItemRecommendListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemRecommendListBinding,
        private val onItemClick: ((VideoBasicModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoBasicModel) = with(binding) {
            item.thumbNailUrl?.let { ivRecommendListThumbnail.loadVideoImage(it) }
            tvRecommendListTitle.text = item.title
            tvRecommendListChannel.text = item.channelTitle
            tvRecommendListDate.text = item.publishTime?.dateFormatter()
        }
    }

}