package com.github.popular.ui.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.popular.R

import com.github.popular.utils.ImageLoader

class PopularRepoAdapter(private val onItemClicked: (PopularRepoListItem) -> Unit, private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<PopularRepoAdapter.ViewHolder>() {
    private val items: MutableList<PopularRepoListItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_repo_list_item, parent, false)
        return ViewHolder(view, imageLoader) { position ->
            onItemClicked.invoke(items[position])
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(newItems: List<PopularRepoListItem>) {
        val diffCallback = PopularRepoDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(view: View, private val imageLoader: ImageLoader, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener { onItemClicked.invoke(adapterPosition) }
        }

        private val repoName: AppCompatTextView = view.findViewById(R.id.repo_name_text)
        private val ownerImage: AppCompatImageView = view.findViewById(R.id.owner_image)
        private val ownerName: AppCompatTextView = view.findViewById(R.id.repo_owner_text)
        private val starsCount: AppCompatTextView = view.findViewById(R.id.stars_text)
        private val repoDescription: AppCompatTextView = view.findViewById(R.id.repo_description_text)
        fun bind(item: PopularRepoListItem) {
            repoName.text = item.name
            ownerName.text = item.owner
            starsCount.text = item.starsCount.toString()
            repoDescription.text = item.description.fold({ "" }, { it })
            imageLoader.loadImageCircular(ownerImage, item.ownerAvatar, R.mipmap.ic_launcher)
        }
    }
}