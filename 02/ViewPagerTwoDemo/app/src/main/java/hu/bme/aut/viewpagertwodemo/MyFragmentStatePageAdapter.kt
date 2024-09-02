package hu.bme.aut.viewpagertwodemo

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyFragmentStatePagerAdapter(activity: AppCompatActivity,
                                  val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            FragmentPageOne.newInstance()
        } else {
            FragmentPageTwo.newInstance()
        }
    }


}