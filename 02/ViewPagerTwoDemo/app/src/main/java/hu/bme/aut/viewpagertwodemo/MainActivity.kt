package hu.bme.aut.viewpagertwodemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.viewpagertwodemo.databinding.ActivityMainBinding
import hu.bme.aut.viewpagertwodemo.pageanim.DepthPageTransformer
import hu.bme.aut.viewpagertwodemo.pageanim.ZoomOutPageTransformer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Toast.makeText(this@MainActivity, "Selected position: ${position}",
                Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var pageNames: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pageNames = resources.getStringArray(R.array.tab_names)

        val fragmentStatePagerAdapter = MyFragmentStatePagerAdapter(
            this, 2)
        binding.mainViewPager.adapter = fragmentStatePagerAdapter

        //binding.mainViewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        binding.mainViewPager.registerOnPageChangeCallback(pageChangeCallback)

        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            //To get the first name of doppelganger celebrities
            tab.text = pageNames[position]
        }.attach()

        //binding.mainViewPager.setPageTransformer(ZoomOutPageTransformer())
        binding.mainViewPager.setPageTransformer(DepthPageTransformer())
    }

    override fun onDestroy() {
        binding.mainViewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onDestroy()
    }
}