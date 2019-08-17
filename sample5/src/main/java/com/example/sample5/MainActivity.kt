package com.example.sample5

import android.content.Context
import android.content.pm.ActivityInfo
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.baseopengl.BaseOpenGl3Activity

class MainActivity : BaseOpenGl3Activity() {
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return OrthoView(this)
    }

//    private lateinit var mGLSurfaceView: OrthoView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)
//
//        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//
//        mGLSurfaceView=OrthoView(this)
//
//        setContentView(mGLSurfaceView)
//        mGLSurfaceView.requestFocus()
//        mGLSurfaceView.isFocusableInTouchMode=true
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mGLSurfaceView.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mGLSurfaceView.onPause()
//    }
}
