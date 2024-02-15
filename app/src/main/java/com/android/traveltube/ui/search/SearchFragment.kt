package com.android.traveltube.ui.search

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.traveltube.R
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.databinding.FragmentSearchBinding
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.repository.YoutubeRepositoryImpl
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchResultAdapter
    private var dataList = mutableListOf<VideoBasicModel>()
    lateinit var YoutubeRepository: YoutubeRepositoryImpl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rootView = view.rootView
        rootView.setBackgroundColor(Color.WHITE)
        YoutubeRepository = YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext()))

        binding.btnGoSearch.setOnClickListener {
            if ((binding.etSearchBar.text).toString().isNotEmpty()) {
                val searchKey = binding.etSearchBar.text.toString()
                var result: List<VideoBasicModel>

                lifecycleScope.launch {
                    result = YoutubeRepository.getSearchResultFromSearch(keyword = searchKey)
                    dataList.clear()
                    dataList.addAll(result)

                    if (dataList.isNotEmpty()) {
                        binding.tvNoSearch.isVisible = false
                        adapter.notifyDataSetChanged()
                    } else binding.tvNoSearch.isVisible = true
                }

                adapter.notifyDataSetChanged()
            }
        }

        adapter = SearchResultAdapter(dataList)
        binding.rvSearchResult.adapter = adapter
        binding.rvSearchResult.layoutManager = GridLayoutManager(context, 2)

        adapter.itemOnClick = object : SearchResultAdapter.ItemOnClick {
            override fun click(data: VideoBasicModel) {
                val action =
                    SearchFragmentDirections.actionFragmentSearchToFragmentVideoDetail(data)
                if (findNavController().currentDestination?.id == R.id.fragment_search) {
                    findNavController().navigate(action)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}