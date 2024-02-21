## 📂 폴더 및 파일 구조
- `data/` : 애플리케이션의 데이터 로직과 API 호출을 처리합니다.
    - `channel/` : ChannelModel 하위에 더 많음 데이터 클래스들이 있음
        - `ChannelModel`
    -  `search/` : SearchModel 하위에 더 많음 데이터 클래스들이 있음
        - `SearchModel`
    - `db/`
        - `converter/`
        - `dao/`
        - `ModelType`
        - `VideoSearchDatabase`
    - `model/`
        - `db/`
            - `ChannelInfoModel`
            - `VideoBasicModel`
            - `VideoViewCountModel`
        - `VideoDetailModel/`
- `factory/` : Repository 생성을 위한 Factory 클래스
    - `SharedViewModelFactory`
- `main/`
  - `MainActivity` : 애플리케이션의 메인 액티비티
- `model/`
  - `db/` : Room DB 테이블에 넣을 데이터 클래스
      - `ChannelInfoModel`
      - `VideoBasicModel`
      - `VideoViewCountModel`
  - `VideoDetailModel`
- `ui/` : 애플리케이션의 UI를 담당하는 Fragment/Adapter와 viewModel이 위치합니다.
  - `country/`
    - `FavoriteImageAdapter` : 북마크 화면에서 사용하는 리사이클러뷰 어댑터
    - `FavoritesFragment` : 북마크 화면 UI 및 로직 처리
    - `FavoritesViewModel` : 북마크 화면의 데이터 및 로직 처리를 담당
  - `detail/`
    - `ImageSearchAdapter` : 이미지 검색 화면에서 사용하는 리사이클러뷰 어댑터
    - `ImageSearchFragment` : 이미지 검색 화면 UI 및 로직 처리
    - `ImageSearchViewModel` : 이미지 검색 화면의 데이터 및 로직 처리를 담당
  - `favorite/`
  - `home/`
  - `mypage/`
  - `search/`
  - `splash/`
- `network/`
    - `ImageNetworkInterface` : 이미지 API 호출 인터페이스 정의
    - `VideoNetworkInterface` : 이미지 API 호출 인터페이스 정의
    - `NetWorkClient` : Retrofit 인스턴스 및 설정 관련 로직

- `repository/` : ViewModel 생성을 위한 Repository 클래스
    - `ImageRepository`
    - `PreferencesRepository`
 
- `utils/` : 프로젝트 전반에 사용되는 유틸리티 함수 모음
    - `FormatManager` : 날짜 형식 변경, 이미지 불러오는 함수 관리
    - `UtilityKeyboard` : 키보드 숨기는 함수
 

- `ViewPager` : 뷰페이저 관련 함수
- `SharedViewModel` : 여러 Fragment 간에 공유되는 데이터 관리



## 프로젝트 소개

여행 중의 행복한 순간들을 볼 수 있는 영상 앱

## 개발 기간

* 24.02.05 ~ 24.02.16

### 여행지 선택 화면 및 관심사 선택 (은명준)

1. 여행지 선택 시 나라를 선택 안하면 토스트메세지가 나오면서 넘어가지 못하게 만들고, 검색기능 구성
2. 관심사 선택 시 최대 2개까지 선택 가능, 추가하고 싶은 관심사를 넣는 기능 추가, 건너뛰기 가능
3. 관심사 추가 시 다이얼로그로 추가 기능 구현

### 홈화면 (김나희)

1. 관심사 선택후에 홈화면이 나오고, 여행지 및 관심사에 관한 영상썸네일 등이 나오게 구성 (기본영상 및 쇼츠영상)
2. 여행지 재선택 기능 구현

### 검색화면 (은명준)

1. 검색 기능 구성
2. 검색에 대한 예외 처리등의 메세지를 표시

### 좋아요화면 (심수빈)

1. 상세화면에서 좋아요를 하면 이화면에 출력
2. 편집기능 구성
3. 좋아요 최신순/가나다순으로 나열 기능 구성

### 상세화면 (심수빈)

1. 유튜브 동영상 나오게 출력
2. 즐겨찾기 기능 추가, 공유기능 추가, 더보기 기능 추가

### 마이비디오화면 (김우진)

1. 프로필 화면 구성 및 시청기록 리사이클러뷰 구성, 시청기록 검색 구성
2. 프로필 수정을 위해서 Custom_Dialog로 만들고, TextWatch를 써서 제한을 걸음
3. 시청기록 리사이클러뷰를 위한 Ui를 구성, 삭제를 위한 Bottom_sheet_Dialog를 만듬
4. 시청기록 검색에대한 예외 처리 등의 메세지를 표시
