package com.android.traveltube.ui.search

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.traveltube.data.search.Item
import com.android.traveltube.databinding.FragmentSearchBinding
import com.android.traveltube.network.RetrofitInstance
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : SearchResultAdapter
    private var dataList = mutableListOf<Item>()





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rootView = view.rootView
        rootView.setBackgroundColor(Color.WHITE)



        binding.btnGoSearch.setOnClickListener {
            if ((binding.etSearchBar.text).toString().isNotEmpty()) {
                val searchKey = binding.etSearchBar.text.toString()
                dataList.clear()
                goSearch(searchKey)

            }

        }

        adapter = SearchResultAdapter(dataList)
        binding.rvSearchResult.adapter = adapter
        binding.rvSearchResult.layoutManager = GridLayoutManager(context,2)

        adapter.itemOnClick = object : SearchResultAdapter.ItemOnClick{
            override fun click(data: Item) {

            }

        }



    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun goSearch(searchKey : String) = lifecycleScope.launch {
        val data = RetrofitInstance.api.getSearchingVideos(searchText = searchKey).items
        dataList.addAll(data)
        adapter.notifyDataSetChanged()
        Log.d("loglog","${dataList}")




    }

}