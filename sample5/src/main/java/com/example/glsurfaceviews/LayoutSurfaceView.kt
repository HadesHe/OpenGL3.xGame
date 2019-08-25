package com.example.glsurfaceviews

import android.content.Context
import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.shapes.LayoutCircle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class LayoutSurfaceView(context: Context) :BaseOpenGl3SurfaceView(context){
    override fun getRender(): Renderer {
        return LayoutRender()
    }

    inner class  LayoutRender:Renderer{
        private lateinit var circle: LayoutCircle

        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)

            MatrixState.pushMatrix()
            MatrixState.pushMatrix()
            MatrixState.translate(-1.3f,0f,0f)

            MatrixState.popMatrix()
            MatrixState.pushMatrix()
            MatrixState.translate(1.3f,0f,0f)
            circle.drawSelf()
            MatrixState.popMatrix()
            MatrixState.popMatrix()

        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0,0,width, height)
            val ratio=width/height.toFloat()
            MatrixState.setProjectFrustum(-ratio,ratio,-1.0f,1.0f,20f,100f)
            MatrixState.setCamera(-50f,8f,40f,0f,0f,0f,0f,1.0f,0f)
            MatrixState.setInitStack()
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(0.5f,0.5f,0.5f,1.0f)
            circle=LayoutCircle(this@LayoutSurfaceView)
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            GLES30.glEnable(GLES30.GL_CULL_FACE)
        }

    }

}