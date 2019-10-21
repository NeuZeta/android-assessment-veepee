package com.vp.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.vp.detail.databinding.ActivityDetailBinding
import com.vp.detail.viewmodel.DetailsViewModel
import com.vp.favorites.SharedPreference
import com.vp.favorites.model.FavoriteMovie
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.run

class DetailActivity : DaggerAppCompatActivity(), QueryProvider {

    val sharedPreference = SharedPreference()
    var isFavoriteMovie = false;
    lateinit var posterURL : String;
    lateinit var favorites: MutableList<String>

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val detailViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        binding.viewModel = detailViewModel
        queryProvider = this
        binding.setLifecycleOwner(this)
        detailViewModel.fetchDetails()

        detailViewModel.title().observe(this, Observer {
            supportActionBar?.title = it
        })

        detailViewModel.details().observe(this, Observer {
            println("Mi Println - details = " + it)
            println("Mi Println - poster = " + it.poster)
            posterURL = it.poster
        })

        favorites = sharedPreference.getFavoritesIds(applicationContext)
        isFavoriteMovie = favorites.contains(getMovieId())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)

        val menuItem = menu?.findItem(R.id.star)


        if (isFavoriteMovie){
            menuItem?.setIcon(R.drawable.ic_star_filled)
        } else {
            menuItem?.setIcon(R.drawable.ic_star)
        }

        menuItem?.setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {

            if (isFavoriteMovie){
                isFavoriteMovie = false
                menuItem?.setIcon(R.drawable.ic_star)
                sharedPreference.removeFavorite(applicationContext, getMovieId())

            } else {
                isFavoriteMovie = true
                menuItem?.setIcon(R.drawable.ic_star_filled)
                sharedPreference.addFavorite(applicationContext, FavoriteMovie(getMovieId(), posterURL))
            }

            true
        })


        return true
    }

    override fun getMovieId(): String {
        return intent?.data?.getQueryParameter("imdbID") ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }

    companion object {
        lateinit var queryProvider: QueryProvider
    }
}
