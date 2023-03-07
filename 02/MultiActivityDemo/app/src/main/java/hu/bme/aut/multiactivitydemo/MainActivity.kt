package hu.bme.aut.multiactivitydemo

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.multiactivitydemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDetails.setOnClickListener {
            // start another activity

            val intent = Intent(this,
                DetailsActivity::class.java)

            intent.putExtra("KEY_DATA",
                binding.etData.text.toString())

            startActivity(intent)

        }
    }
}