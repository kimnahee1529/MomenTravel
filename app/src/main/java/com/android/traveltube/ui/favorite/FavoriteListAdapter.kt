package com.android.traveltube.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.ItemFavoriteEditListBinding
import com.android.traveltube.databinding.ItemFavoriteListBinding
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.DateManager.formatNumber
import com.android.traveltube.utils.UtilManager.loadVideoImage

class FavoriteListAdapter(
    private val onItemClick: (VideoBasicModel, Int) -> Unit,
    var isEditMode: Boolean
) : androidx.recyclerview.widget.ListAdapter<VideoBasicModel, RecyclerView.ViewHolder>(

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.ITEM_VIEW_TYPE_NORMAL.ordinal -> {
                val binding =
                    ItemFavoriteListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                NormalViewHolder(binding, onItemClick)
            }

            ViewType.ITEM_VIEW_TYPE_EDIT.ordinal -> {
                val binding =
                    ItemFavoriteEditListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                EditViewHolder(binding, onItemClick)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isEditMode) ViewType.ITEM_VIEW_TYPE_EDIT.ordinal else ViewType.ITEM_VIEW_TYPE_NORMAL.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NormalViewHolder -> holder.bind(
                getItem(position),
                ViewType.ITEM_VIEW_TYPE_NORMAL.ordinal
            )

            is EditViewHolder -> holder.bind(
                getItem(position),
                ViewType.ITEM_VIEW_TYPE_EDIT.ordinal
            )

            else -> throw IllegalArgumentException("Invalid ViewHolder type")
        }
    }

    class NormalViewHolder(
        private val binding: ItemFavoriteListBinding,
        private val onItemClick: (VideoBasicModel, Int) -> Unit?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoBasicModel, viewType: Int) = with(binding) {
            item.thumbNailUrl?.let { ivFavoriteVideoThumbnail.loadVideoImage(it) }
            tvFavoriteVideoTitle.text = item.title
            tvFavoriteVideoDate.text = item.publishTime?.dateFormatter()
            tvFavoriteVideoViewCount.text =
                "조회수 ${item.videoViewCountModel?.viewCount?.formatNumber()}"
            tvFavoriteChannelName.text = item.channelTitle

            binding.root.setOnClickListener {
                onItemClick.invoke(item, viewType)
            }
        }
    }

    class EditViewHolder(
        private val binding: ItemFavoriteEditListBinding,
        private val onItemClick: (VideoBasicModel, Int) -> Unit?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoBasicModel, viewType: Int) = with(binding) {
            item.thumbNailUrl?.let { ivFavoriteVideoThumbnail.loadVideoImage(it) }
            tvFavoriteVideoTitle.text = item.title
            tvFavoriteVideoDate.text = item.publishTime?.dateFormatter()
            tvFavoriteVideoViewCount.text =
                "조회수 ${item.videoViewCountModel?.viewCount?.formatNumber()}"
            tvFavoriteChannelName.text = item.channelTitle

            btFavoriteEdit.setOnClickListener {
                onItemClick.invoke(item, viewType)
            }
        }
    }

}