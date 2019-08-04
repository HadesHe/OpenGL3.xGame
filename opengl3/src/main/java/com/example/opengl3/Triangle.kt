package com.example.opengl3

import android.opengl.GLES30
import android.opengl.Matrix
import com.example.baseopengl.ShaderUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Triangle(var testView: TestView) {


    private var mMVPMatrixHandle: Int=0
    private var mColorHandler: Int=0
    private var mPositionHandler: Int=0
    private var mProgram: Int=0
    private var mFragmentShader: String?=null
    private var mVertextShader: String?=null
    private lateinit var mColorBuffer: FloatBuffer
    private lateinit var mVertextBuffer: FloatBuffer
    var xAngle=0f



    companion object{
        val vCount:Int=3
        val UNIT_SIZE=0.2f
        val vertices= floatArrayOf(
            (-4* UNIT_SIZE),0f,0f,
            0f,-4* UNIT_SIZE,0f,
            4* UNIT_SIZE,0f,0f
        )
        val colors= floatArrayOf(
            1.0f,1.0f,1.0f,0f,
            0f,0f,1.0f,0f,
            0f,1f,0f,0f
        )

        var mProjectMatrix=FloatArray(16)
        var mVMatrix=FloatArray(16)
        lateinit var mMVPMatrix:FloatArray
        var mMMatrix=FloatArray(16)


        fun getFinalMatrix(spec:FloatArray): FloatArray {
            mMVPMatrix= FloatArray(16)
            Matrix.multiplyMM(mMVPMatrix,0, mVMatrix,0,spec,0)
            Matrix.multiplyMM(mMVPMatrix,0, mProjectMatrix,0, mMVPMatrix,0)
            return mMVPMatrix

        }
    }

    init{
        initVertextData()
        initShader(testView)
    }

    private fun initShader(view: TestView) {
        mVertextShader=ShaderUtil.loadFromAssetsFile("vertex.glsl",view.resources)
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.glsl",view.resources)

        mProgram = ShaderUtil.createProgram(mVertextShader!!, mFragmentShader!!)

        mPositionHandler= GLES30.glGetAttribLocation(mProgram,"aPosition")
        mColorHandler=GLES30.glGetAttribLocation(mProgram,"aColor")
        mMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")
    }

    private fun initVertextData() {
        mVertextBuffer=ByteBuffer.allocateDirect(vertices.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()

        mVertextBuffer.put(vertices)
        mVertextBuffer.position(0)

        mColorBuffer=ByteBuffer.allocateDirect(colors.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()

        mColorBuffer.put(colors)
        mColorBuffer.position(0)
    }

    fun drawSelf(){
        GLES30.glUseProgram(mProgram)
        Matrix.setRotateM(mMMatrix,0,0f,0f,1.0f,0f)
        Matrix.translateM(mMMatrix,0,0f,0f,1f)
        Matrix.rotateM(mMMatrix,0,xAngle,1f,0f,0f)

        GLES30.glUniformMatrix4fv(mMVPMatrixHandle,1,false, getFinalMatrix(mMMatrix),0)

        GLES30.glVertexAttribPointer(
            mPositionHandler,3,GLES30.GL_FLOAT,false,3*4,mVertextBuffer
        )
        GLES30.glEnableVertexAttribArray(mPositionHandler)
        GLES30.glEnableVertexAttribArray(mColorHandler)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0, vCount)

    }


}
