package com.android.traveltube.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.traveltube.factory.PreferencesRepository
import com.android.traveltube.repository.YoutubeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val youtubeRepository: YoutubeRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    fun loadTrendingVideos(region: String = "KR"){
        viewModelScope.launch {
            kotlin.runCatching {
                val videos = youtubeRepository.getTrendingVideos(region)
                Log.d("videos", videos.toString())
            }.onFailure { exception ->
                withContext(Dispatchers.Main) {
                    Log.e("videos Error", "Failed to fetch trending videos", exception)
                }
            }
        }
    }

    fun loadSearchingVideos(region: String = "KR"){
        viewModelScope.launch {
            kotlin.runCatching {
                val videos = youtubeRepository.getSearchingVideos(region)
                Log.d("search", videos.toString())
            }.onFailure { exception ->
                withContext(Dispatchers.Main) {
                    Log.e("search Error", "Failed to fetch trending videos", exception)
                }
            }
        }
    }
}