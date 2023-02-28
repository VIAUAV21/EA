package hu.bme.aut.hellobmeandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import hu.bme.aut.hellobmeandroid.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    var num: String? = null

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShow.setOnClickListener {
            binding.tvData.text = "Current time: ${
                Date(System.currentTimeMillis()).toString()}"
        }
    }
}