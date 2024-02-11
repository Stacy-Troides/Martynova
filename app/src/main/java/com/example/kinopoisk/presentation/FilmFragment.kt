package com.example.kinopoisk.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kinopoisk.R
import com.example.kinopoisk.data.model.CountryX
import com.example.kinopoisk.data.model.GenreX
import com.example.kinopoisk.databinding.FragmentFilmBinding
import kotlinx.coroutines.launch

class FilmFragment : Fragment(R.layout.fragment_film) {

    companion object {
        private const val ARG_ID: String = "ARG_ID"

        fun getInstance(id: Int): Fragment {
            return FilmFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
        }
    }

    private lateinit var binding: FragmentFilmBinding
    private val viewModel: FilmViewModel by viewModels()
    private val filmId: Int by lazy { arguments?.getInt(ARG_ID) ?: error("Missing id") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFilmBinding.inflate(inflater, container, false)
        binding.appBarInfo.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initDetail(filmId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventState.collect {
                with(binding) {
                    genreMovie.text = it.genres.map(GenreX::genre).joinToString()
                    name.text = it.nameRu
                    descriptionMovie.text = it.description
                    countriesMovie.text = it.countries.map(CountryX::country).joinToString()

                    Glide.with(this@FilmFragment)
                        .load(it.posterUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(poster)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.load.collect {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }
}