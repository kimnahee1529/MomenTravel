package com.android.traveltube.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.traveltube.R
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.databinding.FragmentMyFavoriteBinding
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class MyFavoriteFragment : Fragment() {

    private var _binding: FragmentMyFavoriteBinding? = null
    private val binding: FragmentMyFavoriteBinding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(
            YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext()))
        )
    }

    private lateinit var favoriteListAdapter: FavoriteListAdapter

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
    }

    private fun setSortingPopup() = with(binding) {
        tvPopupSort.setOnClickListener {
            PopupMenu(requireContext(), it).apply {
                menuInflater.inflate(R.menu.menu_popup_sort, this.menu)

                setOnMenuItemClickListener { item ->
                    val position = when (item.itemId) {
                        R.id.sort_basic -> 0
                        R.id.sort_title -> 1
                        R.id.sort_date -> 2
                        else -> 0
                    }
                    val sortingOption = SortingOption(position, item.title.toString())
                    viewModel.setSelectedSortingOption(sortingOption)

                    false
                }
            }.show()
        }

    }

    private fun initView() {
        favoriteListAdapter = FavoriteListAdapter(
            onItemClick = { item, viewType ->
                when (viewType) {
                    ViewType.ITEM_VIEW_TYPE_NORMAL.ordinal -> {
                        val action =
                            MyFavoriteFragmentDirections.actionFragmentMyFavoriteToFragmentVideoDetail(
                                item
                            )
                        lifecycleScope.launch {
                            if (findNavController().currentDestination?.id == R.id.fragment_my_favorite) {
                                findNavController().navigate(action)
                            }
                        }
                    }

                    ViewType.ITEM_VIEW_TYPE_EDIT.ordinal -> {
                        viewModel.deleteFavoriteItem(item.id)
                        showSnackBar(R.string.favorite_delete_message)

                    }

                    else -> Unit
                }
            },
            isEditMode = viewModel.isEditMode.value?.isEditMode ?: false
        )

        binding.favoriteRecyclerView.adapter = favoriteListAdapter
        setSortingPopup()

        binding.btFavoriteEdit.setOnClickListener {
            viewModel.toggleEditMode()
        }
    }


    private fun initViewModel() = with(viewModel) {
        favoriteVideos.observe(viewLifecycleOwner) {
            favoriteListAdapter.submitList(it)
        }

        isEditMode.observe(viewLifecycleOwner) { editModeUiState ->
            favoriteListAdapter.isEditMode = editModeUiState.isEditMode
            binding.btFavoriteEdit.text = getString(editModeUiState.buttonText)
        }

        selectedSortingOption.observe(viewLifecycleOwner) {
            binding.tvPopupSort.text = it.title
            viewModel.sortingFavoriteVideos(it.position)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showSnackBar(message: Int) {
        Snackbar.make(
            binding.favoriteFrameLayout,
            getString(message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

}