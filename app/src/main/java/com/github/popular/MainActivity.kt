package com.github.popular

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.popular.ui.details.RepositoryDetailFragment
import com.github.popular.ui.listing.PopularListingFragment

class MainActivity : AppCompatActivity(), PopularListingFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListingFragment()
    }

    private fun initListingFragment() {
        if (supportFragmentManager.findFragmentByTag(PopularListingFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    PopularListingFragment.newInstance(),
                    PopularListingFragment.TAG
                ).commit()
        }
    }

    override fun onRepoClicked(repositoryId: String) {
        if (supportFragmentManager.findFragmentByTag(RepositoryDetailFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    RepositoryDetailFragment.newInstance(repositoryId),
                    PopularListingFragment.TAG
                )
                .addToBackStack(null)
                .commit()
        }
    }
}
