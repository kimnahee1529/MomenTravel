package com.android.traveltube.ui.datail.shorts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.ui.datail.DetailUiState
import com.android.traveltube.viewmodel.BasicDetailViewModel

class DetailShortsModel(
    entity: VideoBasicModel
) : BasicDetailViewModel() {

    private val _uiState: MutableLiveData<DetailUiState> = MutableLiveData(DetailUiState.init())
    val uiState: LiveData<DetailUiState> get() = _uiState

    private val _uiChannelVideoState: MutableLiveData<List<VideoBasicModel>> = MutableLiveData()
    val uiChannelVideoState: LiveData<List<VideoBasicModel>> get() = _uiChannelVideoState

    init {
        _uiState.value = uiState.value?.copy(
            videoId = entity.id,
            videoTitle = entity.title,
            channelName = entity.channelTitle,
            channelThumbnail = entity.channelInfoModel?.channelThumbnail
        )
    }
}

class DetailShortsViewModelFactory(
    private val entity: VideoBasicModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailShortsModel::class.java)) {
            return DetailShortsModel(entity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}