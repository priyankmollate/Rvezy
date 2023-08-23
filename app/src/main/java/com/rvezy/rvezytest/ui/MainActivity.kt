package com.rvezy.rvezytest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.rvezy.rvezytest.ItemListAdapter
import com.rvezy.rvezytest.databinding.ActivityMainBinding
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var breeds: MutableList<String>
    private lateinit var binding: ActivityMainBinding
    private lateinit var listViewAdapter: ItemListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listViewAdapter = ItemListAdapter(this, mutableListOf())
        binding.listView.adapter = listViewAdapter


        binding.listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                Log.v("Test",breeds.get(position).toString())

                val sendBundle = Bundle()
                sendBundle.putString("url", listViewAdapter.getItem(position).toString())
                sendBundle.putString("value", breeds.get(position).toString())
                val i = Intent(this, Description::class.java)
                i.putExtras(sendBundle)
                startActivity(i)

            }

        fetchItemList()
    }

    private fun fetchItemList() {
        val url = "https://api.thecatapi.com/v1/images/search?limit=10&breed_ids=beng&api_key=24be637f-e596-4847-b47a-1791feeea1bd" // Replace with your API URL

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                        val itemList = parseItemList(responseBody)
                        listViewAdapter.setItems(itemList)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }

    private fun parseItemList(response: String): List<String> {
        val itemList = mutableListOf<String>()
         breeds = mutableListOf<String>()

        val jsonArray = JSONArray(response)
        for (i in 0 until jsonArray.length()) {
            itemList.add(jsonArray.getJSONObject(i).getString("url"))
            breeds.add(jsonArray.getJSONObject(i).getString("breeds"))
        }
        return itemList
    }
}

