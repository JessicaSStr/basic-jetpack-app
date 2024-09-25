package com.compose.jetmilk.data

import com.compose.jetmilk.model.MilkDataSource
import com.compose.jetmilk.model.OrderMilk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class Repository {

    private val orderMilks = mutableListOf<OrderMilk>()

    init {
        if (orderMilks.isEmpty()) {
            MilkDataSource.dummyMilks.forEach {
                orderMilks.add(OrderMilk(it, 0))
            }
        }
    }

    fun getAllRewards(): Flow<List<OrderMilk>> {
        return flowOf(orderMilks)
    }

    fun getOrderRewardById(rewardId: Long): OrderMilk {
        return orderMilks.first {
            it.milk.id == rewardId
        }
    }

    fun updateOrderReward(rewardId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderMilks.indexOfFirst { it.milk.id == rewardId }
        val result = if (index >= 0) {
            val orderMilk = orderMilks[index]
            orderMilks[index] =
                orderMilk.copy(milk = orderMilk.milk, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderRewards(): Flow<List<OrderMilk>> {
        return getAllRewards()
            .map { orderMilks ->
                orderMilks.filter { orderMilk ->
                    orderMilk.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}