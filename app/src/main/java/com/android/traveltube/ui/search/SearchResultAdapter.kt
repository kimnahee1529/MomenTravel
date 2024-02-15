package com.android.traveltube.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.SearchResultRecyclerviewItemBinding
import com.android.traveltube.model.db.VideoBasicModel
import com.bumptech.glide.Glide


class SearchResultAdapter(val searchResultVideo : MutableList<VideoBasicModel>)
    : RecyclerView.Adapter<SearchResultAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapter.Holder {
        val binding =
            SearchResultRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return Holder(binding)
    }

    interface ItemOnClick {
        fun click (data : VideoBasicModel)

    }

    var itemOnClick : ItemOnClick? = null

    override fun onBindViewHolder(holder: SearchResultAdapter.Holder, position: Int) {
        val item = searchResultVideo[position]
        holder.bind(item)


        holder.itemView.setOnClickListener {
            itemOnClick?.click(item)
        }

    }

    override fun getItemCount(): Int {
        return searchResultVideo.size
    }

    inner class Holder(val binding : SearchResultRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: VideoBasicModel) {
            val thumbnailImage = binding.ivThumbnail
            val videoName = binding.tvSearchVideoName
            val videoChannelName = binding.tvSearchVideoChannelName

            Glide.with(itemView.context).load(data.thumbNailUrl).into(thumbnailImage)
            videoName.text = data.title
            videoChannelName.text = data.channelTitle


        }

    }

}