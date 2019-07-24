package test.task.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import test.task.newsapp.api.NewsItem


class MainActivity : AppCompatActivity(), NewsFragment.OnNewsFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        displayFragment(MainFragment())
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    displayFragment(MainFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_search -> {
                    displayFragment(EmptyFragment.newInstance(1))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    displayFragment(EmptyFragment.newInstance(2))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    displayFragment(EmptyFragment.newInstance(3))
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_host, fragment)
            .commit()
    }

    override fun onNewsItemSelected(item: NewsItem) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_host, NewsItemFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }

}
