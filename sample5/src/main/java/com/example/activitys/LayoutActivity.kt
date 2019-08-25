package com.example.activitys

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.baseopengl.BaseOpenGl3Activity
import com.example.glsurfaceviews.LayoutSurfaceView

class LayoutActivity :BaseOpenGl3Activity(){
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return LayoutSurfaceView(context)
    }

}