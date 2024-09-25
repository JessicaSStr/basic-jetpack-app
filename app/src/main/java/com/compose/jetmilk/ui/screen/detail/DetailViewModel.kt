package com.compose.jetmilk.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.jetmilk.data.Repository
import com.compose.jetmilk.model.Milk
import com.compose.jetmilk.model.OrderMilk
import com.compose.jetmilk.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderMilk>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderMilk>>
    get() = _uiState

    fun getRewardById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderRewardById(rewardId))
        }
    }

    fun addToCart(reward: Milk, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(reward.id, count)
        }
    }
}
