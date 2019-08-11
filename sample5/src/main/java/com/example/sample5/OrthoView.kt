package com.example.sample5

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import com.example.baseopengl.MatrixState
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OrthoView(context:Context) :GLSurfaceView(context){

    private val TOUCH_SCALE_FACTOR=180f/320
    private var mPreviousX=0
    private var mPreviousY=0
    private var mRenderer: OrthoViewRenderer
    //正交视图与透视视图切换
    private var useOrtho=true

    init {
        setEGLContextClientVersion(3)
        mRenderer=OrthoViewRenderer()
        setRenderer(mRenderer)
        renderMode= RENDERMODE_CONTINUOUSLY
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var y=event?.y!!
        var x=event?.x!!
        event?.let {
            when(it.action){
                MotionEvent.ACTION_MOVE->{
                    var dy=y - mPreviousY
                    var dx=x - mPreviousX

                    mRenderer.ha.forEach {
                        it.yAngle+=dx*TOUCH_SCALE_FACTOR
                        it.xAngle+=dy*TOUCH_SCALE_FACTOR
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    inner class OrthoViewRenderer:Renderer{

        var ha:Array<SixPointedStar> = Array(6,{
            i->
            SixPointedStar(this@OrthoView,0.2f,0.5f,-0.3f*i)
        })
        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)
            ha.forEachIndexed { index, sixPointedStar ->
                if(useOrtho) {
                    sixPointedStar.drawSelf(0.05f*index)
                }else{
                    sixPointedStar.drawSelf(0.2f*index)

                }
            }
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0,0,width, height)
            var ratio=width/height.toFloat()

            if(useOrtho) {
                MatrixState.setProjectOrtho(-ratio, ratio, -1f, 1f, 1f, 10f)
                MatrixState.setCamera(0f,0f,3f,0f,0f,0f,0f,1.0f,0.0f)
            }else{
                MatrixState.setProjectFrustum(-ratio*0.4f,ratio*0.4f,-1*0.4f,1*0.4f,1f,50f)
                MatrixState.setCamera(0f,0f,6f,0f,0f,0f,0f,1.0f,0.0f)
            }
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(1.0f,1.0f,1.0f,1.0f)

            for(i in 0 until 6){
                if(useOrtho) {
                    ha[i] = SixPointedStar(this@OrthoView, 0.2f, 0.5f, -0.3f * i)
                }else{
                    ha[i] = SixPointedStar(this@OrthoView, 0.4f, 1.0f, -1.0f * i)
                }
            }

            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
        }

    }

}