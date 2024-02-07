package com.android.traveltube.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.RecyclerviewCatVideoItemBinding
import com.android.traveltube.utils.UtilManager.loadImage

//class HomeAdapter():
//    ListAdapter<SearchItemModel, HomeAdapter.Holder>(DocumentDiffCallback()) {
//
//    class DocumentDiffCallback : DiffUtil.ItemCallback<SearchItemModel>() {
//        override fun areItemsTheSame(oldItem: SearchItemModel, newItem: SearchItemModel): Boolean {
//            // 객체의 고유 식별자를 비교
//            return oldItem.url == newItem.url
//        }
//
//        override fun areContentsTheSame(oldItem: SearchItemModel, newItem: SearchItemModel): Boolean {
//            // TODO : 그냥 ==은 왜 안되는지 확인하기
//            return oldItem.title == newItem.title
//        }
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val binding =
//            RecyclerviewCatVideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return Holder(binding)
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        val item = getItem(position) // ListAdapter에서는 getItem() 메서드 사용
//        holder.bind(item)
//    }
//
//    inner class Holder(private val binding: RecyclerviewCatVideoItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(document: SearchItemModel) {
//            binding.ivThumbnail.loadImage(document.url)
//        }
//    }
//
//}