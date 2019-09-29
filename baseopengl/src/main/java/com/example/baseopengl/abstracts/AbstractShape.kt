package com.example.baseopengl.abstracts

import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.ShaderUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

abstract class AbstractShape(val mv: BaseOpenGl3SurfaceView) {

    protected var mProgram: Int=0
    protected lateinit var mVertexBuffer: FloatBuffer

    fun initData(){
        initVertexBuffer()
        initProgram()

        initLocationHandle()
    }

    private fun initVertexBuffer() {
        mVertexBuffer=ByteBuffer.allocateDirect(getVertice().size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mVertexBuffer.put(getVertice())
        mVertexBuffer.position(0)
    }

    private fun initProgram() {
        val mVertexShader= ShaderUtil.loadFromAssetsFile(getVertexName(), mv.resources)!!
        val mFragShader= ShaderUtil.loadFromAssetsFile(getFragName(), mv.resources)!!
        mProgram= ShaderUtil.createProgram(mVertexShader, mFragShader)
    }

    abstract fun getVertice():FloatArray
    abstract fun getVertexName(): String
    abstract fun getFragName(): String

    abstract fun initLocationHandle()
    abstract fun drawSelf(texId:Int)
}