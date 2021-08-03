package com.mvvm_withroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mvvm_withroom.databinding.MoviesDetailsBinding
import com.mvvm_withroom.network.MoviesBuilder
import com.mvvm_withroom.network.NetworkHelper
import com.mvvm_withroom.response.MovieDetailsResponse
import com.mvvm_withroom.utils.AppConstants
import com.mvvm_withroom.utils.Status
import com.mvvm_withroom.viewmodel.MoviesViewModel
import com.mvvm_withroom.viewmodel.ViewModelFactory

class MoviesDetailActivity : AppCompatActivity() {
    private lateinit var mBinding : MoviesDetailsBinding
    private lateinit var viewModel: MoviesViewModel
    var movieId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movies_detail)
        setupViewModel()
        setupUi()
        setupObservers()
    }

    private fun setupUi() {
        val bundle = intent.extras
        movieId = bundle!!.getString("movieId")
        mBinding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(NetworkHelper(MoviesBuilder.apiService))
        ).get(MoviesViewModel::class.java)
    }

    private fun setupObservers() {
             viewModel.getMovieDetails(movieId, AppConstants.apiKey, AppConstants.language).observe(this, Observer {
                 it?.let { resource ->
                     when (resource.status) {
                         Status.SUCCESS -> {
                             resource.data?.let { moviesDetailsList -> retrieveDetailsList(moviesDetailsList) }
                         }
                         Status.ERROR -> {
                             Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                         }
                         Status.LOADING -> {

                         }
                     }
                 }
             })
    }

    private fun retrieveDetailsList(movieDetailsResponse: MovieDetailsResponse) {
        Glide.with(mBinding.imagePoster)
            .load("https://image.tmdb.org/t/p/w500"+movieDetailsResponse.posterPath)
            .into(mBinding.imagePoster)
        mBinding.textTitle.text = movieDetailsResponse.title
        mBinding.ratingBar.rating = movieDetailsResponse.voteCount!!.toFloat()
        mBinding.ratingBar.setIsIndicator(true);
        mBinding.textVoteCount.text = movieDetailsResponse.voteCount.toString() +" k reviews"
       // mBinding.textDuration.text = movieDetailsResponse.runtime.toString()
        mBinding.textReleaseDate.text = movieDetailsResponse.releaseDate
        mBinding.textOverview.text = movieDetailsResponse.overview
    }
}