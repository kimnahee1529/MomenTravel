package com.android.traveltube.factory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.traveltube.repository.YoutubeRepository
import com.android.traveltube.viewmodel.SharedViewModel

class SharedViewModelFactory(
    private val youtubeRepository: YoutubeRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("Factory", "searchImages")
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(youtubeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}