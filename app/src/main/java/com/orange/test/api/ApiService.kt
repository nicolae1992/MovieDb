package com.orange.test.api

import com.orange.test.models.info.InfoMovie
import com.orange.test.models.items.Movies
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("movie/popular")
    fun getPopular(@Query("api_key",) token: String,@Query("page") page: String): Single<Movies>

    @GET("movie/top_rated")
    fun getTop(@Query("api_key") token: String,@Query("page") page: String): Single<Movies>

    @GET("movie/{movie_id}")
    fun getInfo(@Path("movie_id") moviesId: String,@Query("api_key") token: String): Single<InfoMovie>

    companion object Factory {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "dad8a59d86a2793dda93aa485f7339c1"
        const val BASE_URL_IMAGE = "http://image.tmdb.org/t/p/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }

        private fun getClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            return OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES)
                .addInterceptor(interceptor).build()
        }
    }
}