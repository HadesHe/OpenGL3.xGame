package com.example.baseopengl.shapes

import com.example.baseopengl.BaseOpenGl3SurfaceView

abstract class BaseColorRect(val mv:BaseOpenGl3SurfaceView,val unitSize:Float,val colorIn:FloatArray) {
    init {
        initVertexData(unitSize,colorIn)
        initShader(mv)
    }

    abstract fun initShader(mv: BaseOpenGl3SurfaceView)

    abstract fun initVertexData(unitSize: Float, colorIn: FloatArray)

    abstract fun drawSelf()

}