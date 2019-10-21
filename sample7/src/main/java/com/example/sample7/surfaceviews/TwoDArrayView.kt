package com.example.sample7.surfaceviews

import android.content.Context
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.abstracts.AbstractRender
import com.example.sample7.R
import com.example.sample7.shapes.TwoDPoint

class TwoDArrayView(context: Context):BaseOpenGl3SurfaceView(context) {

    private val texResIdArray= intArrayOf(
        R.drawable.tex1,
        R.drawable.tex2,
        R.drawable.tex3,
        R.drawable.tex4,
        R.drawable.tex5,
        R.drawable.tex6,
        R.drawable.tex7,
        R.drawable.tex8,
        R.drawable.tex9,
        R.drawable.tex10,
        R.drawable.tex11,
        R.drawable.tex12,
        R.drawable.tex13,
        R.drawable.tex14,
        R.drawable.tex15,
        R.drawable.tex16

    )
    override fun getRender(): Renderer {
        return TwoDArrayRender()
    }

    inner class TwoDArrayRender : AbstractRender(){
        private var texId:Int=0
        private lateinit var point: TwoDPoint

        override fun onRenderCreated() {
            point=TwoDPoint(this@TwoDArrayView)
            point.initData()
            texId=initTextureArray(context,texResIdArray,128,128)
        }

        override fun onRenderChanged(width: Int, height: Int) {
            val ratio=width/height.toFloat()
            MatrixState.setProjectFrustum(-ratio,ratio,-1f,1f,20f,100f)
            MatrixState.setCamera(0f,8f,30f,0f,0f,0f,0f,1f,0f)
            MatrixState.setInitStack()
        }

        override fun onRenderDrawed() {
            MatrixState.pushMatrix()
            MatrixState.pushMatrix()
            point.drawSelf(texId)
            MatrixState.popMatrix()
            MatrixState.popMatrix()
        }

    }
}