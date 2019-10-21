package com.example.sample7.activities

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.baseopengl.BaseOpenGl3Activity
import com.example.sample7.surfaceviews.TwoDArrayView

class TwoDPointActivity :BaseOpenGl3Activity(){
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return TwoDArrayView(context)
    }

}