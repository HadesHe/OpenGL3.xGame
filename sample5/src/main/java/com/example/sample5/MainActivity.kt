package com.example.sample5

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.baseopengl.baseactivities.BaseOpenGl3Activity

class MainActivity : BaseOpenGl3Activity() {
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return OrthoView(this)
    }

}
