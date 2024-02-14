package com.android.traveltube.ui.favorite

enum class ViewType {
    ITEM_VIEW_TYPE_NORMAL,
    ITEM_VIEW_TYPE_EDIT,
    ;

    companion object {
        fun getEntryType(
            ordinal: Int?
        ): ViewType {
            return ViewType.values().firstOrNull {
                it.ordinal == ordinal
            } ?: ITEM_VIEW_TYPE_NORMAL
        }
    }
}