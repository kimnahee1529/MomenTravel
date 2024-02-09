package com.android.traveltube.ui.datail.recommend

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.ItemRecommendListBinding
import com.android.traveltube.model.db.VideoRecommendModel
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.UtilManager.loadImage

class ReCommendListAdapter(
    private val onItemClick: (VideoRecommendModel) -> Unit
) : androidx.recyclerview.widget.ListAdapter<VideoRecommendModel, ReCommendListAdapter.ViewHolder>(

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
            ItemRecommendListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemRecommendListBinding,
        private val onItemClick: ((VideoRecommendModel) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoRecommendModel) = with(binding) {
            item.thumbNailUrl?.let { ivRecommendListThumbnail.loadImage(it) }
            tvRecommendListTitle.text = item.title
            tvRecommendListChannel.text = item.channelTitle
            tvRecommendListDate.text = item.publishTime?.dateFormatter()
            Log.d("TAG", "${item.title}")
        }
    }

}