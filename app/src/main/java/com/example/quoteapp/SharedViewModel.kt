package com.example.quoteapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _favoriteQuote = MutableLiveData<String>()
    val favoriteQuote: LiveData<String> get() = _favoriteQuote

    fun setFavoriteQuote(newFavoriteQuote: String) {
        _favoriteQuote.postValue(newFavoriteQuote)
    }
}