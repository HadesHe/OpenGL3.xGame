package com.example.sample5

import android.content.Context
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OrthoView(context:Context) :GLSurfaceView(context){

    init {
        setEGLContextClientVersion(3)
        mRenderer=OrthoViewRenderer()
        setRenderer(mRenderer)
        renderMode= RENDERMODE_CONTINUOUSLY
    }

    inner class OrthoViewRenderer:Renderer{
        override fun onDrawFrame(gl: GL10?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}