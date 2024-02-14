package com.android.traveltube.ui.country

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.R
import com.android.traveltube.databinding.DialogAddFavoritesBinding
import com.android.traveltube.databinding.ItemDetailCityBinding
import com.android.traveltube.databinding.ItemInterestPlusBinding
import com.android.traveltube.utils.UtilityKeyboard.hideKeyboard

class DetailCityAdapter (
    val interest : MutableList<Interest>,
    private val fragment: Fragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var selectCount : Int = 0
    var alertDialog : AlertDialog? = null
    val TYPE_NORMAL = 0
    val TYPE_IMG = 1
    val searchList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NORMAL -> {
                val binding = ItemDetailCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder(binding)
            }
            TYPE_IMG -> {
                val binding1 = ItemInterestPlusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                InterestViewHolder(binding1)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder -> {
                if (interest[position] is Interest.Favorites) {
                    val interestThing = interest[position] as Interest.Favorites
                    holder.bind(interestThing)
                }
            }
            is InterestViewHolder -> {
                if (interest[position] is Interest.plusImage) {
                    val plusImg = interest[position] as Interest.plusImage
                    holder.bindImage(plusImg)
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == interest.size -1) {
            TYPE_IMG
        } else TYPE_NORMAL
    }

    inner class InterestViewHolder(val binding1 : ItemInterestPlusBinding) : RecyclerView.ViewHolder(binding1.root) {
        fun bindImage(img: Interest.plusImage) {
            val plusImage = binding1.ivInterestPlus
            plusImage.setImageResource(img.image)


            itemView.setOnClickListener {
                val context = itemView.context

                val builder = AlertDialog.Builder(context)

                val dialogBinding = DialogAddFavoritesBinding.inflate(LayoutInflater.from(context))

                builder.setView(dialogBinding.root)

                dialogBinding.ivPlusEditFavorite2.setOnClickListener {
                    dialogBinding.ivPlusEditFavorite2.isVisible = false
                    dialogBinding.etPlusFavorite2.isVisible = true

                }

                dialogBinding.btnDialogConfirm.setOnClickListener {
                    val interestAdd1 = dialogBinding.etPlusFavorite1.text.toString()
                    val interestAdd2 = dialogBinding.etPlusFavorite2.text.toString()

                    if (interestAdd1.isNotEmpty()) {
                        interest.add(interest.size-1,Interest.Favorites(interestAdd1))
                    }
                    if (interestAdd2.isNotEmpty()) {
                        interest.add(interest.size-1,Interest.Favorites(interestAdd2))
                    }

                    notifyDataSetChanged()
                    alertDialog?.dismiss()
                    fragment.hideKeyboard()
                }

                dialogBinding.btnDialogCancel.setOnClickListener {
                    alertDialog?.dismiss()
                    fragment.hideKeyboard()
                }

                alertDialog = builder.create()
                alertDialog?.show()
                alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            }
        }

    }

    inner class ViewHolder(val binding : ItemDetailCityBinding) : RecyclerView.ViewHolder(binding.root) {
        private val context: Context = itemView.context
        fun bind(data : Interest.Favorites) {
            val favorite = binding.tvInterest
            favorite.text = data.favorite

            if (data.isSelected) {
                favorite.setBackgroundResource(R.drawable.interest_chip_selected)
                favorite.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
            } else {
                favorite.setBackgroundResource(R.drawable.interest_chip)
                favorite.setTextColor(ContextCompat.getColor(itemView.context,R.color.black))
            }

            itemView.setOnClickListener {
                if (data.isSelected){
                    selectCount--
                    removeFavorite(data.favorite)
                } else {
                    if (selectCount < 2) {
                        selectCount++
                        addFavorite(data.favorite)
                    } else {
                        Toast.makeText(context,"최대 2개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                data.isSelected = !data.isSelected
                Log.d("로그디","${data.favorite}${data.isSelected}")
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return interest.size
    }

    fun addFavorite(favorite: String) {
        if (searchList.size < 2) {
            searchList.add(favorite)
        }
    }

    fun removeFavorite(favorite : String) {
        if (favorite in searchList) {
            searchList.remove(favorite)
        }
    }

}