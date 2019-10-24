package com.example.baseopengl.baseactivities

import android.content.Context
import android.content.pm.ActivityInfo
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager

/**
 * only GLSurfaceView
 */
abstract class BaseOpenGl3Activity :AppCompatActivity(){


    private lateinit var mGLSurfaceView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        mGLSurfaceView=getGlSurfaceView(this)
        setContentView(mGLSurfaceView)
        mGLSurfaceView.requestFocus()
        mGLSurfaceView.isFocusableInTouchMode=true

    }

    override fun onResume() {
        super.onResume()
        mGLSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mGLSurfaceView.onPause()
    }

    protected abstract fun getGlSurfaceView(context: Context): GLSurfaceView
}