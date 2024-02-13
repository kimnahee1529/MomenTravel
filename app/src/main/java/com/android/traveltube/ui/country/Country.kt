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

val favoriteList : MutableList<Interest> = mutableListOf(
    Interest.Favorites("맛집"),
    Interest.Favorites("등산"),
    Interest.Favorites("음악"),
    Interest.Favorites("스키"),
    Interest.Favorites("명상"),
    Interest.Favorites("박물관"),
    Interest.Favorites("골프"),
    Interest.Favorites("낚시"),
    Interest.Favorites("드라이빙"),
    Interest.Favorites("배낭여행"),
    Interest.Favorites("신혼여행"),
    Interest.plusImage(R.drawable.add)
)

val canTravelList = mutableListOf( "벨리즈", "캐나다", "코스타리카", "쿠바", "도미니카", "도미니카공화국" , "그레나다", "과테말라"
    , "아이티", "온두라스", "자메이카", "멕시코", "안틸레스", "세인트루시아", "미국", "가이아나", "베네수엘라", "볼리비아", "브라질", "수리남",
    "아르헨티나", "에콰도르", "우루과이", "칠레", "콜롬비아", "파라과이", "페루")

