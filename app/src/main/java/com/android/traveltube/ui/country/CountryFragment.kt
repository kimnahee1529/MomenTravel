package com.android.traveltube.ui.country

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.traveltube.R
import com.android.traveltube.databinding.FragmentCountryBinding
import com.android.traveltube.ui.country.Country.Companion.countryList


class CountryFragment : Fragment() {

    private var _binding : FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rootView = view.rootView
        rootView.setBackgroundColor(Color.WHITE)

        val countryList = Country.countryList




        adapter = CountryAdapter(countryList)

        val recyclerView = binding.rvChooseCountry
        val reduceSpace = controlSpace(0,-100,0,0)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.startLayoutAnimation()
        recyclerView.addItemDecoration(reduceSpace)




        adapter.itemclick = object : CountryAdapter.ItemClick {
            override fun itemClick(name: String) {
                //          binding.etCountrySearch.setText(name)
            }
        }
        val cancelImage = binding.ivCancel

        binding.etCountrySearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString().isEmpty()) {
                    cancelImage.isVisible = false
                }
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cancelImage.isVisible = true
            }
            override fun afterTextChanged(s: Editable?) {

                adapter.updateData(
                    if (s.isNullOrBlank()) {
                        countryList
                    }
                    else {
                        getFilteredList(s.toString())}
                )

            }
        })


        cancelImage.setOnClickListener {
            binding.etCountrySearch.text = null
            cancelImage.isVisible = false
            countryList.forEach { it.isSelected = false }
            adapter.notifyDataSetChanged()
        }

        binding.btnNext.setOnClickListener {
            val wantNation = (binding.etCountrySearch.text).toString()

            if (wantNation.isNotEmpty()) {
                moveNextFragment(DetailCityFragment())
//                saveCountry(wantNation)
            } else Toast.makeText(requireContext(),"여행지를 선택해 주세요", Toast.LENGTH_SHORT).show()
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    fun saveCountry (country : String) {
        val spf = requireActivity().getSharedPreferences("country", Context.MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("country", country)
        editor.apply()
    }

    fun moveNextFragment(nextFrag : Fragment) {
        val manager = requireActivity().supportFragmentManager
        val transaction = manager.beginTransaction()

        transaction.replace(R.id.frame_layout,nextFrag)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getFilteredList(s: String): MutableList<Country> {
        val filteredList = mutableListOf<Country>()
        return filteredList.apply {
            countryList.forEach {
                if (it.countryName.contains(s)) add(it)
            }
        }
    }



}