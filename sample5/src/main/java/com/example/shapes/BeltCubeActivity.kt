package com.example.shapes

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.baseopengl.BaseOpenGl3Activity
import com.example.glsurfaceviews.BeltCubeView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class BeltCubeActivity :BaseOpenGl3Activity(){
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return BeltCubeView(context)
    }
}
