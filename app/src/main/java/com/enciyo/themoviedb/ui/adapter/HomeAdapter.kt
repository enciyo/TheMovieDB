package com.enciyo.themoviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.enciyo.themoviedb.databinding.ItemHomeBinding

class HomeAdapter :
    ListAdapter<HomeAdapter.HomeAdapterModel, HomeAdapter.HomeViewHolder>(DIFF) {

    companion object DIFF : DiffUtil.ItemCallback<HomeAdapterModel>() {
        override fun areItemsTheSame(
            oldItem: HomeAdapterModel,
            newItem: HomeAdapterModel
        ): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: HomeAdapterModel,
            newItem: HomeAdapterModel
        ): Boolean =
            oldItem.description == newItem.description
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeViewHolder(
        private val binding: ItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieDTO: HomeAdapterModel) {
            binding.title.text = movieDTO.name
            binding.description.text = movieDTO.description
            binding.img.load(movieDTO.img) {
                crossfade(true)
                transformations(RoundedCornersTransformation(12f))
            }
        }
    }


    data class HomeAdapterModel(
        val img: String,
        val name: String,
        val description: String
    )

}