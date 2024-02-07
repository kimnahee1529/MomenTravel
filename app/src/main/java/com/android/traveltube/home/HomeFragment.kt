package com.android.traveltube.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.traveltube.databinding.FragmentHomeBinding
import androidx.fragment.app.viewModels
import com.android.traveltube.factory.HomeViewModelFactory
import com.android.traveltube.factory.PreferencesRepository
import com.android.traveltube.repository.YoutubeRepository

class HomeFragment() : Fragment() {
    private val favoriteKey = "loadYoutubeData"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels {
        val preferences = requireContext().getSharedPreferences(favoriteKey, Context.MODE_PRIVATE)
        HomeViewModelFactory(YoutubeRepository(), PreferencesRepository(preferences))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

//    private fun initView() {
//        binding.btSendData.setOnClickListener {
//            // 동영상 클릭 했을 때 상세 화면 이동
//            val action = HomeFragmentDirections.actionFragmentHomeToFragmentVideoDetail("youtube_id")
//            findNavController().navigate(action)
//        }
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//
//        return binding.root
//    }


    //video, search api 호출
    private fun initView(){
//        viewModel.loadTrendingVideos("KR")
//        viewModel.loadSearchingVideos("KR")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}