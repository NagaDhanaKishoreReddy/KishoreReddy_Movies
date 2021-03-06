 package com.mvvm_withroom

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvvm_withroom.adapter.MoviesAdapter
import com.mvvm_withroom.databinding.ActivityMainBinding
import com.mvvm_withroom.network.MoviesBuilder
import com.mvvm_withroom.network.NetworkHelper
import com.mvvm_withroom.response.SearchMovieResponse
import com.mvvm_withroom.utils.AppConstants
import com.mvvm_withroom.utils.Status
import com.mvvm_withroom.viewmodel.MoviesViewModel
import com.mvvm_withroom.viewmodel.ViewModelFactory

 class MoviesListActivity : AppCompatActivity() {
     private lateinit var viewModel: MoviesViewModel
     private lateinit var adapter: MoviesAdapter
     private lateinit var mBinding : ActivityMainBinding
     private var pageNo : Int = 1
     private var query : String = "A"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupViewModel()
        setupUI()
        showLoader(true)
        setupObservers(query)

    }

     private fun setupUI() {
            mBinding.textGo.setOnClickListener {
                if (!mBinding.searchView.query.toString().isNullOrEmpty()) {
                    query = mBinding.searchView.query.toString()
                    showLoader(true)
                    setupObservers(query)
                }
            }

         mBinding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
             override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                 super.onScrolled(recyclerView, dx, dy)


             }
         })
     }

     private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(NetworkHelper(MoviesBuilder.apiService))
        ).get(MoviesViewModel::class.java)
    }
     
     private fun setupObservers(query: String) {
         viewModel.getMoviesList(AppConstants.apiKey, AppConstants.language, this.query, pageNo).observe(this, Observer {
             it?.let { resource ->
                 when (resource.status) {
                     Status.SUCCESS -> {
                         showLoader(false)
                         resource.data?.let { moviesList -> retrieveList(moviesList) }
                     }
                     Status.ERROR -> {
                         showLoader(false)
                         Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                     }
                     Status.LOADING -> {

                     }
                 }
             }
         })
     }

     private fun retrieveList(searchMovieResponse: SearchMovieResponse) {

         mBinding.rvMovies.also {
             it.layoutManager = GridLayoutManager(this, 1)
             it.setHasFixedSize(true)
             pageNo++
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
     private fun showLoader(shouldShow : Boolean) {
         if (shouldShow) {
             mBinding.pbLoader.visibility = View.VISIBLE
             mBinding.rvMovies.visibility = View.GONE
         } else {
             mBinding.pbLoader.visibility = View.GONE
             mBinding.rvMovies.visibility = View.VISIBLE
         }
     }
}