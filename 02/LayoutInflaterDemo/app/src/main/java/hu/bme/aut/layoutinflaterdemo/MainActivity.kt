package hu.bme.aut.layoutinflaterdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import hu.bme.aut.layoutinflaterdemo.databinding.ActivityMainBinding
import hu.bme.aut.layoutinflaterdemo.databinding.PersonDetailsBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            addPersonView()
        }

        addPersonView()
    }

    private fun addPersonView() {
        val personViewBinding =
            PersonDetailsBinding.inflate(layoutInflater)

        personViewBinding.btnDelete.setOnClickListener {
            binding.layoutContent.removeView(personViewBinding.root)
        }

        binding.layoutContent.addView(personViewBinding.root)
    }
}