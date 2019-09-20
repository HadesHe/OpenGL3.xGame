package com.example.sample7

import android.content.Context
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.baseopengl.BaseOpenGl3Activity
import com.example.sample7.surfaceviews.MySurfaceView

class MainActivity : BaseOpenGl3Activity() {
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return MySurfaceView(this)
    }
}
