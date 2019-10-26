package com.example.sample8.glsurfaceviews

import android.content.Context
import android.view.MotionEvent
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.abstracts.AbstractRender
import com.example.sample8.R
import com.example.sample8.shapes.Cylinder

class CylinderView(context:Context) :BaseOpenGl3SurfaceView(context){

    private var mPreviousX=0f
    private var mPreviousY=0f
    private val TOUCH_SCALE_FACTOR=180/320.toFloat()
    private var mRenderer:CylinderRender?=null


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val y=it.y
            val x=it.x

            when(it.action){
                MotionEvent.ACTION_MOVE->{
                    val dy=y-mPreviousY
                    val dx=x-mPreviousX
                    mRenderer?.cyclinder?.yAngel = mRenderer?.cyclinder?.yAngel?.plus(dx*TOUCH_SCALE_FACTOR)!!
                    mRenderer?.cyclinder?.xAngel = mRenderer?.cyclinder?.xAngel?.plus(dy*TOUCH_SCALE_FACTOR)!!

                }
            }
            mPreviousX=x
            mPreviousY=y
        }
        return true
    }
    override fun getRender(): Renderer {
        mRenderer=CylinderRender()
        return mRenderer!!
    }

    inner class CylinderRender:AbstractRender(){
        private var textureId1=0
        var cyclinder: Cylinder?=null
        private var textureId=0

        override fun onRenderCreated() {
            MatrixState.setInitStack()
            textureId=initTexture(context, R.drawable.android_robot0)
            textureId1=initTexture(context, R.drawable.yuner)

            cyclinder=Cylinder(this@CylinderView,1f,1.2f,3.9f,36,textureId1,textureId1,textureId)
        }

        override fun onRenderChanged(width: Int, height: Int) {
            val ratio=width/height.toFloat()
            MatrixState.setProjectFrustum(-ratio,ratio,-1f,1f,4f,100f)
            MatrixState.setCamera(0f,0f,8.0f,0f,0f,0f,0f,1f,0f)
            MatrixState.setLightLocation(10f,0f,-10f)


        }

        override fun onRenderDrawed() {
            MatrixState.pushMatrix()
            MatrixState.translate(0f,0f,-10f)
            cyclinder?.drawSelf()
            MatrixState.popMatrix()
        }

    }

}