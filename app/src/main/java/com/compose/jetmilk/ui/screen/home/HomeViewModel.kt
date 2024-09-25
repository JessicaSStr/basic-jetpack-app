package com.compose.jetmilk.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.jetmilk.data.Repository
import com.compose.jetmilk.model.OrderMilk
import com.compose.jetmilk.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderMilk>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderMilk>>>
        get() = _uiState

    fun getAllRewards() {
        viewModelScope.launch {
            repository.getAllRewards()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderMilks ->
                    _uiState.value = UiState.Success(orderMilks)
                }
        }
    }
}