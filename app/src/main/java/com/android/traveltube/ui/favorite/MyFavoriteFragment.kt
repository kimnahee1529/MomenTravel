package com.android.traveltube.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.databinding.FragmentMyFavoriteBinding
import com.android.traveltube.factory.SharedViewModelFactory
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.viewmodel.SharedViewModel


class MyFavoriteFragment : Fragment() {

    private var _binding: FragmentMyFavoriteBinding? = null
    private val binding: FragmentMyFavoriteBinding get() = _binding!!
    private val sharedViewModel by activityViewModels<SharedViewModel> {
        SharedViewModelFactory(YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext())))
    }

    private lateinit var spinner: Spinner
    private lateinit var rotateAnimation: View.OnClickListener

    private val favoriteListAdapter by lazy {
        FavoriteListAdapter(
            onItemClick = {

            }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        setUpSpinner()
    }

    private fun setUpSpinner() {

    }

    private fun initView() {
        binding.favoriteRecyclerView.adapter = favoriteListAdapter
    }

    private fun initViewModel() {
        sharedViewModel.favoriteVideos.observe(viewLifecycleOwner) {
            favoriteListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}