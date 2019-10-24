package com.example.sample7

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.baseopengl.baseactivities.BaseOpenGl3Activity
import com.example.sample7.surfaceviews.MySurfaceView

class MainActivity : BaseOpenGl3Activity() {
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return MySurfaceView(this)
    }
}
