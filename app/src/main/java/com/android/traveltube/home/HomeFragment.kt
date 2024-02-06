package com.android.traveltube.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.traveltube.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.btSendData.setOnClickListener {
            // 동영상 클릭 했을 때 상세 화면 이동
            val action = HomeFragmentDirections.actionFragmentHomeToFragmentVideoDetail("youtube_id")
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}