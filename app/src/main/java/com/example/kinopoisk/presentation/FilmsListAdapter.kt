package com.example.kinopoisk.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kinopoisk.data.model.Film
import com.example.kinopoisk.databinding.ItemFilmsListBinding

class FilmsListAdapter : RecyclerView.Adapter<FilmsListAdapter.ViewHolder>() {
    private var films = mutableListOf<Film>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmsListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(films[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(films[position]) }
        }
    }

    override fun getItemCount(): Int = films.size

    inner class ViewHolder(private val binding: ItemFilmsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            with(binding) {
                year.text = film.year
                name.text = film.nameRu

                Glide.with(itemView)
                    .load(film.posterUrlPreview)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(poster)
            }
        }
    }

    fun setFilmsList(filmList: List<Film>) {
        films = filmList.toMutableList()
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((Film) -> Unit)? = null

    fun setOnClickListener(listener: (Film) -> Unit) {
        onItemClickListener = listener
    }
}