package com.example.sample7.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.abstracts.AbstractShape
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Stairs(mv:BaseOpenGl3SurfaceView) :AbstractShape(mv){
    private var maCameraHandle: Int=0
    private var maLightLocationHandle: Int=0
    private var muMMatrixHandle: Int=0
    private var muMVPMatrixHandle: Int=0
    private var maNormalHander: Int=0
    private var maPositionHandle: Int=0
    private lateinit var mNormalBuffer: FloatBuffer
    val xSize=0.2f
    val y1Size=0.1f
    val y2Size=0.2f
    val y3Size=0.3f
    val y4Size=0.4f
    val z1Size=0.4f
    val z2Size=0.3f
    val z3Size=0.2f
    val z4Size=0.1f

    override fun getVertice(): FloatArray {
        //顶点坐标数据的初始化================begin============================
        val vertices = floatArrayOf(
            //第四个（最下面）立方体的下面
            xSize, 0f, 0f,
            xSize, 0f, z1Size,
            -xSize, 0f, z1Size,
            -xSize, 0f, z1Size,
            -xSize, 0f, 0f,
            xSize, 0f, 0f,
            //第四个（最下面）立方体的上面
            xSize, y1Size, 0f,
            -xSize, y1Size, 0f,
            -xSize, y1Size, z1Size,
            -xSize, y1Size, z1Size,
            xSize, y1Size, z1Size,
            xSize, y1Size, 0f,
            //第四个（最下面）立方体的前面
            xSize, y1Size, z1Size,
            -xSize, y1Size, z1Size,
            -xSize, 0f, z1Size,
            -xSize, 0f, z1Size,
            xSize, 0f, z1Size,
            xSize, y1Size, z1Size,
            //第四个（最下面）立方体的后面
            xSize, y1Size, 0f,
            xSize, 0f, 0f,
            -xSize, 0f, 0f,
            -xSize, 0f,
            0f,
            -xSize,
            y1Size,
            0f,
            xSize,
            y1Size,
            0f,
            //第四个（最下面）立方体的左面
            -xSize,
            y1Size,
            z1Size,
            -xSize,
            y1Size,
            0f,
            -xSize,
            0f,
            0f,
            -xSize,
            0f,
            0f,
            -xSize,
            0f,
            z1Size,
            -xSize,
            y1Size,
            z1Size,
            //第四个（最下面）立方体的右面
            xSize,
            y1Size,
            z1Size,
            xSize,
            0f,
            z1Size,
            xSize,
            0f,
            0f,
            xSize,
            0f,
            0f,
            xSize,
            y1Size,
            0f,
            xSize,
            y1Size,
            z1Size,

            //第三个立方体的上面
            xSize,
            y2Size,
            0f,
            -xSize,
            y2Size,
            0f,
            -xSize,
            y2Size,
            z2Size,
            -xSize,
            y2Size,
            z2Size,
            xSize,
            y2Size,
            z2Size,
            xSize,
            y2Size,
            0f,
            //第三个立方体的前面
            xSize,
            y2Size,
            z2Size,
            -xSize,
            y2Size,
            z2Size,
            -xSize,
            y1Size,
            z2Size,
            -xSize,
            y1Size,
            z2Size,
            xSize,
            y1Size,
            z2Size,
            xSize,
            y2Size,
            z2Size,
            //第三个立方体的后面
            xSize,
            y2Size,
            0f,
            xSize,
            y1Size,
            0f,
            -xSize,
            y1Size,
            0f,
            -xSize,
            y1Size,
            0f,
            -xSize,
            y2Size,
            0f,
            xSize,
            y2Size,
            0f,
            //第三个立方体的左面
            -xSize,
            y2Size,
            z2Size,
            -xSize,
            y2Size,
            0f,
            -xSize,
            y1Size,
            0f,
            -xSize,
            y1Size,
            0f,
            -xSize,
            y1Size,
            z2Size,
            -xSize,
            y2Size,
            z2Size,
            //第三个立方体的右面
            xSize,
            y2Size,
            z2Size,
            xSize,
            y1Size,
            z2Size,
            xSize,
            y1Size,
            0f,
            xSize,
            y1Size,
            0f,
            xSize,
            y2Size,
            0f,
            xSize,
            y2Size,
            z2Size,

            //第二个立方体的上面
            xSize,
            y3Size,
            0f,
            -xSize,
            y3Size,
            0f,
            -xSize,
            y3Size,
            z3Size,
            -xSize,
            y3Size,
            z3Size,
            xSize,
            y3Size,
            z3Size,
            xSize,
            y3Size,
            0f,
            //第二个立方体的前面
            xSize,
            y3Size,
            z3Size,
            -xSize,
            y3Size,
            z3Size,
            -xSize,
            y2Size,
            z3Size,
            -xSize,
            y2Size,
            z3Size,
            xSize,
            y2Size,
            z3Size,
            xSize,
            y3Size,
            z3Size,
            //第二个立方体的后面
            xSize,
            y3Size,
            0f,
            xSize,
            y2Size,
            0f,
            -xSize,
            y2Size,
            0f,
            -xSize,
            y2Size,
            0f,
            -xSize,
            y3Size,
            0f,
            xSize,
            y3Size,
            0f,
            //第二个立方体的左面
            -xSize,
            y3Size,
            z3Size,
            -xSize,
            y3Size,
            0f,
            -xSize,
            y2Size,
            0f,
            -xSize,
            y2Size,
            0f,
            -xSize,
            y2Size,
            z3Size,
            -xSize,
            y3Size,
            z3Size,
            //第二个立方体的右面
            xSize,
            y3Size,
            z3Size,
            xSize,
            y2Size,
            z3Size,
            xSize,
            y2Size,
            0f,
            xSize,
            y2Size,
            0f,
            xSize,
            y3Size,
            0f,
            xSize,
            y3Size,
            z3Size,

            //第一个立方体的上面
            xSize,
            y4Size,
            0f,
            -xSize,
            y4Size,
            0f,
            -xSize,
            y4Size,
            z4Size,
            -xSize,
            y4Size,
            z4Size,
            xSize,
            y4Size,
            z4Size,
            xSize,
            y4Size,
            0f,
            //第一个立方体的前面
            xSize,
            y4Size,
            z4Size,
            -xSize,
            y4Size,
            z4Size,
            -xSize,
            y3Size,
            z4Size,
            -xSize,
            y3Size,
            z4Size,
            xSize,
            y3Size,
            z4Size,
            xSize,
            y4Size,
            z4Size,
            //第一个立方体的后面
            xSize,
            y4Size,
            0f,
            xSize,
            y3Size,
            0f,
            -xSize,
            y3Size,
            0f,
            -xSize,
            y3Size,
            0f,
            -xSize,
            y4Size,
            0f,
            xSize,
            y4Size,
            0f,
            //第一个立方体的左面
            -xSize,
            y4Size,
            z4Size,
            -xSize,
            y4Size,
            0f,
            -xSize,
            y3Size,
            0f,
            -xSize,
            y3Size,
            0f,
            -xSize,
            y3Size,
            z4Size,
            -xSize,
            y4Size,
            z4Size,
            //第一个立方体的右面
            xSize,
            y4Size,
            z4Size,
            xSize,
            y3Size,
            z4Size,
            xSize,
            y3Size,
            0f,
            xSize,
            y3Size,
            0f,
            xSize,
            y4Size,
            0f,
            xSize,
            y4Size,
            z4Size
        )
        return vertices
    }
    override fun needNormal()=true

    override fun initNormalVertexBuffer() {
        val normals = floatArrayOf(
            //第四个（最下面）立方体的下面
            0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f,
            //第四个（最下面）立方体的上面
            0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
            //第四个（最下面）立方体的前面
            0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
            //第四个（最下面）立方体的后面
            0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f,
            //第四个（最下面）立方体的左面
            -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f,
            //第四个（最下面）立方体的右面
            1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,

            //第三个立方体的上面
            0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
            //第三个立方体的前面
            0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
            //第三个立方体的后面
            0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f,
            //第三个立方体的左面
            -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f,
            //第三个立方体的右面
            1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,

            //第二个立方体的上面
            0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
            //第二个立方体的前面
            0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
            //第二个立方体的后面
            0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f,
            //第二个立方体的左面
            -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f,
            //第二个立方体的右面
            1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,

            //第一个立方体的上面
            0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
            //第一个立方体的前面
            0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
            //第一个立方体的后面
            0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f,
            //第一个立方体的左面
            -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f,
            //第一个立方体的右面
            1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f
        )

        mNormalBuffer=ByteBuffer.allocateDirect(normals.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mNormalBuffer.put(normals)
        mNormalBuffer.position(0)
    }

    override fun getVertexName(): String {
        return "vertex_stairs.glsl"
    }

    override fun getFragName(): String {
        return "frag_stairs.glsl"
    }

    override fun initLocationHandle() {
        maPositionHandle=GLES30.glGetAttribLocation(mProgram,"aPosition")
        maNormalHander=GLES30.glGetAttribLocation(mProgram,"aNormal")
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")
        muMMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMMatrix")
        maLightLocationHandle=GLES30.glGetUniformLocation(mProgram,"uLightLocation")
        maCameraHandle=GLES30.glGetUniformLocation(mProgram,"uCamera")
    }

    override fun drawSelf(texId: Int) {
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)
        GLES30.glUniformMatrix4fv(muMMatrixHandle,1,false,MatrixState.getMMatrix(),0)
        GLES30.glUniform3fv(maLightLocationHandle,1,MatrixState.lightPositionFB)
        GLES30.glUniform3fv(maCameraHandle,1,MatrixState.cameraFB)

        GLES30.glVertexAttribPointer(
            maPositionHandle,
            3,
            GLES30.GL_FLOAT,
            false,
            3*4,
            mVertexBuffer
        )
        GLES30.glVertexAttribPointer(
            maNormalHander,
            3,
            GLES30.GL_FLOAT,
            false,
            3*4,
            mNormalBuffer
        )


        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maNormalHander)
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_3D,texId)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,getVertice().size/3)
    }

}