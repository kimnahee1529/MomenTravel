package com.android.traveltube.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.android.traveltube.databinding.FragmentHomeBinding
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.traveltube.factory.HomeViewModelFactory
import com.android.traveltube.factory.PreferencesRepository
import com.android.traveltube.factory.SharedViewModelFactory
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
            //TODO submitList
            homeAdapter.submitList(it)
        }
    }

    private fun initViewModel() {
        sharedViewModel.getDetailItem()
//        sharedViewModel.getChannelItem()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}















