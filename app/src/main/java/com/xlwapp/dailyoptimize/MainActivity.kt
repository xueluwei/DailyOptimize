package com.xlwapp.dailyoptimize

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val MARKET_GOOGLE = "com.android.vending"
    val ID = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var i: Int = 0
        addId()
        text_view.text = i++.toString()
        button.setOnClickListener{
            if(ID.isNullOrEmpty()){
                Toast.makeText(this,"finish",Toast.LENGTH_SHORT).show()
            }else{
                text_view.text = ID.get(0) + "\n" + i++.toString()
                val intent = Intent(Intent.ACTION_VIEW);
                val uri = Uri.parse("http://play.google.com/store/search?q=" + ID.removeAt(0) + "&c=apps");
                intent.setData(uri);
                intent.setPackage(MARKET_GOOGLE);
                startActivity(intent);
            }
        }
    }

    private fun addId() {
        ID.add("qr scanner")
        ID.add("qr code reader")
        ID.add("qr code scanner ")
        ID.add("free qr scanner")
        ID.add("free qr code reader")
        ID.add("barcode scanner")

        ID.add("fat burning")
        ID.add("home workout")
        ID.add("weight loss")

        ID.add("abs workout 30 day")
        ID.add("abs workout")

        ID.add("lose weight")
        ID.add("lose weight in 30 days")
        ID.add("weight loss")

        ID.add("increase height ")
        ID.add("height increase")
        ID.add("height increase exercise")
        ID.add("grow taller")
        ID.add("taller exercise")

        ID.add("baby tracker")
    }
}
