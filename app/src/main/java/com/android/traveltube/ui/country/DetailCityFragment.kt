package com.android.traveltube.ui.country

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.util.query
import com.android.traveltube.R
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.databinding.FragmentDetailCityBinding
import com.android.traveltube.factory.SharedViewModelFactory
import com.android.traveltube.network.RetrofitInstance
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.viewmodel.SharedViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Query


class DetailCityFragment : Fragment() {
    private  val COUNTRY_KEY = "country"
    private var _binding: FragmentDetailCityBinding? = null

    private val binding: FragmentDetailCityBinding get() = _binding!!
    private lateinit var adapter: DetailCityAdapter
    private lateinit var sharedPref: SharedPreferences
    private var loadingDialog: LoadingDialogFragment? = null
    private val sharedViewModel by activityViewModels<SharedViewModel> {
        SharedViewModelFactory(YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext())))
    }
    private val viewModel by viewModels<DetailCityViewModel> {
        DetailCityViewModelProviderFactory(
            YoutubeRepositoryImpl(
                VideoSearchDatabase.getInstance(
                    requireContext()
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rootView = view.rootView
        rootView.setBackgroundColor(Color.WHITE)

        initView()
        initViewModel()

        adapter = DetailCityAdapter(favoriteList)

        val recyclerView = binding.rvInterest
        val increaseSpace = controlSpace(0, 75, 0, 0)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.addItemDecoration(increaseSpace)





    }

    private fun initView() {
        binding.btMoveHomeFragment.setOnClickListener {
            saveFavorite()

            val country = sharedPref.getString(COUNTRY_KEY,"")
            val favorites = sharedPref.getString("favorites","")

            val searchKey = "$country, $favorites"
            Log.d("로그디","컨트리 : ${country} , favorite :${favorites}")

            /**
             * TODO 선택된 여행지, 관심사 태그를 통하여 api로 동영상 검색
             * 검색 된 결과를 Room 저장
             * 저장 된 항목은 HomeFragment 에서 사용할 수 있어야 함.
             */
//            viewModel.getSearchVideoList() // 동영상 검색
//            viewModel.getTravelVideoList()
            viewModel.getSearchVideoList() // 동영상 검색
            viewModel.getTravelVideoList()
            viewModel.getShortsVideoList()
            showLoadingActivity()
        }
    }

    private fun initViewModel() {
        viewModel.bothSearchesSuccessful.observe(viewLifecycleOwner) { success ->
            if (success) {
                val action = DetailCityFragmentDirections.actionFragmentDetailCityToFragmentHome()
                lifecycleScope.launch {
                    if (findNavController().currentDestination?.id == R.id.fragment_detail_city) {
                        findNavController().navigate(action)
                    }
                    closeLoadingActivity()
                }
            }
        }
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    private fun showLoadingActivity() {
        loadingDialog = LoadingDialogFragment()
        loadingDialog?.show(parentFragmentManager, "LoadingDialog")
    }
    private fun closeLoadingActivity() {
        loadingDialog?.dismiss()
    }
//    fun loadSearchKey(){
//        val spf = requireActivity().getSharedPreferences("searchKey", Context.MODE_PRIVATE)
//        val country = spf.getString("country", "")
//        searchKeyWord += "${country}"
//
//    }
    private fun saveFavorite() {
        val favorite1: String
        val favorite2: String
        when (adapter.searchList.size) {
            1 -> {
                favorite1 = adapter.searchList[0]
                favorite2 = ""
            }
            2 -> {
                favorite1 = adapter.searchList[0]
                favorite2 = adapter.searchList[1]
            }
            else -> {
                favorite1 = ""
                favorite2 = ""
            }
        }
        val favorites = "${favorite1}" + "${favorite2}"
        sharedPref = requireActivity().getSharedPreferences("preferenceName", Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putString("favorites", favorites)
            apply()
        }
    }


}