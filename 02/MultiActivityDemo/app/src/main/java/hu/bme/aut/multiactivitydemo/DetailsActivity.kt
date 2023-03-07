package hu.bme.aut.multiactivitydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        if (intent.hasExtra("KEY_DATA")) {
            val data = intent.getStringExtra("KEY_DATA")
            Toast.makeText(this,data, Toast.LENGTH_LONG).show()
        }
    }
}