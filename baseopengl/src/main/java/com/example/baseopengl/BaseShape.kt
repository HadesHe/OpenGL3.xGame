package com.example.baseopengl


abstract class BaseShape(val mv:BaseOpenGl3SurfaceView) {

    protected val UNIT_SIZE=1.0f
    init {
        initVertData()
        initShader(mv)
    }

    abstract fun initShader(mv: BaseOpenGl3SurfaceView)

    abstract fun initVertData()

    abstract fun drawSelf()

}