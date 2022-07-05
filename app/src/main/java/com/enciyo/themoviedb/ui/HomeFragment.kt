package com.enciyo.themoviedb.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.enciyo.themoviedb.R
import com.enciyo.themoviedb.databinding.HomeFragmentBinding
import com.enciyo.themoviedb.ext.attach
import com.enciyo.themoviedb.ext.detach
import com.enciyo.themoviedb.ext.linearLayoutManager
import com.enciyo.themoviedb.ext.queryTextChanges
import com.enciyo.themoviedb.ui.adapter.HomeAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private val binding by viewBinding(HomeFragmentBinding::bind, onViewDestroyed = {
        it.recyclerView.detach()
        it.tabs.removeOnTabSelectedListener(tabSelectedLister)
    })
    private val vm by viewModels<HomeViewModel>()

    private val linearLayoutManager by linearLayoutManager()
    private val adapter by lazy { HomeAdapter() }
    private val tabSelectedLister: TabLayout.OnTabSelectedListener by lazy {
        object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vm.search(
                    query = binding.searchView.query.toString(),
                    type = HomeViewModel.MediaType.findByPosition(tab.position)
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab) = Unit
            override fun onTabReselected(tab: TabLayout.Tab) = Unit

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.attach(adapter, linearLayoutManager)
        with(vm) {
            state.observe(viewLifecycleOwner, ::observeState)
            loadingState.observe(viewLifecycleOwner, ::observeLoading)
            infoState.observe(viewLifecycleOwner, ::observeError)
        }

        binding.searchView.queryTextChanges()
            .onEach {
                vm.search(
                    query = binding.searchView.query.toString(),
                    type = HomeViewModel.MediaType.findByPosition(binding.tabs.selectedTabPosition)
                )
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)


        binding.tabs.addOnTabSelectedListener(tabSelectedLister)

    }

    private fun observeError(error: String) {
        binding.infoView.isVisible = error.isNotEmpty()
        binding.infoView.text = error
    }

    private fun observeState(state: HomeViewModel.HomeViewState) {
        adapter.submitList(null)
        adapter.submitList(state.items)
        binding.tabs.isVisible = state.isTabVisible
    }

    private fun observeLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

}