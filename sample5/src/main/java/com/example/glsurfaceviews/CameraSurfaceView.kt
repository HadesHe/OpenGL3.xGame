package com.example.glsurfaceviews

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import com.example.baseopengl.MatrixState
import com.example.shapes.CameraCube
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CameraSurfaceView(context:Context) :GLSurfaceView(context){

    companion object{
        private val TOUCH_SCALE_FACTOR=180/320.toFloat()
    }

    private var mRender: CameraRender
    private var yAngle: Float=0f
    private var mPreviousX: Float = 0.0f
    var ratio: Float=1f

    init {
        setEGLContextClientVersion(3)
        mRender=CameraRender()
        setRenderer(mRender)
        renderMode=GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x=event?.x
        when(event?.action){
            MotionEvent.ACTION_MOVE->{
                val dx=x!! - mPreviousX
                yAngle+=dx*TOUCH_SCALE_FACTOR
            }
        }
        mPreviousX=x!!
        return true
    }

    inner class CameraRender:GLSurfaceView.Renderer{
        private lateinit var cube: CameraCube

        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)
            MatrixState.pushMatrix()
            MatrixState.rotate(yAngle,0f,1f,0f)

            MatrixState.pushMatrix()
            MatrixState.translate(-3f,0f,0f)
            MatrixState.rotate(60f,0f,1f,0f)
            cube.drawSelf()
            MatrixState.popMatrix()

            MatrixState.pushMatrix()
            MatrixState.translate(3f,0f,0f)
            MatrixState.rotate(-60f,0f,1f,0f)
            cube.drawSelf()
            MatrixState.popMatrix()
            MatrixState.popMatrix()

        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0,0,width,height)
            ratio=width/height.toFloat()
            MatrixState.setProjectFrustum(-ratio*0.7f,ratio*0.7f,-0.7f,0.7f,1f,10f)
            MatrixState.setCamera(0f,0.5f,4f,0f,0f,0f,0f,1f,0f)
            MatrixState.setInitStack()
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(0.5f,0.5f,0.5f,1.0f)
            cube=CameraCube(this@CameraSurfaceView)
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            GLES30.glEnable(GLES30.GL_CULL_FACE)
        }
    }




}