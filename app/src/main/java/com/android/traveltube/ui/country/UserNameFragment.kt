package com.android.traveltube.ui.country

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.traveltube.R
import com.android.traveltube.databinding.FragmentUserNameBinding


class UserNameFragment : Fragment() {
    private var _binding : FragmentUserNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserNameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rootView = view.rootView
        rootView.setBackgroundColor(Color.WHITE)

        val nextBtn = binding.btnNext

        nextBtn.setOnClickListener {
            val nickName = binding.etNickName.text.toString()
            if (nickName.isNotEmpty()) {
//                saveNickname(nickName)
                moveNextFragment(CountryFragment())

            } else Toast.makeText(requireContext(), "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }
    fun moveNextFragment(nextFrag : Fragment) {
        val manager = requireActivity().supportFragmentManager
        val transaction = manager.beginTransaction()

        transaction.replace(R.id.fragment_detail_city,nextFrag)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //sharedReferece에 이름 저장
    fun saveNickname (nickName : String) {
        val spf = requireActivity().getSharedPreferences("name", Context.MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("name", nickName)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

