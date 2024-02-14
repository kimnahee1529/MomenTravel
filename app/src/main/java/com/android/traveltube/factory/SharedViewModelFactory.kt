package com.android.traveltube.factory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.viewmodel.SharedViewModel

class SharedViewModelFactory(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("Factory", "searchImages")
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(youtubeRepositoryImpl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}