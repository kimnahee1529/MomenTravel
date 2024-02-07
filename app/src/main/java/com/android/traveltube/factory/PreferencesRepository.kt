package com.android.traveltube.factory

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesRepository(private val sharedPreferences: SharedPreferences) {
    private val favoriteKey = "saveFavoritesData"

//    fun loadFavoritesDataList(): List<SearchItemModel> {
//        val json = sharedPreferences.getString(favoriteKey, null)
//        return if (json != null) {
//            val type = object : TypeToken<List<SearchItemModel>>() {}.type
//            Gson().fromJson(json, type)
//        } else {
//            emptyList()
//        }
//    }
//
//    fun toggleFavorite(document: SearchItemModel) {
//        val currentFavorites = getFavoritesDataList() // 현재 저장된 즐겨찾기 리스트를 가져옴
//        val editor = sharedPreferences.edit()
//        val gson = Gson()
//
//        var foundDocument: SearchItemModel? = null
//
//        for (favDoc in currentFavorites) {
//            if (favDoc.url == document.url) {
//                foundDocument = favDoc
//                break
//            }
//        }
//
//        if (foundDocument != null) {
//            currentFavorites.remove(foundDocument)
//        } else {
//            currentFavorites.add(document)
//        }
//
//        val json = gson.toJson(currentFavorites)
//        editor.putString(favoriteKey, json)
//        editor.apply()
//    }
//
//    fun getFavoritesDataList(): MutableList<SearchItemModel> {
//        val json = sharedPreferences.getString(favoriteKey, null)
//        val type = object : TypeToken<List<SearchItemModel>>() {}.type
//        return Gson().fromJson(json, type) ?: mutableListOf()
//    }


}