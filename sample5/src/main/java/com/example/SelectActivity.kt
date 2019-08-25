package com.example

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.example.activitys.DrawRangeElementActivity
import com.example.activitys.LayoutActivity
import com.example.activitys.TanslateActivity
import com.example.sample5.MainActivity
import com.example.sample5.R
import com.example.shapes.BeltCubeActivity

class SelectActivity :AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnTranslate->{
                val intent=Intent(this, TanslateActivity::class.java)
                startActivity(intent)
            }
            R.id.btnMain ->{
                val intent=Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btnBelt ->{
                val intent=Intent(this,BeltCubeActivity::class.java)
                startActivity(intent)
            }
            R.id.btnDrawRange->{
                val intent=Intent(this,DrawRangeElementActivity::class.java)
                startActivity(intent)
            }
            R.id.btnDrawRange->{
                val intent=Intent(this,DrawRangeElementActivity::class.java)
                startActivity(intent)
            }
            R.id.btnLayout->{
                val intent=Intent(this,LayoutActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val btnTanslate: Button by lazy {
        findViewById(R.id.btnTranslate) as Button
    }

    private val btnMain: Button by lazy {
        findViewById(R.id.btnMain) as Button
    }

    private val btnBelt: Button by lazy {
        findViewById(R.id.btnBelt) as Button
    }
    private val btnDrawRange: Button by lazy {
        findViewById(R.id.btnDrawRange) as Button
    }

    private val btnLayout: Button by lazy {
        findViewById(R.id.btnLayout) as Button
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        btnTanslate.setOnClickListener(this)
        btnMain.setOnClickListener(this)
        btnBelt.setOnClickListener(this)
        btnDrawRange.setOnClickListener(this)
        btnLayout.setOnClickListener(this)
    }

}