package com.example.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import com.example.baseopengl.shapes.BaseColorRect
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class NearColorRect(mv:BaseOpenGl3SurfaceView,unitSize: Float,colorIn: FloatArray) : BaseColorRect(mv,unitSize,colorIn) {
    private var muMMatrixHandle: Int=0
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
        GLES30.glUseProgram(mProgram)
        //�����ձ任��������Ⱦ����
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0)
        //��ƽ�ơ���ת�任��������Ⱦ����
        GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0)
        //������λ�����ݴ�����Ⱦ����
        GLES30.glVertexAttribPointer(
            maPositionHandle,
            3,
            GLES30.GL_FLOAT,
            false,
            3 * 4,
            mVertexBuffer
        )
        //��������ɫ���ݴ�����Ⱦ����
        GLES30.glVertexAttribPointer(
            maColorHandle,
            4,
            GLES30.GL_FLOAT,
            false,
            4 * 4,
            mColorBuffer
        )
        //���ö���λ����������
        GLES30.glEnableVertexAttribArray(maPositionHandle)
        //���ö�����ɫ��������
        GLES30.glEnableVertexAttribArray(maColorHandle)
        //������ɫ����
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, vCount)
    }

    override fun initVertexData(unitSize: Float, colorIn: FloatArray) {
        vCount = 6
        val vertices = floatArrayOf(
            0f, 0f, 0f,
            unitSize, unitSize, 0f,
            -unitSize, unitSize, 0f,
            -unitSize, -unitSize, 0f,
            unitSize, -unitSize, 0f,
            unitSize, unitSize, 0f
        )

        mVertexBuffer = ByteBuffer.allocateDirect(vertices.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mVertexBuffer.put(vertices)
        mVertexBuffer.position(0)

        val colors= floatArrayOf(
            colorIn[0],colorIn[1],colorIn[2],colorIn[3],
            colorIn[0],colorIn[1],colorIn[2],colorIn[3],
            colorIn[0],colorIn[1],colorIn[2],colorIn[3],
            colorIn[0],colorIn[1],colorIn[2],colorIn[3],
            colorIn[0],colorIn[1],colorIn[2],colorIn[3],
            colorIn[0],colorIn[1],colorIn[2],colorIn[3]
        )

        mColorBuffer=ByteBuffer.allocateDirect(colors.size*4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mColorBuffer.put(colors)
        mColorBuffer.position(0)

    }
    override fun drawSelf() {
        mVertexShader = ShaderUtil.loadFromAssetsFile("beltvertex.glsl", mv.resources)!!
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader = ShaderUtil.loadFromAssetsFile("beltfrag.glsl", mv.resources)!!
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader)
        //��ȡ�����ж���λ����������id
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition")
        //��ȡ�����ж�����ɫ��������id
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor")
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix")
        //��ȡλ�á���ת�任��������id
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix")
    }

}
