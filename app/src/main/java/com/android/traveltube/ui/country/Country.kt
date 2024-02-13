package com.android.traveltube.ui.country

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.R

data class Country(
    val flag : Int,
    var countryName : String,
    var isSelected :Boolean = false
) {
    companion object {
        val countryList = mutableListOf(
            Country(R.drawable.europe, "유럽"),
            Country(R.drawable.eastasia, "동남아"),
            Country(R.drawable.america, "아메리카"),
            Country(R.drawable.brazil, "브라질"),
            Country(R.drawable.australia, "호주"),
            Country(R.drawable.china, "중국"),
            Country(R.drawable.england, "영국"),
            Country(R.drawable.france, "프랑스"),
            Country(R.drawable.german, "독일"),
            Country(R.drawable.hawaii, "하와이"),
            Country(R.drawable.india, "인도"),
            Country(R.drawable.italy, "이탈리아"),
            Country(R.drawable.japan, "일본"),
            Country(R.drawable.spain, "스페인"),
            Country(R.drawable.switzerland, "스위스"),
            Country(R.drawable.thailand, "태국"),
            Country(R.drawable.vietnam, "베트남"),
        )
    }
}

class controlSpace(private val left : Int , private val top : Int ,private val right: Int,
                   private val bottom : Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(left, top, right, bottom)
    }
}

sealed class Interest {
    data class Favorites(val favorite : String ,var isSelected: Boolean = false) : Interest()
    data class plusImage(val image : Int) : Interest()
}


