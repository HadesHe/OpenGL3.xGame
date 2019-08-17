package com.example.shapes

import android.opengl.GLES30
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import com.example.glsurfaceviews.TanslateView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Cube(val mv: TanslateView) {

    private val UNIT=0.25f
    private var muMVPMatrixHandle: Int=0
    private var maColorHandle: Int=0
    private var maPositionHandle: Int=0
    private var mProgram: Int=0
    private var mFragmentShader: String?=null
    private var mVertexShader: String?=null
    private lateinit var mColorBuffer: FloatBuffer
    private lateinit var mVertexBuffer: FloatBuffer
    private var vCount: Int=0

    init {
        initVertexData()
        initShader(mv)
    }

    private fun initShader(mv: TanslateView) {
        mVertexShader=ShaderUtil.loadFromAssetsFile("translatevertex.glsl",mv.resources)
        mFragmentShader=ShaderUtil.loadFromAssetsFile("translatefrag.glsl",mv.resources)

        mProgram=ShaderUtil.createProgram(mVertexShader!!,mFragmentShader!!)

        maPositionHandle=GLES30.glGetAttribLocation(mProgram,"aPosition")
        maColorHandle=GLES30.glGetAttribLocation(mProgram,"aColor")
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")
    }

    private fun initVertexData() {
         vCount=12*6

         val vertices= floatArrayOf(
             //ǰ��
             0.0f,0.0f,1.0f*UNIT,
             1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             -1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             0.0f,0.0f,1.0f*UNIT,
             -1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             -1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             0.0f,0.0f,1.0f*UNIT,
             -1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             0.0f,0.0f,1.0f*UNIT,
             1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             //����
             0.0f,0.0f,-1.0f*UNIT,
             1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             0.0f,0.0f,-1.0f*UNIT,
             1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             -1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             0.0f,0.0f,-1.0f*UNIT,
             -1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             -1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             0.0f,0.0f,-1.0f*UNIT,
             -1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             //����
             -1.0f*UNIT,0.0f,0.0f,
             -1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             -1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             -1.0f*UNIT,0.0f,0.0f,
             -1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             -1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             -1.0f*UNIT,0.0f,0.0f,
             -1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             -1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             -1.0f*UNIT,0.0f,0.0f,
             -1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             -1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             //����
             1.0f*UNIT,0.0f,0.0f,
             1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             1.0f*UNIT,0.0f,0.0f,
             1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             1.0f*UNIT,0.0f,0.0f,
             1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             1.0f*UNIT,0.0f,0.0f,
             1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             //����
             0.0f,1.0f*UNIT,0.0f,
             1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             0.0f,1.0f*UNIT,0.0f,
             1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             -1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             0.0f,1.0f*UNIT,0.0f,
             -1.0f*UNIT,1.0f*UNIT,-1.0f*UNIT,
             -1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             0.0f,1.0f*UNIT,0.0f,
             -1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             1.0f*UNIT,1.0f*UNIT,1.0f*UNIT,
             //����
             0.0f,-1.0f*UNIT,0.0f,
             1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             -1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             0f,-1.0f*UNIT,0f,
             -1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT,
             -1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             0f,-1.0f*UNIT,0f,
             -1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             0f,-1.0f*UNIT,0f,
             1.0f*UNIT,-1.0f*UNIT,-1.0f*UNIT,
             1.0f*UNIT,-1.0f*UNIT,1.0f*UNIT
         )

        mVertexBuffer=ByteBuffer.allocateDirect(vertices.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mVertexBuffer.put(vertices)
        mVertexBuffer.position(0)

        val colors= floatArrayOf(
            //ǰ��
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,0f,0f,0f,
            1f,0f,0f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,0f,0f,0f,
            1f,0f,0f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,0f,0f,0f,
            1f,0f,0f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,0f,0f,0f,
            1f,0f,0f,0f,
            //����
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,0f,1f,0f,
            0f,0f,1f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,0f,1f,0f,
            0f,0f,1f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,0f,1f,0f,
            0f,0f,1f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,0f,1f,0f,
            0f,0f,1f,0f,
            //����
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,0f,1f,0f,
            1f,0f,1f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,0f,1f,0f,
            1f,0f,1f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,0f,1f,0f,
            1f,0f,1f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,0f,1f,0f,
            1f,0f,1f,0f,
            //����
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,1f,0f,0f,
            1f,1f,0f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,1f,0f,0f,
            1f,1f,0f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,1f,0f,0f,
            1f,1f,0f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            1f,1f,0f,0f,
            1f,1f,0f,0f,
            //����
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,1f,0f,0f,
            0f,1f,0f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,1f,0f,0f,
            0f,1f,0f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,1f,0f,0f,
            0f,1f,0f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,1f,0f,0f,
            0f,1f,0f,0f,
            //����
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,1f,1f,0f,
            0f,1f,1f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,1f,1f,0f,
            0f,1f,1f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,1f,1f,0f,
            0f,1f,1f,0f,
            1f,1f,1f,0f,//�м�Ϊ��ɫ
            0f,1f,1f,0f,
            0f,1f,1f,0f
        )

        mColorBuffer=ByteBuffer.allocateDirect(colors.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mColorBuffer.put(colors)
        mColorBuffer.position(0)

    }

    fun drawself(){
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)

        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,false,3*4,mVertexBuffer)
        GLES30.glVertexAttribPointer(maColorHandle,4,GLES30.GL_FLOAT,false,4*4,mColorBuffer)

        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maColorHandle)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount)

    }
}