package com.example.sample6.surfaceviews

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.sample6.shapes.Ball
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class BallSurfaceView(context: Context) :GLSurfaceView(context){
    private var mRender: BallRender
    private val TOUCH_SCALE_FACTOR=180/320.toFloat()
    private var mPreviousX=0f
    private var mPreviousY=0f
    private lateinit var ball: Ball

    init {
        setEGLContextClientVersion(3)
        mRender=BallRender()
        setRenderer(mRender)
        renderMode=GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val y=event?.y!!
        val x=event?.x!!
        when(event?.action){
            MotionEvent.ACTION_MOVE ->{
                val dy=y-mPreviousY
                val dx=x-mPreviousX
                ball.yAngle+=dx*TOUCH_SCALE_FACTOR
                ball.xAngle=dy*TOUCH_SCALE_FACTOR
            }

        }
        mPreviousX=x
        mPreviousY=y
        return true
    }

    inner class BallRender:Renderer{
        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)
            MatrixState.pushMatrix()
            MatrixState.pushMatrix()
            ball.drawSelf()
            MatrixState.popMatrix()
            MatrixState.popMatrix()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0,0,width, height)
            val ratio=width/height.toFloat()
            MatrixState.setProjectFrustum(-ratio,ratio,-1f,1f,20f,100f)
            MatrixState.setCamera(0f,0f,30f,0f,0f,0f,0f,1f,0f)
//            MatrixState.setCamera(-32f,16f,90f,0f,0f,0f,0f,1.0f,0.0f)
            MatrixState.setInitStack()
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(1f,1f,1f,1f)
            ball= Ball(this@BallSurfaceView)
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            GLES30.glEnable(GLES30.GL_CULL_FACE)
        }

    }

}