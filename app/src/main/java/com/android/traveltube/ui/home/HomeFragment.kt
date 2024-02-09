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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.traveltube.factory.HomeViewModelFactory
import com.android.traveltube.factory.PreferencesRepository
import com.android.traveltube.factory.SharedViewModelFactory
import com.android.traveltube.repository.YoutubeRepository
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.viewmodel.HomeViewModel
import com.android.traveltube.viewmodel.SharedViewModel
import java.util.Date

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupImageRecyclerView()
    }

    private fun setupImageRecyclerView() {
        //각 아이템 클릭 시 Detail 화면으로 이동
        val homeAdapter = HomeAdapter { videoDetailModel ->
            val action =
                HomeFragmentDirections.actionFragmentHomeToFragmentVideoDetail(videoDetailModel)
            findNavController().navigate(action)
        }
        binding.rvCatVideo.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = homeAdapter
        }
        sharedViewModel.detailItems.observe(viewLifecycleOwner) {
            homeAdapter.submitList(it)
        }
    }

    //video, search api 호출
    private fun initViewModel() {
//        sharedViewModel.getDetailItem() //채널 썸네일 받아오기
//        sharedViewModel.getChannelItem()
//        sharedViewModel.getVideoViewCount()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}