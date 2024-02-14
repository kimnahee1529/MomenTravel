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

val canTravelList = mutableListOf("벨리즈", "캐나다", "코스타리카", "쿠바", "도미니카", "도미니카공화국" , "그레나다", "과테말라"
    , "아이티", "온두라스", "자메이카", "멕시코", "안틸레스", "세인트루시아", "미국", "가이아나", "베네수엘라", "볼리비아", "브라질", "수리남",
    "아르헨티나", "에콰도르", "우루과이", "칠레", "콜롬비아", "파라과이", "페루","아르메니아", "오스트리아", "벨라루스", "벨기에", "불가리아", "크로아티아",
    "체코", "덴마크", "에스토니아", "핀란드", "프랑스", "독일", "그리스", "헝가리", "아이슬란드", "아일랜드", "이탈리아", "카자흐스탄", "라트비아",
    "리히텐슈타인", "리투아니아", "룩셈부르크", "마케도니아", "몰타", "몰도바", "모나코", "몬테네그로", "네덜란드", "노르웨이", "폴란드", "포르투갈",
    "루마니아", "러시아", "산마리노", "세르비아", "슬로바키아", "슬로베니아", "스페인", "스웨덴", "스위스", "터키", "우크라이나", "영국", "바티칸", "아프가니스탄",
    "아르메니아", "바레인", "방글라데시", "부탄", "브루나이", "캄보디아", "중국", "인도", "인도네시아", "이란", "이라크", "이스라엘", "일본", "요르단", "카자흐스탄"
    , "쿠에이트", "키르기스스탄", "라오스", "레바논", "말레이시아", "몰디브", "몽골", "부르마", "네팔", "오만", "파키스탄", "팔레스타인", "필리핀", "카타르", "러시아",
    "사우디아라비아", "싱가폴", "스리랑카", "시리아", "타이완", "타지키스탄", "태국", "동티모르", "아랍에미리트", "우즈베키스탄", "베트남", "예멘",  "알제리", "앙골라",
    "보츠와나", "카메룬", "중앙아프리카공화국", "코트디부아르", "콩고", "이집트", "에티오피아", "가봉", "감비아", "가나", "기니", "케냐",  "리비아", "마다가스카르",
    "말리", "모로코", "나미비아", "나이지리아", "세네갈", "남아프리카공화국","수단", "탄자니아", "토고", "튀니스", "우간다", "잠비아",
    "짐바브웨", "호주", "파푸아뉴기니", "뉴질랜드")

