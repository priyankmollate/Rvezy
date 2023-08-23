package com.rvezy.rvezytest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rvezy.rvezytest.R
import org.json.JSONArray
import org.json.JSONObject

class Description : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)


        val image: ImageView =findViewById(R.id.imageView);
        val textView1: TextView =findViewById(R.id.textView1);
        val textView2: TextView =findViewById(R.id.textView2);
        val textView3: TextView =findViewById(R.id.textView3);

        val bundle = intent.extras
        val value = bundle!!.getString("value")
        val url = bundle!!.getString("url")


        var obj = JSONArray(value)[0].toString()
        var jsonObject =JSONObject(obj)

        Log.v("obj",jsonObject.getString("name"))

        textView1.setText("Name: "+jsonObject.getString("name"))
        textView2.setText("Origin: "+jsonObject.getString("origin"))
        textView3.setText("Description: \n"+jsonObject.getString("description"))

        Glide.with(applicationContext).load(url).into(image)

    }


    override fun onBackPressed() {
        super.onBackPressed()

        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}