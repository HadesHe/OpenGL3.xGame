package com.example.sample5

import android.content.Context
import android.content.pm.ActivityInfo
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.baseopengl.BaseOpenGl3Activity

class MainActivity : BaseOpenGl3Activity() {
    override fun getGlSurfaceView(context: Context): GLSurfaceView {
        return OrthoView(this)
    }

}
