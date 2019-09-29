package com.example.sample7

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.baseopengl.BaseOpenGl3Activity
import com.example.sample7.surfaceviews.PointView

class PointActivity:BaseOpenGl3Activity(){
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return PointView(this@PointActivity)
    }

}