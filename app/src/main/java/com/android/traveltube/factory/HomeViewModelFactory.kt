package com.android.traveltube.factory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.traveltube.viewmodel.HomeViewModel
import com.android.traveltube.repository.YoutubeRepositoryImpl

class HomeViewModelFactory(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl,
    private val preferencesRepository: PreferencesRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("Factory", "searchImages")
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(youtubeRepositoryImpl, preferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}