package com.example.opengl3

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var mTestView: TestView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mTestView=TestView(this)
        mTestView.requestFocus()
        mTestView.isFocusableInTouchMode=true
        setContentView(mTestView)
    }

    override fun onResume() {
        super.onResume()
        mTestView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mTestView.onPause()
    }
}
