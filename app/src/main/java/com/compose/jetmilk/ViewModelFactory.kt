package com.compose.jetmilk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.compose.jetmilk.data.Repository
import com.compose.jetmilk.ui.screen.cart.CartViewModel
import com.compose.jetmilk.ui.screen.detail.DetailViewModel
import com.compose.jetmilk.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}