package com.compose.jetmilk.di

import com.compose.jetmilk.data.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}