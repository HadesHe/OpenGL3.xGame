package com.example.sample6

import android.content.Context
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.baseopengl.BaseOpenGl3Activity
import com.example.sample6.surfaceviews.BallSurfaceView

class MainActivity : AppCompatActivity() {

    private lateinit var mGLSurfaceView: BallSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mGLSurfaceView=BallSurfaceView(this)
        setContentView(mGLSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        mGLSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mGLSurfaceView.onPause()
    }

}
