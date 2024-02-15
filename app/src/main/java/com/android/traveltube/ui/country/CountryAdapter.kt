package com.android.traveltube.ui.country

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.R
import com.android.traveltube.databinding.ItemCountryRecyclerviewBinding
import java.util.Locale

class CountryAdapter(var items : MutableList<Country>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>(),
    Filterable {

    //    private var selectedItem : Int = RecyclerView.NO_POSITION
    private var filteredList: MutableList<Country> = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCountryRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    interface ItemClick {
        fun itemClick(name: String)
    }

    var itemclick: ItemClick? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nation = items[position]
        holder.bind(nation)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredList = if (charString.isEmpty()) {
                    items
                } else {
                    val tempList = mutableListOf<Country>()
                    for (item in items) {
                        if (item.countryName.toLowerCase(Locale.getDefault())
                                .contains(charString)
                        ) {
                            tempList.add(item)
                        }
                    }
                    tempList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<Country>
                notifyDataSetChanged()
            }

        }
    }

    fun updateData(newItems: MutableList<Country>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: ItemCountryRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            val image = binding.ivTravel
            val name = binding.tvNation
            val bottomName = binding.tvBottomName

            image.setImageResource(country.flag)
            name.text = country.countryName
            bottomName.text = country.countryName

            if (country.isSelected) {
                image.clearColorFilter()
                name.isVisible = false
                bottomName.isVisible = true
            } else {
               // image.setColorFilter(R.color.)
                name.isVisible = true
                bottomName.isVisible = false
            }

            itemView.setOnClickListener {
                country.isSelected = !country.isSelected
                itemclick?.itemClick(country.countryName)
                Log.d("로그디", "선택됨 : ${country.isSelected} , 나라이름 : ${country.countryName}")
                items.forEachIndexed { index, item ->
                    if (index != adapterPosition && item.isSelected) {
                        item.isSelected = false
                    }
                }
                notifyDataSetChanged()
            }
        }
    }
}
