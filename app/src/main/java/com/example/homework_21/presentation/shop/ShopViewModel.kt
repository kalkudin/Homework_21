package com.example.homework_21.presentation.shop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_21.data.common.Resource
import com.example.homework_21.domain.usecase.GetShopItemsUseCase
import com.example.homework_21.presentation.event.ShopEvent
import com.example.homework_21.presentation.mapper.shopItemToState
import com.example.homework_21.presentation.model.ShopItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(private val getShopItemsUseCase: GetShopItemsUseCase): ViewModel() {

    private val _shopItemState = MutableStateFlow(ShopItemState())
    val shopItemState : StateFlow<ShopItemState> = _shopItemState.asStateFlow()

    fun onEvent(event : ShopEvent) {
        when(event) {
            is ShopEvent.GetShopItems -> getShopItems()
        }
    }

    private fun getShopItems() {
        Log.d("ShopViewModel", "Fetching shop items")
        viewModelScope.launch {
            getShopItemsUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("ShopViewModel", "Successfully retrieved shop items")
                        val items = result.data.map { shopItemToState(it) }
                        _shopItemState.update { ShopItemState(isSuccess = items) }
                    }
                    is Resource.Error -> {
                        Log.e("ShopViewModel", "Error fetching shop items: ${result.errorMessage}")
                        _shopItemState.update { ShopItemState(isError = result.errorMessage) }
                    }
                    is Resource.Loading -> {
                        Log.d("ShopViewModel", "Loading shop items")
                        _shopItemState.update { ShopItemState(isLoading = true) }
                    }
                }
            }
        }
    }
}