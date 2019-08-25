package com.example.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class LayoutCircle(mv:BaseOpenGl3SurfaceView) :Circle(mv){

    private var iCount: Int=0
    private lateinit var mIndexBuffer: Buffer

    override fun initVertData() {
        val n=10
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

        mVertexBuffer= ByteBuffer.allocateDirect(vertices.size*4)
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

        mColorBuffer= ByteBuffer.allocateDirect(colors.size*4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mColorBuffer.put(colors)
        mColorBuffer.position(0)

        iCount=vCount
        val incides=ByteArray(iCount)

        for(i in 0 until iCount){
            incides[i]=i.toByte()
        }

        mIndexBuffer= ByteBuffer.allocateDirect(incides.size)
            .put(incides)
            .position(0)
    }

    override fun initShader(mv: BaseOpenGl3SurfaceView) {
        mVertexShader=ShaderUtil.loadFromAssetsFile("layoutvertex.glsl",mv.resources)!!
        mFragmentShader=ShaderUtil.loadFromAssetsFile("layoutfrag.glsl",mv.resources)!!

        mProgram=ShaderUtil.createProgram(mVertexShader,mFragmentShader)
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")
        //将顶点位置属性变量索引与顶点着色器中变量名进行绑定
        GLES30.glBindAttribLocation(mProgram,1,"aPosition")
        GLES30.glBindAttribLocation(mProgram,2,"aColor")
    }

    override fun drawSelf() {
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)
        GLES30.glVertexAttribPointer(1,3,GLES30.GL_FLOAT,false,3*4,mVertexBuffer)
        GLES30.glVertexAttribPointer(2,4,GLES30.GL_FLOAT,false,4*4,mColorBuffer)

        GLES30.glEnableVertexAttribArray(1)
        GLES30.glEnableVertexAttribArray(2)

        GLES30.glDrawElements(GLES30.GL_TRIANGLE_FAN,iCount,
            GLES30.GL_UNSIGNED_BYTE,mIndexBuffer)
    }

}