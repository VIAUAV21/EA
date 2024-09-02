package hu.ait.bmedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import hu.ait.bmedemo.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {
            var name = binding.etUserName.text.toString()
            name += Date(System.currentTimeMillis()).toString()

            val result = 5
            val text = getString(R.string.result_text, result)

            Toast.makeText(this, name, Toast.LENGTH_LONG).show()
        }
    }

}