package com.example.baseopengl.abstracts

import android.util.Log
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.ShaderUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

abstract class AbstractShape(val mv: BaseOpenGl3SurfaceView) {

    protected var mProgram: Int=0
    protected lateinit var mVertexBuffer: FloatBuffer
    private val TAG=AbstractShape::class.java.simpleName

    fun initData(){
        initVertexBuffer()

        //是否需要第二组顶点坐标
        if(needNormal()){
            initNormalVertexBuffer()

        }
        initProgram()

        initLocationHandle()
    }

    /**
     * 分配第二组顶点 buffer
     * needNormal 为 true 时有效,默认不分配
     */
    open protected fun initNormalVertexBuffer() {


    }

    open protected fun needNormal(): Boolean =false

    private fun initVertexBuffer() {
        mVertexBuffer=ByteBuffer.allocateDirect(getVertice().size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mVertexBuffer.put(getVertice())
        mVertexBuffer.position(0)
    }

    private fun initProgram() {
        if(!checkAssetFileExist(getVertexName())|| !checkAssetFileExist(getFragName())){
            Log.d(TAG,"${getVertexName()} or ${getFragName()} not exist")
            return
        }

        val mVertexShader= ShaderUtil.loadFromAssetsFile(getVertexName(), mv.resources)!!
        val mFragShader= ShaderUtil.loadFromAssetsFile(getFragName(), mv.resources)!!
        mProgram= ShaderUtil.createProgram(mVertexShader, mFragShader)
    }

    private fun checkAssetFileExist(vertexName: String):Boolean {
        return mv.resources.assets.list("").contains(vertexName)
    }

    abstract fun getVertice():FloatArray
    abstract fun getVertexName(): String
    abstract fun getFragName(): String

    abstract fun initLocationHandle()
    abstract fun drawSelf(texId:Int)
}