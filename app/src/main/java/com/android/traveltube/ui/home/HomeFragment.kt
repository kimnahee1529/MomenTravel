package com.android.traveltube.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.android.traveltube.databinding.FragmentHomeBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.traveltube.factory.HomeViewModelFactory
import com.android.traveltube.factory.PreferencesRepository
import com.android.traveltube.factory.SharedViewModelFactory
import com.android.traveltube.model.VideoDetailModel
import com.android.traveltube.repository.YoutubeRepository
import com.android.traveltube.viewmodel.HomeViewModel
import com.android.traveltube.viewmodel.SharedViewModel

class HomeFragment() : Fragment() {
    private val favoriteKey = "loadYoutubeData"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels {
        val preferences = requireContext().getSharedPreferences(favoriteKey, Context.MODE_PRIVATE)
        HomeViewModelFactory(YoutubeRepository(), PreferencesRepository(preferences))
    }
    private val sharedViewModel by activityViewModels<SharedViewModel> {
        SharedViewModelFactory(YoutubeRepository())
    }
    private lateinit var sendtoDetialData: VideoDetailModel
    private val homeAdapter by lazy { HomeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    //video, search api 호출
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupImageRecyclerView()
    }
    private fun setupImageRecyclerView() {
        binding.rvCatVideo.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = homeAdapter
        }
        sharedViewModel.detailItems.observe(viewLifecycleOwner){
            //TODO submitList
            homeAdapter.submitList(it)
            Log.d("sharedViewModel에서 가져온 detailItem", it.toString())
        }
    }
    private fun initViewModel(){
//        sharedViewModel.getDetailItem()
        sharedViewModel.getChannelItem()
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}













//    //홈화면에서 디테일로 동작하는지 확인하기 위한 코드, 삭제 필요
//    private fun initView() {
//        binding.btSendData.setOnClickListener {
//            // 동영상 클릭 했을 때 상세 화면 이동
//            val action = HomeFragmentDirections.actionFragmentHomeToFragmentVideoDetail(sendtoDetialData)
//            findNavController().navigate(action)
//        }
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//
//        return binding.root
//    }

    //클릭 리스너
//    override fun onHeartClick(view: View, position: Int) {
//        Log.d("리스너", "onHeartClick")
//        homeAdapter.getDocumentAtPosition(position)?.let { document ->
//            viewModel.toggleFavorite(document)
//        }
//    }

