 package com.mvvm_withroom

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mvvm_withroom.network.MoviesBuilder
import com.mvvm_withroom.network.NetworkHelper
import com.mvvm_withroom.response.SearchMovieResponse
import com.mvvm_withroom.utils.Status
import com.mvvm_withroom.viewmodel.MoviesViewModel
import com.mvvm_withroom.viewmodel.ViewModelFactory

 class MainActivity : AppCompatActivity() {
     private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupViewModel()
        setupObservers()

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
     }

     private fun retrieveList(searchMovieResponse: SearchMovieResponse) {
        Log.i("Response", "")
     }
}