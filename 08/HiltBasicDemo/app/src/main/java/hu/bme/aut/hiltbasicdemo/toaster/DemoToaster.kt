package hu.bme.aut.hiltbasicdemo.toaster

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class DemoToaster @Inject constructor(
    @ActivityContext private val context: Context
) {

    fun doToast() {
        Toast.makeText(context, "HI", Toast.LENGTH_LONG).show()
    }

}