package com.orange.test.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.orange.test.api.ApiService
import com.orange.test.api.ApiService.Factory.API_KEY
import com.orange.test.models.info.InfoMovie
import com.orange.test.models.items.Movies
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = ApiService.create()
    private var disposable: CompositeDisposable = CompositeDisposable()
    private val responseTopMovies = MutableLiveData<Movies>()
    private val responseInfo = MutableLiveData<InfoMovie>()

    fun getResponseTopMovies(): MutableLiveData<Movies> {
        return responseTopMovies
    }

    fun getResponsePopularMovies(): MutableLiveData<Movies> {
        return responseTopMovies
    }

    fun responseInfoMovies(): MutableLiveData<InfoMovie> {
        return responseInfo
    }

     fun getTopMovies(page:Int) {
        disposable.add(
            apiService.getTop(API_KEY,page.toString()).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribeWith(object : DisposableSingleObserver<Movies>() {
                override fun onSuccess(result: Movies) {
                    responseTopMovies.value = result
                }

                override fun onError(e: Throwable) {
                }
            })
        )
    }

     fun getPopularMovies( page:Int) {
        disposable.add(
            apiService.getPopular(API_KEY,page.toString()).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribeWith(object : DisposableSingleObserver<Movies>() {
                override fun onSuccess(result: Movies) {
                    responseTopMovies.value = result
                }

                override fun onError(e: Throwable) {
                }
            })
        )
    }

    fun getMovieInfo( id:Int) {
        disposable.add(
            apiService.getInfo(id.toString(),API_KEY).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribeWith(object : DisposableSingleObserver<InfoMovie>() {
                override fun onSuccess(result: InfoMovie) {
                    responseInfo.value = result
                }

                override fun onError(e: Throwable) {
                }
            })
        )
    }
}

