package com.example.shapes

import android.opengl.GLES30
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import com.example.glsurfaceviews.CameraSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class ColorRect(val mv: CameraSurfaceView) {
    private val UNIT_SIZE=1f
    internal var mProgram: Int = 0//�Զ�����Ⱦ������ɫ������id
    internal var muMVPMatrixHandle: Int = 0//�ܱ任��������
    internal var muMMatrixHandle: Int = 0//λ�á���ת�任��������
    internal var maPositionHandle: Int = 0 //����λ����������
    internal var maColorHandle: Int = 0 //������ɫ��������
    internal lateinit var mVertexShader: String//������ɫ������ű�
    internal lateinit var mFragmentShader: String//ƬԪ��ɫ������ű�

    internal lateinit var mVertexBuffer: FloatBuffer//�����������ݻ���
    internal lateinit var mColorBuffer: FloatBuffer//������ɫ���ݻ���
    internal var vCount = 0

    init {
        initVertexData()
        initShader(mv)

    }

    //��ʼ��������������ɫ���ݵķ���
    fun initVertexData() {
        //�����������ݵĳ�ʼ��================begin============================
        vCount = 6
        val vertices = floatArrayOf(
            0f,
            0f,
            0f,
            UNIT_SIZE,
            UNIT_SIZE,
            0f,
            -UNIT_SIZE,
            UNIT_SIZE,
            0f,
            -UNIT_SIZE,
            -UNIT_SIZE,
            0f,
            UNIT_SIZE,
            -UNIT_SIZE,
            0f,
            UNIT_SIZE,
            UNIT_SIZE,
            0f
        )

        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        val vbb = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder())//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer()//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices)//�򻺳����з��붥����������
        mVertexBuffer.position(0)//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================

        //������ɫ���ݵĳ�ʼ��================begin============================
        val colors = floatArrayOf(
            1f,
            1f,
            1f,
            0f,
            0f,
            0f,
            1f,
            0f,
            0f,
            0f,
            1f,
            0f,
            0f,
            0f,
            1f,
            0f,
            0f,
            0f,
            1f,
            0f,
            0f,
            0f,
            1f,
            0f
        )//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
        //����������ɫ���ݻ���
        val cbb = ByteBuffer.allocateDirect(colors.size * 4)
        cbb.order(ByteOrder.nativeOrder())//�����ֽ�˳��
        mColorBuffer = cbb.asFloatBuffer()//ת��ΪFloat�ͻ���
        mColorBuffer.put(colors)//�򻺳����з��붥����ɫ����
        mColorBuffer.position(0)//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //������ɫ���ݵĳ�ʼ��================end============================

    }

    //��ʼ����ɫ��
    fun initShader(mv: CameraSurfaceView) {
        //���ض�����ɫ���Ľű�����
        mVertexShader = ShaderUtil.loadFromAssetsFile("cameravert.glsl", mv.getResources())!!
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader = ShaderUtil.loadFromAssetsFile("camerafrag.glsl", mv.getResources())!!
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

    fun drawSelf() {
        //ָ��ʹ��ĳ����ɫ������
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
}
