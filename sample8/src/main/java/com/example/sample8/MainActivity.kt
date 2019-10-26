package com.example.sample8

import android.content.Context
import android.content.pm.ActivityInfo
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.baseopengl.baseactivities.BaseOpenGl3Activity
import com.example.sample8.glsurfaceviews.CylinderView

class MainActivity : BaseOpenGl3Activity() {
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return CylinderView(this@MainActivity)
    }


}
