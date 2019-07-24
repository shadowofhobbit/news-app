package test.task.newsapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class PagerAdapter(fm: FragmentManager, context: Context) :
    FragmentPagerAdapter(fm) {
    private val pageCount = 2

    private val tabTitles = arrayOf(context.getString(R.string.tab_main),
        context.getString(R.string.tab_vip))

    override fun getCount(): Int {
        return pageCount
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return NewsFragment.newInstance()
        } else {
            return EmptyFragment.newInstance(0)
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }
}