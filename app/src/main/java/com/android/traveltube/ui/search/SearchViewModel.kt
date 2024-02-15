package com.android.traveltube.ui.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.android.traveltube.data.db.ModelType
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.viewmodel.BasicDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



