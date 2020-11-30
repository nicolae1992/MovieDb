package com.orange.test.ui.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationListener(private val layoutManager: GridLayoutManager?) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager?.childCount ?: 0
        val totalItemCount = layoutManager?.itemCount ?: 0
        val firstVisibleItemPosition = layoutManager?.findFirstVisibleItemPosition() ?: 0
        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
            loadMoreItems()
        }
    }

    protected abstract fun loadMoreItems()

    companion object {
        const val PAGE_START = 1
        private const val PAGE_SIZE = 20
    }
}