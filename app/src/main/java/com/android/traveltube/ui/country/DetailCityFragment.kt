package com.android.traveltube.ui.country

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
import com.android.traveltube.repository.YoutubeRepository
import com.android.traveltube.viewmodel.SharedViewModel
import kotlinx.coroutines.launch


class DetailCityFragment : Fragment() {

    private var _binding: FragmentDetailCityBinding? = null
    private val binding: FragmentDetailCityBinding get() = _binding!!
    private lateinit var adapter : DetailCityAdapter

    private val sharedViewModel by activityViewModels<SharedViewModel> {
        SharedViewModelFactory(YoutubeRepository(VideoSearchDatabase.getInstance(requireContext())))
    }

    private val viewModel by viewModels<DetailCityViewModel> {
        DetailCityViewModelProviderFactory(
            YoutubeRepository(
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
        val increaseSpace = controlSpace(0,75,0,0)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context,3)
        recyclerView.addItemDecoration(increaseSpace)

    }

    private fun initView() {
        binding.btMoveHomeFragment.setOnClickListener {
            /**
             * TODO 선택된 여행지, 관심사 태그를 통하여 api로 동영상 검색
             * 검색 된 결과를 Room 저장
             * 저장 된 항목은 HomeFragment 에서 사용할 수 있어야 함.
             */
            viewModel.getSearchVideoList() // 동영상 검색
            viewModel.getTravelVideoList()
            showLoadingActivity()
        }


    }

    private fun initViewModel() {
        viewModel.searchResults.observe(viewLifecycleOwner) {
            sharedViewModel.getResultsVideoList(it)

            // TODO 로딩 화면 필요함
            if (isAdded && findNavController().currentDestination != null) {
                val action = DetailCityFragmentDirections.actionFragmentDetailCityToFragmentHome()

                viewLifecycleOwner.lifecycleScope.launch {
                    findNavController().navigate(action)

                    closeLoadingActivity()

                }
            }
        }
        viewModel.searchTravelResults.observe(viewLifecycleOwner){
            sharedViewModel.getResultsTravelLVideoList(it)
            Log.d("여행 카테고리 동영상들", it.toString())
        }
    }

    private var loadingDialog: LoadingDialogFragment? = null

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

}