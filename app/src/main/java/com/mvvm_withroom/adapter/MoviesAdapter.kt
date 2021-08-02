package com.mvvm_withroom.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvvm_withroom.MoviesDetailActivity
import com.mvvm_withroom.R
import com.mvvm_withroom.databinding.ItemMoviesBinding
import com.mvvm_withroom.response.SearchMovieResponse

class MoviesAdapter(private val moviesList: SearchMovieResponse) : RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {

    override fun getItemCount(): Int {
        Log.i("MoviesSize", moviesList.results!!.size.toString())
       return moviesList.results!!.size

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MoviesHolder(
            DataBindingUtil.inflate<ItemMoviesBinding>
                (
                LayoutInflater.from(parent.context),
                R.layout.item_movies,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        Glide.with(holder.mBinding.ivMoviePoster.context)
            .load("https://image.tmdb.org/t/p/w500"+moviesList.results?.get(position)?.posterPath)
            .into(holder.mBinding.ivMoviePoster)
        holder.mBinding.tvMovieTitle.text = moviesList.results?.get(position)?.title
        holder.mBinding.tvReleaseDate.text = moviesList.results?.get(position)?.releaseDate
        holder.mBinding.tvMovieRating.text = moviesList.results?.get(position)?.voteAverage.toString()
        holder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context, MoviesDetailActivity::class.java)
            v.context.startActivity(intent)

        }
    }

    inner class MoviesHolder(val mBinding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

    }
}