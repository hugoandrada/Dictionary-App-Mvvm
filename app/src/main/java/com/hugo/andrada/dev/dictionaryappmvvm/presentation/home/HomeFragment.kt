package com.hugo.andrada.dev.dictionaryappmvvm.presentation.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hugo.andrada.dev.dictionaryappmvvm.R
import com.hugo.andrada.dev.dictionaryappmvvm.databinding.FragmentHomeBinding
import com.hugo.andrada.dev.dictionaryappmvvm.presentation.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        homeAdapter = HomeAdapter()
        setupAdapter()
        setupSearchView()
        setupData()
        setupEvents()
    }

    private fun setupAdapter() {
        binding.recyclerView.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setupData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.searchQuery.collect { query ->
                        viewModel.onSearch(query)
                    }
                }
                launch {
                    viewModel.state.collect { state ->
                        homeAdapter.submitList(state.words)
                        binding.progressBar.isVisible = state.isLoading
                    }
                }
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.queryHint = "search for words....."
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.onSearch(query.lowercase().trim())
                    binding.recyclerView.scrollToPosition(0)
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { event ->
                when(event) {
                    is HomeViewModel.UiEvent.ShowSnackBar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}