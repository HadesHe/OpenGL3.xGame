package com.example.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class DrawElementCircle(mv:BaseOpenGl3SurfaceView) :Circle(mv){

    private lateinit var mIndexBuffer: Buffer

    override fun initVertData() {
        val n=8
        vCount=n+2

        val angleSpan=360f/n
        val vertices=FloatArray(vCount*3)

        var count = 0
        //��һ�����������
        vertices[count++] = 0f
        vertices[count++] = 0f
        vertices[count++] = 0f
        var angdeg = 0f
        while (Math.ceil(angdeg.toDouble()) <= 360) {//ѭ�������������������
            val angrad = Math.toRadians(angdeg.toDouble())//��ǰ����
            //��ǰ��
            vertices[count++] = (-UNIT_SIZE * Math.sin(angrad)).toFloat()
            vertices[count++] = (UNIT_SIZE * Math.cos(angrad)).toFloat()
            vertices[count++] = 0f//����z����
            angdeg += angleSpan
        }

        mVertexBuffer=ByteBuffer.allocateDirect(vertices.size*4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mVertexBuffer.put(vertices)
        mVertexBuffer.position(0)

        count = 0
        val colors = FloatArray(vCount * 4)
        //��һ���������ɫ:��ɫ
        colors[count++] = 1f
        colors[count++] = 1f
        colors[count++] = 1f
        colors[count++] = 0f
        //ʣ�ඥ�����ɫ:��ɫ
        var i = 4
        while (i < colors.size) {
            colors[count++] = 0f
            colors[count++] = 1f
            colors[count++] = 0f
            colors[count++] = 0f
            i += 4
        }

        mColorBuffer=ByteBuffer.allocateDirect(colors.size*4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mColorBuffer.put(colors)
        mColorBuffer.position(0)
        val incides= byteArrayOf(
            0,1,2,
            0,2,3,
            0,3,4,
            0,4,5,
            0,5,6,
            0,6,7,
            0,7,8,
            0,8,1
        )

        mIndexBuffer=ByteBuffer.allocateDirect(incides.size)
            .put(incides)
            .position(0)
    }

    override fun drawSelf() {
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,
            MatrixState.getFinalMatrix(),0)
        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,
            false,3*4,mVertexBuffer)
        GLES30.glVertexAttribPointer(maColorHandle,4,GLES30.GL_FLOAT,false,4*4,mColorBuffer)
        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maColorHandle)
        GLES30.glDrawElements(GLES30.GL_TRIANGLES,10*3,GLES30.GL_UNSIGNED_BYTE,mIndexBuffer)
    }

    override fun drawSelf(start: Int, end: Int) {
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,
            MatrixState.getFinalMatrix(),0)
        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,
            false,3*4,mVertexBuffer)
        GLES30.glVertexAttribPointer(maColorHandle,4,GLES30.GL_FLOAT,false,4*4,mColorBuffer)
        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maColorHandle)
        mIndexBuffer.position(start)
        GLES30.glDrawRangeElements(
            GLES30.GL_TRIANGLES,
            0,8,8*3,GLES30.GL_UNSIGNED_BYTE,mIndexBuffer
        )
    }

}