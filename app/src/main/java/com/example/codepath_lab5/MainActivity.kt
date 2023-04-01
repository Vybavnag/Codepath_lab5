package com.example.codepath_lab5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import com.example.codepath_lab5.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.Headers
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    var petImageURL = ""
    var petName=""
    var petHouse=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCharImageUrl()
        val button = findViewById<Button>(R.id.nextpokemon)
        val imageView =findViewById<ImageView>(R.id.GOTimg)
        val textView = findViewById<TextView>(R.id.GOTname)
        val textView1= findViewById<TextView>(R.id.GOThouse)
        getNextImage(button,imageView, textView, textView1)
    }


    private fun getCharImageUrl() {
        val client = AsyncHttpClient()
        client["https://thronesapi.com/api/v2/Characters",object  : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog", "response successful$json")
                val rnds = (0..52).random()
                var resultsJSON = json.jsonArray.getJSONObject(rnds)
                petImageURL = resultsJSON.getString("imageUrl")
                petName = resultsJSON.getString("fullName")
                petHouse = resultsJSON.getString("family")
                Log.d("petImageURL", "pet image URL set")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]
    }

    private fun getNextImage(button: Button, imageView: ImageView, textView: TextView, textView1: TextView) {
        button.setOnClickListener {
            getCharImageUrl()
            getNextText(textView, textView1)
            Glide.with(this)
                . load(petImageURL)
                .fitCenter()
                .into(imageView)
        }
    }

    private fun getNextText(textView: TextView, textView1: TextView){
        textView.text= petName
        textView1.text = petHouse
    }
}
