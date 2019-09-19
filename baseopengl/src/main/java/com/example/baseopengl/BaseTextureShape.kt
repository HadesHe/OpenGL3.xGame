package com.example.baseopengl

abstract class BaseTextureShape(val mv: BaseOpenGl3SurfaceView) {

    fun initData(){
        initVertData()
        initShader(mv)
    }
    abstract fun initShader(mv: BaseOpenGl3SurfaceView)

    abstract fun initVertData()
}