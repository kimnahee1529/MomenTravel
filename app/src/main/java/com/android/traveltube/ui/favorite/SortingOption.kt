package com.android.traveltube.ui.favorite


data class SortingOption(
    val position: Int,
    val title: String
) {
    companion object {
        fun init() = SortingOption(
            position = 0,
            title = "기본",
        )
    }
}