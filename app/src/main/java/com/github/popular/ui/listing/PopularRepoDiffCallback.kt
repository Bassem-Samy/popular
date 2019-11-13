package com.github.popular.ui.listing

import androidx.recyclerview.widget.DiffUtil

class PopularRepoDiffCallback(private val oldList: List<PopularRepoListItem>, private val newList: List<PopularRepoListItem>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].starsCount == newList[newItemPosition].starsCount
    }
}