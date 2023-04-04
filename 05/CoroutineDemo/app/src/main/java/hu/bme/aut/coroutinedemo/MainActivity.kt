package hu.bme.aut.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            println(Thread.currentThread().getName())
            Thread.sleep(5) //pretend that some heavy calculation happens
        }

    }

    fun threadExamples() {
        Thread(MyThread()).start()
        thread {
            println(Thread.currentThread().getName())
            Thread.sleep(5) //pretend that some heavy calculation happens
        }

    }


    fun coroutineTest() {
        //threadExamples()
        runBlocking {
            println("thread of main() method: " + Thread.currentThread().name)
            var recommendedProducts: Deferred<List<String>> = async{
                println("requesting recommended products...")
                println("thread of recommended products request: ${Thread.currentThread().name}")
                withContext(Dispatchers.IO){//suspending point: suspending the coroutine started with async, DOES NOT block the main thread
                    requestDataFromServer(10000) //simulate a request to a server
                }
                println("thread of recommended products request: ${Thread.currentThread().name}")
                listOf("product1", "product2")
            }

            var recentlySeenProducts: Deferred<List<String>> = async{
                println("requesting recently seen products...")
                println("thread of recently seen products request: ${Thread.currentThread().name}")
                withContext(Dispatchers.IO){//suspending point: suspending the coroutine started with async, DOES NOT block the main thread

                    requestDataFromServer(7000) //simulate a request to a server
                }
                println("requesting recently seen products finished...")

                listOf("product3", "product4")
            }

            // kivétel kezelésre: CoroutineExceptionHandler /  https://kotlinlang.org/docs/reference/coroutines/exception-handling.html

            showOnUI(recommendedProducts.await().union(recentlySeenProducts.await()).toList())
        }
    }

    suspend fun requestDataFromServer(timeNeeded: Long){
        println("data request to server started")
        println("thread of requestDataFromServer() function: " + Thread.currentThread().name)
        delay(timeNeeded)
        println("data request to server ended")
    }

    fun showOnUI(data: List<String>) {
        println("Will be shown on UI: " + data)
        println("thread of showOnUI() function: " + Thread.currentThread().name)
        //showing the profucts on the UI
        Toast.makeText(this, "Will be shown on UI: " + data, Toast.LENGTH_LONG).show()
    }

}