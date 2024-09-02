package hu.bme.aut.hiltbasicdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.hiltbasicdemo.analytics.AnalyticsEngine
import hu.bme.aut.hiltbasicdemo.analytics.RealAnalytics
import hu.bme.aut.hiltbasicdemo.analytics.TestAnalytics
import hu.bme.aut.hiltbasicdemo.logging.MainLogger
import hu.bme.aut.hiltbasicdemo.toaster.DemoToaster
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var info: Info

    @Inject
    lateinit var vehicle: Vehicle

    @Inject
    lateinit var logger: MainLogger

    @RealAnalytics
    @Inject
    lateinit var analyitics: AnalyticsEngine

    @Inject
    lateinit var demoToaster: DemoToaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvHello.text = info.text

        //tvHello.text = vehicle.type
        //tvHello.text = analyitics.doTest()

        //demoToaster.doToast()

        //logger.doLogging()
    }
}