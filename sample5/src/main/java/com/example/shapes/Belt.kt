package com.example.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.BaseShape
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Belt(mv:BaseOpenGl3SurfaceView):BaseShape(mv){
    private var muMVPMatrixHandle: Int=0
    private var maColorHandle: Int=0
    private var maPositionHandle: Int=0
    private var mProgram: Int=0
    private lateinit var mFragmentShader: String
    private lateinit var mVertexShader: String
    private lateinit var mColorBuffer: FloatBuffer
    private lateinit var mVertexBuffer: FloatBuffer
    private var vCount: Int=0

    override fun initShader(mv: BaseOpenGl3SurfaceView) {
        mVertexShader=ShaderUtil.loadFromAssetsFile("beltvert.glsl",mv.resources)!!
        mFragmentShader=ShaderUtil.loadFromAssetsFile("beltfrag.glsl",mv.resources)!!

        mProgram=ShaderUtil.createProgram(mVertexShader,mFragmentShader)
        maPositionHandle=GLES30.glGetAttribLocation(mProgram,"aPosition")
        maColorHandle=GLES30.glGetAttribLocation(mProgram,"aColor")
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"mMVPMatrix")


    }

    override fun initVertData() {
        val n=6
        vCount=2*(n+1)
        var angleBegin=-90f
        var angleEnd=90f
        var angleSpan=(angleEnd-angleBegin)/n

        val vertices=FloatArray(vCount*3)


        var count=0
        var angdeg = angleBegin
        while (angdeg <= angleEnd) {
            val angrad = Math.toRadians(angdeg.toDouble())//��ǰ����
            //��ǰ��
            vertices[count++] = (-0.6f * UNIT_SIZE * Math.sin(angrad)) as Float//����x����
            vertices[count++] = (0.6f * UNIT_SIZE * Math.cos(angrad)) as Float//����y����
            vertices[count++] = 0f//����z����
            //��ǰ��
            vertices[count++] = (-UNIT_SIZE * Math.sin(angrad)) as Float//����x����
            vertices[count++] = (UNIT_SIZE * Math.cos(angrad)) as Float//����y����
            vertices[count++] = 0f//����z����
            angdeg += angleSpan
        }

        mVertexBuffer=ByteBuffer.allocateDirect(vertices.size*4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mVertexBuffer.put(vertices)
        mVertexBuffer.position(0)

        count=0
        var colors=FloatArray(vCount*4)
        for (i in 0 until colors.size step 8 ){
            colors[count++] = 1f
            colors[count++] = 1f
            colors[count++] = 1f
            colors[count++] = 0f

            colors[count++] = 0f
            colors[count++] = 1f
            colors[count++] = 1f
            colors[count++] = 0f
        }

        mColorBuffer=ByteBuffer.allocateDirect(colors.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mColorBuffer.put(colors)
        mColorBuffer.position(0)
    }

    override fun drawSelf() {
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)
        GLES30.glVertexAttribPointer(
            maPositionHandle,
            3,
            GLES30.GL_FLOAT,
            false,
            3*4,
            mVertexBuffer
        )

        GLES30.glVertexAttribPointer(
            maColorHandle,
            4,
            GLES30.GL_FLOAT,
            false,
            4*4,
            mColorBuffer
        )
        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maColorHandle)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP,0,vCount)
    }


}
