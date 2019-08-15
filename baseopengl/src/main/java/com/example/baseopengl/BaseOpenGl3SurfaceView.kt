package com.example.baseopengl

import android.content.Context
import android.opengl.GLSurfaceView

abstract class BaseOpenGl3SurfaceView(context: Context) :GLSurfaceView(context){

    private var mRenderer: Renderer

    init {
        setEGLContextClientVersion(3)
        mRenderer=getRender()
        setRenderer(mRenderer)
        renderMode=getGlSurfaceRenderMode()
    }

    protected fun getGlSurfaceRenderMode()=GLSurfaceView.RENDERMODE_CONTINUOUSLY

    protected abstract fun getRender(): Renderer
}