package com.orange.test.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.orange.test.R
import com.orange.test.api.ApiService.Factory.BASE_URL_IMAGE
import com.orange.test.models.items.Result
import com.squareup.picasso.Picasso

class MoviesAdapter(private val mListMovies: List<Result>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.mTvTitle.text = mListMovies[position].title
        Picasso.get().load(BASE_URL_IMAGE + "w300" + mListMovies[position].posterPath)
            .into(holder.mIvHolder)
    }

    override fun getItemCount() = mListMovies.size

    inner class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val mIvHolder: ImageView by lazy { v.findViewById<ImageView>(R.id.ivHolder) }
        val mTvTitle: TextView by lazy { v.findViewById<TextView>(R.id.tvTitle) }

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemClickListener?.onItemClick(mListMovies[adapterPosition].id)
        }
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }
}