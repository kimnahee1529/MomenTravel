package com.android.traveltube.ui.datail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.traveltube.R
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.databinding.FragmentVideoDetailBinding
import com.android.traveltube.factory.SharedViewModelFactory
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.ui.datail.recommend.RecommendListAdapter
import com.android.traveltube.ui.datail.channel.ChannelOtherVideoListAdapter
import com.android.traveltube.utils.DateManager.convertToDecimalString
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.DateManager.formatNumber
import com.android.traveltube.utils.UtilManager.loadVideoImage
import com.android.traveltube.viewmodel.SharedViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class VideoDetailFragment : Fragment() {
    private var _binding: FragmentVideoDetailBinding? = null
    private val binding: FragmentVideoDetailBinding get() = _binding!!

    private val args by navArgs<VideoDetailFragmentArgs>()

    private val sharedViewModel by activityViewModels<SharedViewModel> {
        SharedViewModelFactory(YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext())))
    }

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext())),
            args.homeToDetailEntity
        )
    }

    private val channelListAdapter by lazy {
        ChannelOtherVideoListAdapter(
            onItemClick = {

            }
        )
    }

    private val recommendListAdapter by lazy {
        RecommendListAdapter(
            onItemClick = {

            }
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        binding.recommendRecyclerView.adapter = recommendListAdapter
        binding.channelRecyclerView.adapter = channelListAdapter

        binding.ivLike.setOnClickListener {
            viewModel.onClickedLike()
        }

    }

    private fun initViewModel() {
        with(viewModel) {
            uiState.observe(viewLifecycleOwner) { item ->
                item ?: return@observe

                with(binding) {
                    setUpYoutubePlayer(item.videoId)
                    tvVideoTitle.text = item.videoTitle
                    tvVideoDescription.text =
                        "조회수 ${item.viewCount?.formatNumber()} ${item.videoDate?.dateFormatter()}\n${item.videoDescription}"
                    tvChannelTitle.text = item.channelName
                    tvChannelSubscriptionCount.text =
                        "구독자 ${item.subscriptionCount?.convertToDecimalString()}명"
                    item.channelThumbnail?.let { ivChannelThumbnail.loadVideoImage(it) }

                    tvOtherVideosTitle.text = "${item.channelName}의 다른 동영상"

                    ivLike.setImageResource(if (item.isFavorite) R.drawable.ic_like_24 else R.drawable.ic_like_empty_24)
                }
            }

            uiChannelVideoState.observe(viewLifecycleOwner) {
                channelListAdapter.submitList(it)
            }

            loadingState.observe(viewLifecycleOwner) { loadingState ->
                if (loadingState.isLoading) {
                    binding.shimmerFrameLayout.startShimmer()
                } else {
                    binding.shimmerFrameLayout.stopShimmer()
                }

                binding.shimmerFrameLayout.visibility = loadingState.shimmerVisibility
                binding.channelRecyclerView.visibility = loadingState.recyclerViewVisibility
            }
        }

        with(sharedViewModel) {
            searchResults.observe(viewLifecycleOwner) {
                recommendListAdapter.submitList(it)
            }

        }
    }

    private fun setUpYoutubePlayer(videoId: String?) {
        if (videoId == null) {
            return
        }

        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                super.onStateChange(youTubePlayer, state)
                when (state) {
                    PlayerConstants.PlayerState.PLAYING ->
                        viewModel.onVideoPlaying()

                    PlayerConstants.PlayerState.PAUSED -> Unit
                    PlayerConstants.PlayerState.ENDED -> Unit
                    else -> Unit
                }
            }
        })
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}