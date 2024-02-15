package com.android.traveltube.ui.country

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.traveltube.R
import com.android.traveltube.databinding.FragmentUserNameBinding
import com.android.traveltube.utils.Constants.NAME_KEY
import com.android.traveltube.utils.Constants.PREFERENCE_NAME


class UserNameFragment : Fragment() {
    private var _binding : FragmentUserNameBinding? = null
    private lateinit var sharedPref: SharedPreferences
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
        sharedPref = requireActivity().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        nextBtn.setOnClickListener {
            val nickName = binding.etNickName.text.toString()
            if (nickName.isNotEmpty()) {
                saveNickname(nickName)
                Log.d("로그디","닉네임 입력${nickName}")
//                val name = sharedPref.getString(NAME_KEY,"")
//
//
//                Log.d("로그디","${name}")
                findNavController().navigate(R.id.action_userNameFragment_to_fragment_country)
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
        sharedPref.edit().apply {
            putString(NAME_KEY, nickName)
            apply()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

