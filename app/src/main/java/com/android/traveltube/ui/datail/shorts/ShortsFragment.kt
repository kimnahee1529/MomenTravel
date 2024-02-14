package com.android.traveltube.ui.datail.shorts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.traveltube.databinding.FragmentShortsBinding
import com.android.traveltube.ui.datail.VideoDetailFragmentArgs
import com.android.traveltube.utils.UtilManager.loadVideoImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class ShortsFragment : Fragment() {
    private var _binding: FragmentShortsBinding? = null
    private val binding: FragmentShortsBinding get() = _binding!!
    private val args by navArgs<VideoDetailFragmentArgs>()

    private val viewModel: DetailShortsModel by viewModels {
        DetailShortsViewModelFactory(
            args.homeToDetailEntity
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShortsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }
    private fun initViewModel(){
        with(viewModel) {
            uiState.observe(viewLifecycleOwner) { item ->
                item ?: return@observe
                with(binding) {
                    setUpYoutubePlayer(item.videoId)
                    tvShortsTitle.text = item.videoTitle
                    tvChannelTitle.text = item.channelName
                    item.channelThumbnail?.let { ivChannelThumbnail.loadVideoImage(it) }
                }
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
                    PlayerConstants.PlayerState.PAUSED -> Unit

                    PlayerConstants.PlayerState.ENDED -> Unit

                    else -> Unit
                }
            }
        })
    }
}