package com.main.apitest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }


        search.setOnClickListener {
            var input = userinput.text.toString()
            fetchData(input)
        }


    }

    fun fetchData( input: String){
        val url = "https://raw.githubusercontent.com/TreNgheDiCode/api_android/main/getlist"
//        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
//            { response ->
//                if(response.get("Response")=="False"){
//                    name.text = "Incorrect detail"
//                }else {
//                    Glide.with(this).load(response.getString("Poster")).into(image)
//                    name.text = response.getString("Plot")
//                    name.text = response.getString("Title")+"\n\n"+"Writer: "+response.getString("Writer")
//                }
//            },
//            { error ->
//                Log.d("vol",error.toString())
//            }
//        )

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                plot.text = "Response is: ${response.substring(0, 500)}"
            },
            Response.ErrorListener { plot.text = "That didn't work!" })


        requestQueue.add(stringRequest)
    }
}