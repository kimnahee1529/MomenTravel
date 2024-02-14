package com.android.traveltube.ui.favorite

import com.android.traveltube.R

data class EditModeUiState(
    val isEditMode: Boolean,
    val buttonText: Int
) {
    companion object {
        fun init() = EditModeUiState(
            isEditMode = false,
            buttonText = R.string.edit,
        )
    }
}