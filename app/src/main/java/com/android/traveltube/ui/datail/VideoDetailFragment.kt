package com.android.traveltube.ui.datail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.android.traveltube.databinding.FragmentVideoDetailBinding


class VideoDetailFragment : Fragment() {
    private var _binding: FragmentVideoDetailBinding? = null
    private val binding: FragmentVideoDetailBinding get() = _binding!!

    // navArgs 선언
    private val args by navArgs<VideoDetailFragmentArgs>()

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
    }

    private fun initView() {
        // HomeFragment 로부터 전달 받은 데이터
//        val id = args.id
//        Log.d("TAG", "HomeFragment: $id")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}