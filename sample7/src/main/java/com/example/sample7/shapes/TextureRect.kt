package com.example.sample7.shapes

import android.opengl.GLES30
import android.opengl.Matrix
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.BaseShape
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class TextureRect(mv:BaseOpenGl3SurfaceView,val sRange:Float,val tRange:Float):BaseShape(mv){
    private var muMVPMatrixHandle: Int=0
    private var maTexCoorHandle: Int=0
    private var maPositionHandle: Int=0
    private var mProgram: Int=0
    private lateinit var mFragmentShader: String
    private lateinit var mVertexShader: String
    private lateinit var mTexCoorBuffer: FloatBuffer
    private lateinit var mVertexBuffer: FloatBuffer
    private var vCount: Int=0

    var xAngle=0f
    var yAngle=0f
    var zAngle=0f

    companion object{
        var mMMatrix=FloatArray(16)
    }

    override fun initShader(mv: BaseOpenGl3SurfaceView) {
        mVertexShader= ShaderUtil.loadFromAssetsFile("vertex.glsl",mv.resources)!!
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.glsl",mv.resources)!!
        mProgram=ShaderUtil.createProgram(mVertexShader,mFragmentShader)
        maPositionHandle=GLES30.glGetAttribLocation(mProgram,"aPosition")
        maTexCoorHandle=GLES30.glGetAttribLocation(mProgram,"aTexCoor")

        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")

    }

    override fun initVertData() {
        vCount=6
        val UNIT_SIZE=0.3f
        val vertices= floatArrayOf(
            -4*UNIT_SIZE,4*UNIT_SIZE,0f,
            -4*UNIT_SIZE,-4*UNIT_SIZE,0f,
            4*UNIT_SIZE,-4*UNIT_SIZE,0f,

            4*UNIT_SIZE,-4*UNIT_SIZE,0f,
            4*UNIT_SIZE,4*UNIT_SIZE,0f,
            -4*UNIT_SIZE,4*UNIT_SIZE,0f
        )

        mVertexBuffer=ByteBuffer.allocateDirect(vertices.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mVertexBuffer.put(vertices)
        mVertexBuffer.position(0)

        val texCoor= floatArrayOf(
            0f,0f,
            0f, tRange,
            sRange,tRange,
            sRange,tRange,
            sRange, 0f,
            0f,0f
        )
        mTexCoorBuffer=ByteBuffer.allocateDirect(texCoor.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mTexCoorBuffer.put(texCoor)
        mTexCoorBuffer.position(0)

    }

    override fun drawSelf() {
    }

    fun drawSelf(texId:Int){
        GLES30.glUseProgram(mProgram)
        Matrix.setRotateM(mMMatrix,0,0f,0f,1f,0f)
        Matrix.translateM(mMMatrix,0,0f,0f,1f)
        Matrix.rotateM(mMMatrix,0,yAngle,0f,1f,0f)
        Matrix.rotateM(mMMatrix,0,zAngle,0f,0f,1f)
        Matrix.rotateM(mMMatrix,0,xAngle,1f,0f,0f)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(mMMatrix),0)

        GLES30.glVertexAttribPointer(maPositionHandle,
            3,
            GLES30.GL_FLOAT,
            false,
            3*4,
            mVertexBuffer)

        GLES30.glVertexAttribPointer(
            maTexCoorHandle,
            2,
            GLES30.GL_FLOAT,
            false,
            2*4,
            mTexCoorBuffer
        )

        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maTexCoorHandle)

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId)

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount)
    }
}