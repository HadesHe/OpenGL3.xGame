package com.example.activitys

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.baseopengl.BaseOpenGl3Activity
import com.example.glsurfaceviews.TanslateView

class TanslateActivity:BaseOpenGl3Activity() {
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return TanslateView(context)
    }

}
