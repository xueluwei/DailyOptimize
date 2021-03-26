package com.xlwapp.dailyoptimize

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
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
            if (!isAvilible(MARKET_GOOGLE)) {
                Toast.makeText(this,"doesn't have google play store",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
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

        ID.add("running app")
        ID.add("run app")
        ID.add("running tracker")
        ID.add("mile tracker")

        ID.add("video downloader")
        ID.add("videoder")
        ID.add("downloader")

        ID.add("photo gallery")
        ID.add("photos app")
        ID.add("gallery photos")

        ID.add("math games")
        ID.add("free maths game")
        ID.add("addition games")


        ID.add("pdf scanner")
        ID.add("docscan")
        ID.add("pdf scanner app")
    }

    fun isAvilible(packageName: String?): Boolean {
        //获取packagemanager
        val packageManager: PackageManager = this.getPackageManager()
        //获取所有已安装程序的包信息
        val packageInfos =
            packageManager.getInstalledPackages(0)
        //用于存储所有已安装程序的包名
        val packageNames: MutableList<String> = ArrayList()
        //从pinfo中将包名字逐一取出，压入pName list中
        for (i in packageInfos.indices) {
            val packName = packageInfos[i].packageName
            packageNames.add(packName)
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName)
    }
}
