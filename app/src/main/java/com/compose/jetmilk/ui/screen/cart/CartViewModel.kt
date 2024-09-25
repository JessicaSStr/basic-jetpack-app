package com.compose.jetmilk.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.jetmilk.data.Repository
import com.compose.jetmilk.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderMilks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderRewards()
                .collect { orderMilk ->
                    val totalRequiredPrice =
                        orderMilk.sumOf { it.milk.requiredPrice * it.count }
                    _uiState.value = UiState.Success(CartState(orderMilk, totalRequiredPrice))
                }
        }
    }

    fun updateOrderReward(milkId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(milkId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderMilks()
                    }
                }
        }
    }
}