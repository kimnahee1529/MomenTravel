package com.android.traveltube.ui.datail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.traveltube.databinding.FragmentVideoDetailBinding
import com.android.traveltube.ui.datail.channel.ChannelListAdapter
import com.android.traveltube.ui.datail.recommend.ReCommendListAdapter
import com.android.traveltube.utils.DateManager.dateFormatter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class VideoDetailFragment : Fragment() {
    private var _binding: FragmentVideoDetailBinding? = null
    private val binding: FragmentVideoDetailBinding get() = _binding!!

    // navArgs 선언
    private val args by navArgs<VideoDetailFragmentArgs>()

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(args.homeToDetailEntity)
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
    ): View? {
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
    }

    private fun initViewModel() = with(viewModel) {
        uiState.observe(viewLifecycleOwner) { item ->
            if (item == null) {
                return@observe
            }

            setUpYoutubePlayer(item.videoId)
            binding.tvVideoTitle.text = item.videoTitle
            binding.tvVideoDescription.text =
                item.videoDate?.dateFormatter() + "\n" + item.videoDescription
            binding.tvChannelTitle.text = item.channelName
        }
    }

    private fun setUpYoutubePlayer(videoId: String?) {
        if (videoId == null) {
            // TODO 썸네일 표시
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