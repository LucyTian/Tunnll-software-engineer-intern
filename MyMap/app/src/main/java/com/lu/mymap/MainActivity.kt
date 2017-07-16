package com.lu.mymap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn1:ImageButton = findViewById(R.id.btn1)
        btn1.setOnClickListener {
            // TODO Auto-generated method stub
            finish()
            System.exit(0)
        }
    }
}
