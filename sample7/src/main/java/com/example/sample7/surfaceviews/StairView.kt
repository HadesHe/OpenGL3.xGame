package com.example.sample7.surfaceviews

import android.content.Context
import android.view.MotionEvent
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.abstracts.AbstractRender
import com.example.sample7.shapes.Stairs

class StairView( context: Context) :BaseOpenGl3SurfaceView(context){
    private val TOUCH_SCALE_FACTOR=180/320.toFloat()
    private var mPreviousX=0f
    private var mPreviousY=0f
    private lateinit var mRender:StairRender

    override fun getRender(): Renderer {
        mRender=StairRender()
        return mRender
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event?.let {
            val y=it.y
            val x=it.x
            when(it.action){
                MotionEvent.ACTION_MOVE->{
                    val dy=y-mPreviousY
                    var dx=x-mPreviousX
                    mRender.yAngle+=dx*TOUCH_SCALE_FACTOR
                    mRender.xAngle+=dy*TOUCH_SCALE_FACTOR
                }
            }
            mPreviousX=x
            mPreviousY=y
            return true
        }
        return super.onTouchEvent(event)
    }

    inner class StairRender : AbstractRender() {

        var yAngle=0f
        var xAngle=0f

        private var textureId: Int=0
        private lateinit var lovo: Stairs

        override fun onRenderCreated() {
            MatrixState.setInitStack()
            MatrixState.setLightLocation(0f,100f,200f)

            lovo=Stairs(this@StairView)
            lovo.initData()

            val texData = byteArrayOf(
                //3d 1
                80,
                80,
                80,
                255.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte(),
                80,
                80,
                80,
                255.toByte(),
                //3d 2
                255.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte(),
                80,
                80,
                80,
                255.toByte(),
                80,
                80,
                80,
                255.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte()
            )
            textureId=init3DTexture(texData,2,2,2)

        }

        override fun onRenderChanged(width: Int, height: Int) {
            val ratio=width/height
            MatrixState.setProjectFrustum(-0.3f*ratio,0.3f*ratio,-0.3f,0.3f,2f,100f)
            MatrixState.setCamera(0f,0f,0f,0f,0f,-1f,0f,1f,0f)
        }


        override fun onRenderDrawed() {
            MatrixState.pushMatrix()
            MatrixState.translate(0f,-0.2f,-3f)
            MatrixState.rotate(yAngle,0f,1f,0f)
            MatrixState.rotate(xAngle,1f,0f,0f)
            lovo.drawSelf(textureId)
            MatrixState.popMatrix()
        }

    }

}