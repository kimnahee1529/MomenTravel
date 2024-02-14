package com.android.traveltube.ui.search

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.traveltube.R
import com.android.traveltube.data.db.ModelType
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.data.search.Item
import com.android.traveltube.databinding.FragmentSearchBinding
import com.android.traveltube.factory.SharedViewModelFactory
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.network.RetrofitInstance
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.ui.datail.VideoDetailFragmentDirections
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : SearchResultAdapter
    private var dataList = mutableListOf<VideoBasicModel>()
    lateinit var  YoutubeRepository : YoutubeRepositoryImpl
  //  private var dataList = mutableListOf<VideoBasicModel>()

    private val viewModel by viewModels<SearchViewModel> {
        SharedViewModelFactory(YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext())))
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rootView = view.rootView
        rootView.setBackgroundColor(Color.WHITE)
 //       YoutubeRepository = YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext()))


        binding.btnGoSearch.setOnClickListener {
            val searchKey = binding.etSearchBar.toString()
            val result = viewModel.searchWithKeyWord(searchKey)
           // dataList.addAll(result)
        }

//        binding.btnGoSearch.setOnClickListener {
//            if ((binding.etSearchBar.text).toString().isNotEmpty()) {
//                val searchKey = binding.etSearchBar.text.toString()
//                dataList.clear()
//                goSearch(searchKey)
//
//            }
//
//        }


        adapter = SearchResultAdapter(dataList)
        binding.rvSearchResult.adapter = adapter
        binding.rvSearchResult.layoutManager = GridLayoutManager(context,2)

        adapter.itemOnClick = object : SearchResultAdapter.ItemOnClick{
            override fun click(data: VideoBasicModel) {

                lifecycleScope.launch {
                    Log.d("112233",data.toString())
                    val data = getItem(data.id) ?: return@launch
                    Log.d("1122",data.toString())
                    val action = SearchFragmentDirections.actionFragmentSearchToFragmentVideoDetail(data)
                    if (findNavController().currentDestination?.id == R.id.fragment_search) {
                        findNavController().navigate(action)

                    }
                }

            }


        }



    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun goSearch(searchKey : String) = lifecycleScope.launch {
        val data = YoutubeRepository.getVideos(ModelType.VIDEO_CATEGORY_TRAVEL)
        Log.d("112233",data.value.toString())
        val list = data.value?.filter { it.title?.contains(searchKey) ?: false }
        Log.d("11223344",list.toString())
        if (list != null) {
            dataList.addAll(list)
            Log.d("112255",list.toString())
        }
        adapter.notifyDataSetChanged()
        Log.d("loglog","${dataList}")

    }

//     suspend fun getItem(id : String) : VideoBasicModel? {
//
//       // return YoutubeRepository.getVideoById(id)
//    }





}