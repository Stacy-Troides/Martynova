package com.example.kinopoisk.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.FragmentFilmsListBinding

class FilmsListFragment : Fragment(R.layout.fragment_films_list) {
    private lateinit var binding: FragmentFilmsListBinding
    private val viewModel: FilmsListViewModel by viewModels()
    private lateinit var listAdapter: FilmsListAdapter
    private lateinit var swipe: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmsListBinding.inflate(inflater, container, false)
        swipe = binding.swipeRefresh
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()

        swipe.setOnRefreshListener {
            viewModel.onRefreshList()
            swipe.isRefreshing = false
        }

        binding.buttonGlass.setOnClickListener {
            viewModel.onSearchClicked(binding.search.text.toString())
        }
    }

    private fun initObservers() {

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.pb.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            binding.emptyView.visibility = if (it) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (it) View.GONE else View.VISIBLE
        }

        viewModel.listFilms.observe(viewLifecycleOwner) {
            listAdapter.setFilmsList(it)
            listAdapter.setOnClickListener { film ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FilmFragment.getInstance(film.filmId))
                    .addToBackStack(FilmsListFragment().tag)
                    .commit()
            }
        }
    }

    private fun initRecyclerView() {
        listAdapter = FilmsListAdapter()
        binding.recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
