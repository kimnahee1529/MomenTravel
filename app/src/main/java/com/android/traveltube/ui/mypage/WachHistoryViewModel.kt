package com.android.traveltube.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.traveltube.repository.YoutubeRepositoryImpl
import kotlinx.coroutines.launch

class WatchHistoryViewModel(private val youtubeRepositoryImpl: YoutubeRepositoryImpl) : ViewModel() {

    fun deleteWatchHistoryItem(videoId: String) {
        viewModelScope.launch {
            youtubeRepositoryImpl.updateSavedStatus(videoId, false)
        }
    }
}