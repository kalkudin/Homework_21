package com.example.homework_21.presentation.shop

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework_21.databinding.FragmentShopLayoutBinding
import com.example.homework_21.presentation.adapter.ShopItemRecyclerAdapter
import com.example.homework_21.presentation.base.BaseFragment
import com.example.homework_21.presentation.event.ShopEvent
import com.example.homework_21.presentation.model.ShopItemState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopFragment : BaseFragment<FragmentShopLayoutBinding>(FragmentShopLayoutBinding::inflate) {

    private val shopViewModel : ShopViewModel by viewModels()
    private val shopAdapter: ShopItemRecyclerAdapter by lazy { ShopItemRecyclerAdapter() }

    override fun bind() {
        bindItemRecyclerView()
        getShopItems()
    }

    override fun bindViewActionListeners() {

    }

    override fun bindObservers() {
        bindShopFlow()
    }

    private fun bindItemRecyclerView() {
        binding.shopItemRecyclerView.apply {
            adapter = shopAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun getShopItems() {
        shopViewModel.onEvent(ShopEvent.GetShopItems)
    }

    private fun bindShopFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                shopViewModel.shopItemState.collect { it
                    handleShopState(state = it)
                }
            }
        }
    }

    private fun handleShopState(state : ShopItemState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.isSuccess?.let { list ->
            shopAdapter.submitList(list)
        }

        state.isError?.let { errorMessage ->
            showErrorMessage(errorMessage)
        }
    }

    private fun showErrorMessage(message : String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}