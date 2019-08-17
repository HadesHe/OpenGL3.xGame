package com.example.glsurfaceviews

import android.content.Context
import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.shapes.Cube
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class TanslateView(context: Context) :BaseOpenGl3SurfaceView(context) {
    override fun getRender(): Renderer {
        return TranslateRender()
    }

    inner class TranslateRender : Renderer {
        private lateinit var mCube: Cube

        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT
            or GLES30.GL_COLOR_BUFFER_BIT)
            MatrixState.pushMatrix()
            mCube.drawself()
            MatrixState.popMatrix()

            MatrixState.pushMatrix()
            MatrixState.translate(0.5f,0.3f,-0.3f)
            mCube.drawself()
            MatrixState.popMatrix()

        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0,0,width, height)
            val ratio=width/height.toFloat()
            MatrixState.setProjectFrustum(-ratio*0.8f,
                ratio*1.2f,-1f,1f,20f,100f)
            MatrixState.setCamera(-16f,8f,45f,0f,0f,0f,0f,1.0f,0.0f)
            MatrixState.setInitStack()
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(1.0f,1.0f,1.0f,1.0f)
            mCube= Cube(this@TanslateView)
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            GLES30.glEnable(GLES30.GL_CULL_FACE)//背面剪裁
        }

    }

}
