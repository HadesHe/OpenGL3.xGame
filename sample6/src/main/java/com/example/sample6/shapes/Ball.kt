package com.example.sample6.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.BaseShape
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import com.example.sample6.surfaceviews.BallSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.ArrayList

class Ball(mv: BallSurfaceView) {
    private var maLightLocationHandle: Int=0
    private var maNormalHandle: Int=0
    private var muMMatrixHandle: Int=0
    private lateinit var mNormalBuffer: FloatBuffer
    private var muRHandle: Int=0
    private var muMVPMatrixHandle: Int=0
    private var maPositionHandle: Int=0
    private var mProgram: Int=0
    private lateinit var mFragmentShader: String
    private lateinit var mVertextShader: String
    private lateinit var mVertexBuffer: FloatBuffer
    private var vCount: Int=0
    private val r=1f
    var xAngle=0f
    var yAngle=0f
    var zAngle=0f
    val UNIT_SIZE=1f

    init {
        initVertData()
        initShader(mv)
    }


     fun initShader(mv: BallSurfaceView) {
        mVertextShader=ShaderUtil.loadFromAssetsFile("ballvert.glsl",mv.resources)!!
        mFragmentShader=ShaderUtil.loadFromAssetsFile("ballfrag.glsl",mv.resources)!!
        mProgram=ShaderUtil.createProgram(mVertextShader,mFragmentShader)

        maPositionHandle=GLES30.glGetAttribLocation(mProgram,"aPosition")
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")
         muMMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMMatrix")
        muRHandle=GLES30.glGetUniformLocation(mProgram,"uR")
         maNormalHandle=GLES30.glGetAttribLocation(mProgram,"aNormal")
         maLightLocationHandle=GLES30.glGetUniformLocation(mProgram,"uLightLocation")
    }

    fun initVertData() {
        val alVertix = ArrayList<Float>()// ��Ŷ��������ArrayList
        val angleSpan = 10// ������е�λ�зֵĽǶ�
        var vAngle = -90
        while (vAngle < 90)
        // ��ֱ����angleSpan��һ��
        {
            var hAngle = 0
            while (hAngle <= 360)
            // ˮƽ����angleSpan��һ��
            {// ����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ�����
                val x0 = (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle.toDouble())) * Math.cos(
                    Math
                        .toRadians(hAngle.toDouble())
                )).toFloat()
                val y0 = (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle.toDouble())) * Math.sin(
                    Math
                        .toRadians(hAngle.toDouble())
                )) .toFloat()
                val z0 = (r * UNIT_SIZE * Math.sin(
                    Math
                        .toRadians(vAngle.toDouble())
                )) .toFloat()

                val x1 = (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle.toDouble())) * Math.cos(
                    Math
                        .toRadians((hAngle + angleSpan).toDouble())
                )) .toFloat()
                val y1 = (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle.toDouble())) * Math.sin(
                    Math
                        .toRadians((hAngle + angleSpan).toDouble())
                )) .toFloat()
                val z1 = (r * UNIT_SIZE * Math.sin(
                    Math
                        .toRadians(vAngle.toDouble())
                )) .toFloat()

                val x2 = (r * UNIT_SIZE
                        * Math.cos(Math.toRadians((vAngle + angleSpan).toDouble())) * Math
                    .cos(Math.toRadians((hAngle + angleSpan).toDouble()))) .toFloat()
                val y2 = (r * UNIT_SIZE
                        * Math.cos(Math.toRadians((vAngle + angleSpan).toDouble())) * Math
                    .sin(Math.toRadians((hAngle + angleSpan).toDouble()))) .toFloat()
                val z2 = (r * UNIT_SIZE * Math.sin(
                    Math
                        .toRadians((vAngle + angleSpan).toDouble())
                )) .toFloat()

                val x3 = (r * UNIT_SIZE
                        * Math.cos(Math.toRadians((vAngle + angleSpan).toDouble())) * Math
                    .cos(Math.toRadians(hAngle.toDouble()))) .toFloat()
                val y3 = (r * UNIT_SIZE
                        * Math.cos(Math.toRadians((vAngle + angleSpan).toDouble())) * Math
                    .sin(Math.toRadians(hAngle.toDouble()))) .toFloat()
                val z3 = (r * UNIT_SIZE * Math.sin(
                    Math
                        .toRadians((vAngle + angleSpan).toDouble())
                )) .toFloat()

                // �����������XYZ��������Ŷ��������ArrayList
                alVertix.add(x1)
                alVertix.add(y1)
                alVertix.add(z1)
                alVertix.add(x3)
                alVertix.add(y3)
                alVertix.add(z3)
                alVertix.add(x0)
                alVertix.add(y0)
                alVertix.add(z0)

                alVertix.add(x1)
                alVertix.add(y1)
                alVertix.add(z1)
                alVertix.add(x2)
                alVertix.add(y2)
                alVertix.add(z2)
                alVertix.add(x3)
                alVertix.add(y3)
                alVertix.add(z3)
                hAngle = hAngle + angleSpan
            }
            vAngle = vAngle + angleSpan
        }
        vCount = alVertix.size / 3// ���������Ϊ����ֵ������1/3����Ϊһ��������3������
        val vertices=FloatArray(vCount*3)

        for (i in alVertix.indices) {
            vertices[i] = alVertix[i]
        }

        mVertexBuffer=ByteBuffer.allocateDirect(vertices.size*4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()

        mVertexBuffer.put(vertices)
        mVertexBuffer.position(0)

        mNormalBuffer=ByteBuffer.allocateDirect(vertices.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mNormalBuffer.put(vertices)
        mNormalBuffer.position(0)


    }

    fun drawSelf() {
        MatrixState.rotate(xAngle,1f,0f,0f)
        MatrixState.rotate(yAngle,0f,1f,0f)
        MatrixState.rotate(zAngle,0f,0f,1f)

        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)
        GLES30.glUniformMatrix4fv(muMMatrixHandle,1,false,MatrixState.getMMatrix(),0)
        GLES30.glUniform1f(muRHandle,r*UNIT_SIZE)
        GLES30.glUniform3fv(maLightLocationHandle,1,MatrixState.lightPositionFB)
        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,
            false,3*4,mVertexBuffer)
        GLES30.glVertexAttribPointer(maNormalHandle,3,GLES30.GL_FLOAT,false,
            3*4,mNormalBuffer)
        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maNormalHandle)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount)
    }

}