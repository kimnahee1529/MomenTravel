package com.android.traveltube.ui.country

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.traveltube.R
import com.android.traveltube.databinding.FragmentCountryBinding
import com.android.traveltube.ui.country.Country.Companion.countryList
import com.android.traveltube.utils.Constants.PREFERENCE_NAME_COUNTRY


class CountryFragment : Fragment() {
    private  val COUNTRY_KEY = "country"
    private var _binding : FragmentCountryBinding? = null
    private lateinit var sharedPref: SharedPreferences
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

        val autoCompleteAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, canTravelList)
        binding.etCountrySearch.setAdapter(autoCompleteAdapter)

        adapter = CountryAdapter(countryList)

        val recyclerView = binding.rvChooseCountry
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.scheduleLayoutAnimation()

        var travelName = ""

        adapter.itemclick = object : CountryAdapter.ItemClick {
            override fun itemClick(name: String) {
               travelName = name
            }
        }
        val cancelImage = binding.ivCancel

        binding.etCountrySearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString().isEmpty()) {
                    cancelImage.isVisible = false
                    binding.tvNoSearchResult.isVisible = false
                }
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cancelImage.isVisible = true
            }
            override fun afterTextChanged(s: Editable?) {
                val str = s.toString()
                val filteredList = if (str.isNullOrBlank()) countryList else getFilteredList(str)
                if (canTravelList.contains(str)) {
                    binding.tvNoSearchResult.isVisible = false
                    val position = canTravelList.indexOf(str)

                    when(position) {
                        in 0 ..4 -> filteredList
                        in 5..31 -> filteredList.add(0, countryList[2])

                        in 32..72 ->filteredList.add(0,countryList[0])
                        in 73..115 -> filteredList.add(0,countryList[1])
                        else ->filteredList.add(0,countryList[3])
                    }
                } else binding.tvNoSearchResult.isVisible = true
                adapter.updateData(filteredList)

//                adapter.updateData(
//                    if (str.isNullOrBlank()) countryList
//                    else getFilteredList(str)
//                )

            }

        })

        cancelImage.setOnClickListener {
            binding.etCountrySearch.text = null
            cancelImage.isVisible = false
            recyclerView.scheduleLayoutAnimation()
        }

        binding.btnNext.setOnClickListener {
            val wantNation = travelName

            if (wantNation.isNotEmpty()) {
                saveCountry(wantNation)


                sharedPref = requireContext().getSharedPreferences(PREFERENCE_NAME_COUNTRY, Context.MODE_PRIVATE)
                val country = sharedPref.getString(COUNTRY_KEY,"")
                Log.d("컨트리 프레그먼트","11233 ${country}")
                findNavController().navigate(R.id.action_countryFragment_to_fragment_detail_city)
            } else Toast.makeText(requireContext(),"여행지를 선택해 주세요", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    fun saveCountry (country : String) {
        val spf = requireActivity().getSharedPreferences(PREFERENCE_NAME_COUNTRY, Context.MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString(COUNTRY_KEY, country)
        Log.d("컨트리 프레그먼트",country)
        editor.apply()
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