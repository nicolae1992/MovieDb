package com.orange.test.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.orange.test.R
import com.orange.test.api.ApiService
import com.orange.test.models.info.InfoMovie
import com.orange.test.ui.fragment.TAG_ID_MOVIE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_info.*


class MovieInfoActivity : AppCompatActivity() {

    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(this)[MoviesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)
        val idMovie: Int = intent.getIntExtra(TAG_ID_MOVIE, 0)
        observable()
        viewModel.getMovieInfo(idMovie)
    }

    @SuppressLint("SetTextI18n")
    private fun setView(infoMovie: InfoMovie) {
        lvMovieInfo.visibility = View.VISIBLE
        Picasso.get().load(ApiService.BASE_URL_IMAGE + "original" + infoMovie.posterPath.toString())
            .into(ivImageHeader)

        tvRelease?.text = infoMovie.releaseDate.toString()
        tvRuntime?.text = "${infoMovie.runtime} min"
        tvTitle?.text = infoMovie.title
        tvOverview ?.text = infoMovie.overview.toString()
        rbRating?.rating = (infoMovie.voteAverage / 2).toFloat()
        tvGenres.text = "Gender: "
        infoMovie.genres.forEach { tvGenres.append("${it.name} , ")}
        btnHomePage.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(infoMovie.homepage)
            startActivity(i)
        }
    }

    private fun observable() {
        viewModel.responseInfoMovies().observe(this, {
            if (it != null) {
                setView(it)

            }
        })
    }
}