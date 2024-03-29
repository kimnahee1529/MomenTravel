package com.android.traveltube.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.android.traveltube.databinding.FragmentHomeBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.factory.HomeViewModelFactory
import com.android.traveltube.factory.PreferencesRepository
import com.android.traveltube.factory.SharedViewModelFactory
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.utils.Constants.NAME_KEY
import com.android.traveltube.utils.Constants.PREFERENCE_NAME
import com.android.traveltube.viewmodel.HomeViewModel
import com.android.traveltube.viewmodel.SharedViewModel

class HomeFragment : Fragment() {
    private val favoriteKey = "loadYoutubeData"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPreferences
    private val viewModel: HomeViewModel by viewModels {
        val preferences = requireContext().getSharedPreferences(favoriteKey, Context.MODE_PRIVATE)
        HomeViewModelFactory(
            YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext())),
            PreferencesRepository(preferences)
        )
    }
    private val sharedViewModel by activityViewModels<SharedViewModel> {
        SharedViewModelFactory(YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext())))
    }

    //각 아이템 클릭 시 Detail 화면으로 이동
    private val homeListAdapter by lazy {
        SearchAdapter { videoDetailModel ->
            val action =
                HomeFragmentDirections.actionFragmentHomeToFragmentVideoDetail(videoDetailModel)
            findNavController().navigate(action)
        }
    }
    private val travelListAdapter by lazy {
        TravelAdapter { videoDetailModel ->
            val action =
                HomeFragmentDirections.actionFragmentHomeToFragmentVideoDetail(videoDetailModel)
            findNavController().navigate(action)
        }
    }
    private val shortsListAdapter by lazy {
        ShortsAdapter { videoDetailModel ->
            val action =
                HomeFragmentDirections.actionFragmentHomeToFragmentShorts(videoDetailModel)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val savedName = sharedPref.getString(NAME_KEY, "")

        binding.tvRecommendVideo.text = if (savedName.isNullOrBlank()) {
            "하나둘셋님을 위한 여행지"
        } else {
            "${savedName}님을 위한 여행지"
        }
        initViewModel()
        setupImageRecyclerView()
        onRechoiceClickListener()
    }

    private fun onRechoiceClickListener() {
        binding.btBtnRechoice.setOnClickListener {
            val action = HomeFragmentDirections.actionFragmentHomeToFragmentCountry()
            findNavController().navigate(action)
        }
    }

    private fun setupImageRecyclerView() {
        binding.rvSearchVideo.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = homeListAdapter
        }
        binding.rvTravelVideo.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = travelListAdapter
        }
        binding.rvShortsVideo.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = shortsListAdapter
        }
    }

    //video, search api 호출
    private fun initViewModel() {
        sharedViewModel.searchResults.observe(viewLifecycleOwner) {
            homeListAdapter.submitList(it)
        }
        sharedViewModel.searchTravelResults.observe(viewLifecycleOwner) {
            travelListAdapter.submitList(it)
        }
        sharedViewModel.searchShortsTravelResults.observe(viewLifecycleOwner) {
            shortsListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}