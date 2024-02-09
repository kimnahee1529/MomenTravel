package com.android.traveltube.ui.country

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.android.traveltube.databinding.FragmentLoadingBinding

class LoadingDialogFragment : DialogFragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    private val dots by lazy {
        listOf(binding.dot1, binding.dot2, binding.dot3)
    }

    private val handler = Handler(Looper.getMainLooper())
    private var dotIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWindowAttributes()
        startLoadingAnimation()
    }

    private fun setUpWindowAttributes() {
        val params = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params
        dialog?.window?.setBackgroundDrawableResource(android.R.color.white)
    }

    private fun startLoadingAnimation() {
        handler.post(object : Runnable {
            override fun run() {
                animateDot(dots[dotIndex])
                dotIndex = (dotIndex + 1) % dots.size
                handler.postDelayed(this, 500) // 500ms 간격으로 변경
            }
        })
    }

    private fun animateDot(dot: ImageView) {
        val scaleX = ObjectAnimator.ofFloat(dot, "scaleX", 1.0f, 1.5f, 1.0f)
        val scaleY = ObjectAnimator.ofFloat(dot, "scaleY", 1.0f, 1.5f, 1.0f)

        scaleX.interpolator = AccelerateDecelerateInterpolator()
        scaleY.interpolator = AccelerateDecelerateInterpolator()

        scaleX.duration = 500 // 애니메이션 지속 시간 (ms)
        scaleY.duration = 500 // 애니메이션 지속 시간 (ms)

        scaleX.start()
        scaleY.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
