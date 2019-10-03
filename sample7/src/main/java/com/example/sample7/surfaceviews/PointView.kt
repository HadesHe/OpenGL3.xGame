package com.example.sample7.surfaceviews

import android.content.Context
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.abstracts.AbstractRender
import com.example.sample7.R
import com.example.sample7.shapes.Points

class PointView(context: Context) :BaseOpenGl3SurfaceView(context){
    override fun getRender(): Renderer {
        return PointRender()
    }

    inner class PointRender: AbstractRender() {
        private var texId: Int=0
        private lateinit var point: Points

        override fun onRenderCreated() {
            point=Points(this@PointView)
            point.initData()
            texId=initTexture(context, R.raw.fp)

        }

        override fun onRenderChanged() {
            val ratio=width/height.toFloat()
            MatrixState.setProjectFrustum(-ratio,ratio,-1f,1f,20f,100f)
            MatrixState.setCamera(0f,8f,30f,0f,0f,0f,0f,1f,0f)
            MatrixState.setInitStack()
        }

        override fun onRenderDrawed() {
            MatrixState.pushMatrix()
            point.drawSelf(texId)
            MatrixState.popMatrix()
        }

    }

}