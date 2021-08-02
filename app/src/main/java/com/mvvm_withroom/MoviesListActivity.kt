 package com.mvvm_withroom

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm_withroom.adapter.MoviesAdapter
import com.mvvm_withroom.databinding.ActivityMainBinding
import com.mvvm_withroom.network.MoviesBuilder
import com.mvvm_withroom.network.NetworkHelper
import com.mvvm_withroom.response.MovieDetailsResponse
import com.mvvm_withroom.response.SearchMovieResponse
import com.mvvm_withroom.utils.Status
import com.mvvm_withroom.viewmodel.MoviesViewModel
import com.mvvm_withroom.viewmodel.ViewModelFactory

 class MoviesListActivity : AppCompatActivity() {
     private lateinit var viewModel: MoviesViewModel
     private lateinit var adapter: MoviesAdapter
     private lateinit var mBinding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupViewModel()
        setupUI()
        setupObservers()

    }

     private fun setupUI() {

     }

     private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(NetworkHelper(MoviesBuilder.apiService))
        ).get(MoviesViewModel::class.java)
    }
     
     private fun setupObservers() {
         viewModel.getMoviesList().observe(this, Observer {
             it?.let { resource ->
                 when (resource.status) {
                     Status.SUCCESS -> {
                         resource.data?.let { moviesList -> retrieveList(moviesList) }
                     }
                     Status.ERROR -> {
                         Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                     }
                     Status.LOADING -> {
                         Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()
                     }
                 }
             }
         })
    /*     viewModel.getMovieDetails().observe(this, Observer {
             it?.let { resource ->
                 when (resource.status) {
                     Status.SUCCESS -> {
                         resource.data?.let { moviesDetailsList -> retrieveDetailsList(moviesDetailsList) }
                     }
                     Status.ERROR -> {
                         Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                     }
                     Status.LOADING -> {
                         Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()
                     }
                 }
             }
         })*/
     }

     private fun retrieveList(searchMovieResponse: SearchMovieResponse) {

         mBinding.rvMovies.also {
             it.layoutManager = GridLayoutManager(this, 1)
             it.setHasFixedSize(true)
             it.adapter =
                 MoviesAdapter(
                     searchMovieResponse,
                 )
             mBinding.rvMovies.addItemDecoration(
                 DividerItemDecoration(
                     mBinding.rvMovies.context,
                     (mBinding.rvMovies.layoutManager as GridLayoutManager).orientation
                 )
             )


         }
     }

     private fun retrieveDetailsList(searchMovieResponse: MovieDetailsResponse) {
        Log.i("Response", "")
     }
}