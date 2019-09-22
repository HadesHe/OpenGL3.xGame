package com.example.sample7

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.baseopengl.BaseOpenGl3Activity
import com.example.sample7.surfaceviews.EarthView

class EarthActivity :BaseOpenGl3Activity(){

    private lateinit var earthView:EarthView
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        earthView=EarthView(this@EarthActivity)
        return earthView
    }

    override fun onResume() {
        super.onResume()
        earthView.threadFlag=true
    }

    override fun onPause() {
        super.onPause()
        earthView.threadFlag=false
    }

}