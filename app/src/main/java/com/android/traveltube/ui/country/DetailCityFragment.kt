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
import com.android.traveltube.R
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.databinding.FragmentDetailCityBinding
import com.android.traveltube.factory.SharedViewModelFactory
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.utils.Constants.COUNTRY_KEY
import com.android.traveltube.utils.Constants.FAVORITES_KEY
import com.android.traveltube.utils.Constants.PREFERENCE_NAME_COUNTRY
import com.android.traveltube.viewmodel.SharedViewModel
import kotlinx.coroutines.launch


class DetailCityFragment : Fragment() {
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

        adapter = DetailCityAdapter(favoriteList, this)

        val recyclerView = binding.rvInterest
//        val increaseSpace = controlSpace(0, 75, 0, 0)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)
//        recyclerView.addItemDecoration(increaseSpace)
    }

    private fun initView() {
        binding.btnMoveHomeFragment.setOnClickListener {
            saveFavorite()

            val country = sharedPref.getString(COUNTRY_KEY, "")
            val favorites = sharedPref.getString(FAVORITES_KEY, "")

            val searchKey = "$country, $favorites"
            Log.d("searchKey", "$searchKey")


            viewModel.getSearchVideoList(searchKey) // 동영상 검색
            viewModel.getTravelVideoList()
            viewModel.getShortsVideoList()
            showLoadingActivity()
        }
        binding.btnSkip.setOnClickListener {
            sharedPref = requireActivity().getSharedPreferences(PREFERENCE_NAME_COUNTRY, Context.MODE_PRIVATE)
            val country = sharedPref.getString(COUNTRY_KEY, "")
            viewModel.getSearchVideoList(country!!)
            viewModel.getTravelVideoList()
            viewModel.getShortsVideoList()
            showLoadingActivity()
        }
    }

    private fun initViewModel() {
        viewModel.bothSearchesSuccessful.observe(viewLifecycleOwner) { success ->
            if (success) {
                val action = DetailCityFragmentDirections.actionFragmentDetailCityToFragmentHome()

                viewLifecycleOwner.lifecycleScope.launch {
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
        val favorites = "$favorite1 $favorite2"
        sharedPref = requireActivity().getSharedPreferences(PREFERENCE_NAME_COUNTRY, Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putString(FAVORITES_KEY, favorites)
            apply()
        }
    }
}