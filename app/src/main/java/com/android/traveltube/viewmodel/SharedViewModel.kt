package com.android.traveltube.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.traveltube.data.db.ModelType
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.repository.YoutubeRepositoryImpl

class SharedViewModel(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl
) : ViewModel() {
    val searchResults: LiveData<List<VideoBasicModel>> get() = youtubeRepositoryImpl.getVideos(ModelType.VIDEO_RECOMMEND)
    val searchTravelResults: LiveData<List<VideoBasicModel>> get() = youtubeRepositoryImpl.getVideos(ModelType.VIDEO_CATEGORY_TRAVEL)
    val searchShortsTravelResults: LiveData<List<VideoBasicModel>> get() = youtubeRepositoryImpl.getVideos(ModelType.VIDEO_CATEGORY_SHORTS)
}