package com.compose.jetmilk.ui.screen.cart

import com.compose.jetmilk.model.OrderMilk

data class CartState(
    val orderMilk: List<OrderMilk>,
    val totalRequiredPrice: Int
)