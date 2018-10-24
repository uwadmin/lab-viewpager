package edu.uw.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity(), MovieListFragment.OnMovieSelectedListener, SearchFragment.OnSearchListener {

    private lateinit var searchFragment: SearchFragment
    private lateinit var listFragment: MovieListFragment
    private lateinit var detailFragment: DetailFragment
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchFragment = SearchFragment.newInstance()

        viewPager = findViewById(R.id.pager)
        pagerAdapter = MoviePagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
    }

    override fun onSearchSubmitted(searchTerm: String) {
        listFragment = MovieListFragment.newInstance(searchTerm)
        pagerAdapter.notifyDataSetChanged()
        viewPager.currentItem = 1 //hard-code the shift
    }

    override fun onMovieSelected(movie: Movie) {
        Log.v(TAG, "Detail for $movie")
        detailFragment = DetailFragment.newInstance(movie)
        pagerAdapter.notifyDataSetChanged()
        viewPager.currentItem = 2 //hard-code the shift
    }

    private inner class MoviePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            //Hard-code the ordering as an example
            if (position == 0) return searchFragment
            if (position == 1) return listFragment
            return if (position == 2) detailFragment else null
        }

        //work-around for Fragment replacement
        override fun getItemPosition(`object`: Any?): Int {
            return PagerAdapter.POSITION_NONE
        }

        override fun getCount(): Int {
            return 3
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    companion object {

        private val TAG = "MainActivity"
    }

}
