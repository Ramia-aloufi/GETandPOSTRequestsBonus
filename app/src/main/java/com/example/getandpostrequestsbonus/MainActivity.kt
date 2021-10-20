package com.example.getandpostrequestsbonus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var et:EditText
    lateinit var tv:TextView
    lateinit var btnAdd:Button
    lateinit var btnShow:Button
    lateinit var rr :Names.NamesItem
    private val apiInterface = Constants.apiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et = findViewById(R.id.editTextTextPersonName)
        btnAdd = findViewById(R.id.button)
        btnShow = findViewById(R.id.button2)
        tv = findViewById(R.id.tv)
        rr = Names.NamesItem()


        btnAdd.setOnClickListener {
            PostName()
        }
        btnShow.setOnClickListener {
            GetNames()
        }

    }
    fun PostName(){
        var tt = et.text.toString()
        rr.name = tt
       apiInterface?.added(rr)?.enqueue(object : Callback<Names.NamesItem>{
           override fun onResponse(call: Call<Names.NamesItem>, response: Response<Names.NamesItem>) {
               Log.d("po", response.code().toString())
               Log.d("rr","$rr")

               Toast.makeText(this@MainActivity,"Addedd",Toast.LENGTH_SHORT).show()
           }

           override fun onFailure(call: Call<Names.NamesItem>, t: Throwable) {
               Toast.makeText(this@MainActivity,"Failure",Toast.LENGTH_SHORT).show()

           }

       } )
    }

    fun GetNames(){
        var names = ""
        apiInterface?.getNames()?.enqueue(object :Callback<ArrayList<Names.NamesItem>>{
            override fun onResponse(call: Call<ArrayList<Names.NamesItem>>, response: Response<ArrayList<Names.NamesItem>>) {
                Log.d("po", response.code().toString())
                Toast.makeText(this@MainActivity,"Get",Toast.LENGTH_SHORT).show()
                var uu = response.body()
                for (i in 0 until uu!!.size){
                    names += "${ uu[i].name }\n"
                }
                tv.text = names
            }

            override fun onFailure(call: Call<ArrayList<Names.NamesItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Failure",Toast.LENGTH_SHORT).show()
            }

        })
    }
}