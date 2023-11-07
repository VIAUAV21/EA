package hu.bme.aut.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coroutineTest()
    }

    class MyThread: Runnable {

        override fun run() {
            Log.d("TAG_CO",Thread.currentThread().getName())
            Thread.sleep(5) //pretend that some heavy calculation happens
        }

    }

    fun threadExamples() {
        Thread(MyThread()).start()
        thread {
            Log.d("TAG_CO",Thread.currentThread().getName())
            Thread.sleep(5) //pretend that some heavy calculation happens
        }

    }


    fun coroutineTest() {
        //threadExamples()
        runBlocking {
            Log.d("TAG_CO","thread of main() method: " + Thread.currentThread().name)
            var recommendedProducts: Deferred<List<String>> = async{
                Log.d("TAG_CO","requesting recommended products...")
                Log.d("TAG_CO","thread of recommended products request: ${Thread.currentThread().name}")
                withContext(Dispatchers.IO){//suspending point: suspending the coroutine started with async, DOES NOT block the main thread
                    requestDataFromServer(10000) //simulate a request to a server
                }
                Log.d("TAG_CO","thread of recommended products request: ${Thread.currentThread().name}")
                listOf("product1", "product2")
            }

            var recentlySeenProducts: Deferred<List<String>> = async{
                Log.d("TAG_CO","requesting recently seen products...")
                Log.d("TAG_CO","thread of recently seen products request: ${Thread.currentThread().name}")
                withContext(Dispatchers.IO){//suspending point: suspending the coroutine started with async, DOES NOT block the main thread

                    requestDataFromServer(7000) //simulate a request to a server
                }
                Log.d("TAG_CO","requesting recently seen products finished...")

                listOf("product3", "product4")
            }

            // kivétel kezelésre: CoroutineExceptionHandler /  https://kotlinlang.org/docs/reference/coroutines/exception-handling.html

            showOnUI(recommendedProducts.await().union(recentlySeenProducts.await()).toList())
        }
    }

    suspend fun requestDataFromServer(timeNeeded: Long){
        Log.d("TAG_CO","data request to server started")
        Log.d("TAG_CO","thread of requestDataFromServer() function: " + Thread.currentThread().name)
        delay(timeNeeded)
        Log.d("TAG_CO","data request to server ended")
    }

    fun showOnUI(data: List<String>) {
        Log.d("TAG_CO","Will be shown on UI: " + data)
        Log.d("TAG_CO","thread of showOnUI() function: " + Thread.currentThread().name)
        //showing the profucts on the UI
        Toast.makeText(this, "Will be shown on UI: " + data, Toast.LENGTH_LONG).show()
    }

}