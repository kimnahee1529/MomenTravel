package com.android.traveltube.ui.datail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.traveltube.R
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.databinding.FragmentVideoDetailBinding
import com.android.traveltube.repository.YoutubeRepository
import com.android.traveltube.ui.datail.channel.ChannelListAdapter
import com.android.traveltube.ui.datail.recommend.ReCommendListAdapter
import com.android.traveltube.utils.DateManager.convertToDecimalString
import com.android.traveltube.utils.DateManager.dateFormatter
import com.android.traveltube.utils.UtilManager.loadImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class VideoDetailFragment : Fragment() {
    private var _binding: FragmentVideoDetailBinding? = null
    private val binding: FragmentVideoDetailBinding get() = _binding!!

    private val args by navArgs<VideoDetailFragmentArgs>()

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            YoutubeRepository(VideoSearchDatabase.getInstance(requireContext())),
            args.homeToDetailEntity
        )
    }

    private val channelListAdapter by lazy {
        ChannelListAdapter(
            onItemClick = {
            }
        )
    }

    private val recommendListAdapter by lazy {
        ReCommendListAdapter(
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
        // Adapter 연결
        binding.recommendRecyclerView.adapter = recommendListAdapter
        binding.channelVideoRecyclerView.adapter = channelListAdapter

        binding.ivLike.setOnClickListener {
            // TODO 좋아요 버튼 클릭
            viewModel.onClickedLike()
        }

        viewModel.getRecommendVideos()
    }

    private fun initViewModel() = with(viewModel) {
        uiState.observe(viewLifecycleOwner) { item ->
            item ?: return@observe

            with(binding) {
                setUpYoutubePlayer(item.videoId)
                tvVideoTitle.text = item.videoTitle
                tvVideoDescription.text = "${item.videoDate?.dateFormatter()}\n${item.videoDescription}"
                tvChannelTitle.text = item.channelName
                tvChannelSubscriptionCount.text = "구독자 ${item.subscriptionCount?.convertToDecimalString()}명"
                item.channelThumbnail?.let { ivChannelThumbnail.loadImage(it) }

                ivLike.setImageResource(if (item.isFavorite) R.drawable.ic_like_24 else R.drawable.ic_like_empty_24)
                tvChannelOtherVideoTitle.text = "${item.channelName}의 다른 동영상"
            }
        }

        uiRecommendState.observe(viewLifecycleOwner) {
            recommendListAdapter.submitList(it)
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
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}