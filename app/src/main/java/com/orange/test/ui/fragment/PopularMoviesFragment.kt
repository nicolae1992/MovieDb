package com.orange.test.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.orange.test.R
import com.orange.test.models.items.Result
import com.orange.test.ui.MovieInfoActivity
import com.orange.test.ui.MoviesViewModel
import com.orange.test.ui.adapter.MoviesAdapter
import com.orange.test.ui.adapter.PaginationListener
import com.orange.test.ui.adapter.PaginationListener.Companion.PAGE_START
import kotlinx.android.synthetic.main.fragment_movies.*
const val TAG_ID_MOVIE  = "id_movie"
class PopularMoviesFragment : BaseFragment(), MoviesAdapter.OnItemClickListener {
    private var mLayoutManager: GridLayoutManager? = null
    private var adapterMovies: MoviesAdapter? = null
    private var movies: MutableList<Result> = arrayListOf()
    private var currentPage: Int = PAGE_START

    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(this)[MoviesViewModel::class.java]
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_movies
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapterMovies = MoviesAdapter(movies)
        mLayoutManager = GridLayoutManager(requireActivity(), 2)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        observable()
        adapterMovies?.setOnItemClickListener(this)
        viewModel.getPopularMovies(currentPage)
    }

    private fun initRecycler() {
        rvMovies?.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = adapterMovies
        }
    }
    private fun observable(){
        viewModel.getResponsePopularMovies().observe(viewLifecycleOwner, {
            movies.addAll(it.results)
            adapterMovies?.notifyDataSetChanged()
        })

        rvMovies?.addOnScrollListener(object : PaginationListener(mLayoutManager) {
            override fun loadMoreItems() {
                currentPage++
                viewModel.getPopularMovies(currentPage)
            }
        })
    }

    override fun onItemClick(id: Int) {
        val myIntent = Intent(activity, MovieInfoActivity::class.java)
        myIntent.putExtra(TAG_ID_MOVIE, id)
        startActivity(myIntent)
    }
}