package com.example

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.example.activitys.TanslateActivity
import com.example.sample5.R

class SelectActivity :AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnTranslate->{
                val intent=Intent(this, TanslateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val btnTanslate: Button by lazy {
        findViewById(R.id.btnTranslate) as Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        btnTanslate.setOnClickListener(this)
    }

}