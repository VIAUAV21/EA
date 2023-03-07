package hu.bme.aut.viewpagertwodemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FragmentPageOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page_one, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FragmentPageOne()
    }
}