package com.example.opengl3

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import java.lang.Exception
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class TestView(context: Context):GLSurfaceView(context) {

    companion object{
        val ANGLE_SPAN=0.375f
    }

    private lateinit var rthread: RotateThread
    var mRenderer=SceneRenderer()
    lateinit var tle:Triangle
    init{
        setEGLContextClientVersion(3)
        setRenderer(mRenderer)
        renderMode=GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    inner class SceneRenderer:GLSurfaceView.Renderer{
        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)
            tle.drawSelf()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            val ratio=width/height.toFloat()
            Matrix.frustumM(Triangle.mProjMatrix,0,-ratio,ratio,-1.0f,1.0f,1.0f,10.0f)
            Matrix.setLookAtM(Triangle.mVMatrix,0,0f,0f,3.0f,0f,0f,0f,0f,1.0f,0.0f)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(0.0f,0.0f,0.0f,1.0f)
            tle=Triangle(this@TestView)
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            rthread=RotateThread()
            rthread.start()
        }
    }

    inner class RotateThread:Thread(){
        var flag=true
        override fun run() {
            while (flag){
                mRenderer.tle.xAngle=mRenderer.tle.xAngle+ANGLE_SPAN
                try {
                    Thread.sleep(20)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}
