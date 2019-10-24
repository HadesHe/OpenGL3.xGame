package com.example.shapes

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.baseopengl.baseactivities.BaseOpenGl3Activity
import com.example.glsurfaceviews.BeltCubeView

class BeltCubeActivity : BaseOpenGl3Activity(){
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return BeltCubeView(context)
    }
}
