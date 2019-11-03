package com.example.sample8.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShapeUtil
import com.example.baseopengl.abstracts.AbstractShape
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class CircleL(mv: BaseOpenGl3SurfaceView, val scale: Float, val r: Float, val n: Int) : AbstractShape(mv) {
    private var maColorHandle: Int=0
    private var maNormalHandle: Int=0
    private var maPositionHandle: Int = 0
    private var muCameraHandle: Int = 0
    private var muLightLocationHandle: Int = 0
    private var muMMatrixHandle: Int = 0
    private var muMVPMatrixHandle: Int = 0
    private lateinit var mColorBuffer: FloatBuffer
    private lateinit var mNormalBuffer: FloatBuffer

    override fun getVertice(): FloatArray {
        return ShapeUtil.get3DCircleVertices(n * 9, n, scale * r)!!
    }

    override fun getVertexName(): String {
        return "vertex_color_light.glsl"
    }

    override fun getFragName(): String {
        return "frag_color_light.glsl"
    }

    override fun needNormal() = true

    override fun initNormalVertexBuffer() {
        val normals = FloatArray(n * 9) { i ->
            if (i % 3 == 2) 1f else 0f
        }

        mNormalBuffer = ByteBuffer.allocateDirect(normals.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mNormalBuffer.put(normals)
        mNormalBuffer.position(0)


        val colors = FloatArray(n * 3 * 4)


        var colorCount = 0
        var angdeg = 0f
        val angdegSpan = 360.0f / n    //顶角的度数
        while (Math.ceil(angdeg.toDouble()) < 360) {

            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f

            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f

            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            angdeg += angdegSpan
        }

        mColorBuffer = ByteBuffer.allocateDirect(colors.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mColorBuffer.put(colors)
        mColorBuffer.position(0)
    }


    //    in vec3 aPosition;  //顶点位置
//    in vec3 aNormal;    //顶点法向量
//    in vec4 aColor;    //顶点颜色
    override fun initLocationHandle() {
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix")
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix")
        muLightLocationHandle = GLES30.glGetUniformLocation(mProgram, "uLightLocation")
        muCameraHandle = GLES30.glGetUniformLocation(mProgram, "uCamera")
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition")
        maNormalHandle = GLES30.glGetAttribLocation(mProgram, "aNormal")
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor")
    }

    override fun drawSelf(texId: Int) {
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)
        GLES30.glUniformMatrix4fv(muMMatrixHandle,1,false,MatrixState.getMMatrix(),0)
        GLES30.glUniform3fv(muCameraHandle,1,MatrixState.cameraFB)
        GLES30.glUniform3fv(muLightLocationHandle,1,MatrixState.lightPositionFB)

        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,false,3*4,mVertexBuffer)
        GLES30.glVertexAttribPointer(maColorHandle,4,GLES30.GL_FLOAT,false,4*4,mColorBuffer)
        GLES30.glVertexAttribPointer(maNormalHandle,4,GLES30.GL_FLOAT,false,3*4,mNormalBuffer)

        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maColorHandle)
        GLES30.glEnableVertexAttribArray(maNormalHandle)

        GLES30.glLineWidth(2f)

        GLES30.glDrawArrays(GLES30.GL_LINE_STRIP,0,3*n)




    }

}
