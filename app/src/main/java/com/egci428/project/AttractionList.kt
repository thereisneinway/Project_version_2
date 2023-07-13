package com.egci428.project

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.Request
import com.egci428.project.Data.AttractionAdapter
import com.egci428.project.Data.Attractions
import com.google.gson.reflect.TypeToken

class AttractionList : AppCompatActivity(), AttractionAdapter.ClickListener {
    lateinit var recyclerView: RecyclerView
    private var region = ""
    var attractionList : ArrayList<Attractions> = ArrayList()
    var adapter = AttractionAdapter(attractionList, this,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_list)
        region = intent.getStringExtra("region").toString()
        val regionText = findViewById<TextView>(R.id.region)
        regionText.text = region
        val backBtn = findViewById<Button>(R.id.backList)
        val refreshBtn = findViewById<Button>(R.id.refreshBtn)
        recyclerView = findViewById(R.id.totalList)
        recyclerView.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        //load data from firebase to attractionList
        fetchData()


        backBtn.setOnClickListener{
            finish()
        }
        refreshBtn.setOnClickListener {
            //load data from firebase to attractionList
            fetchData()
            //load image from file by uri from firebase
        }

    }

    private fun fetchData(){
        val jsonURL = "https://egi428-project-default-rtdb.firebaseio.com/$region.json"
        val client = OkHttpClient()
        val loadJsonAsync = object: AsyncTask<String, String, String>() {
            override fun onPreExecute() {
                Toast.makeText(this@AttractionList,"Fetching data", Toast.LENGTH_SHORT).show()
            }
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                try {
                    attractionList.clear()
                    attractionList.addAll(Gson().fromJson<ArrayList<Attractions>>(result,
                        object : TypeToken<ArrayList<Attractions>>() {}.type))
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    println("=========Loaded to adapter")
                    Toast.makeText(this@AttractionList,"Found: "+ attractionList.size, Toast.LENGTH_SHORT).show()
                }catch(e: Exception){
                    e.printStackTrace()
                    Toast.makeText(this@AttractionList,"Can't connect to database", Toast.LENGTH_SHORT).show()
                }
            }
            override fun doInBackground(vararg params: String): String {
                val builder = Request.Builder()
                builder.url(params[0])
                val request = builder.build()
                try {
                    val response = client.newCall(request).execute()
                    return response.body.string()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return ""
            }

        }
        val url_get_data = StringBuilder()
        url_get_data.append(jsonURL)
        loadJsonAsync.execute(url_get_data.toString())

    }
    override fun clickedItem(attractionsModel: Attractions) {
        val intent = Intent(this, AttractionDetail::class.java)
        intent.putExtra("name", attractionsModel.name)
        intent.putExtra("description", attractionsModel.description)
        intent.putExtra("location", attractionsModel.location)
        intent.putExtra("hours", attractionsModel.hours)
        intent.putExtra("rating", attractionsModel.rating.toFloat())
        intent.putExtra("imageAddr", attractionsModel.imageAddr)
        intent.putExtra("lat", attractionsModel.lat.toString())//Double
        intent.putExtra("long", attractionsModel.long.toString())//Double
        startActivity(intent)
    }
}