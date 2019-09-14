package com.example.sample7.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.BaseShape
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Triangle(mv:BaseOpenGl3SurfaceView) :BaseShape(mv){
    var xAngle: Float=0f
    var zAngle: Float=0f
    var yAngle: Float=0f
    private var muMVPMatrixHandle: Int=0
    private var maTexCoordHandle: Int=0
    private var maPositionHandler: Int=0
    private var mProgram: Int=0
    private lateinit var mFragmentShader: String
    private lateinit var mVertexShader: String
    private lateinit var mTexCoorBuffer: FloatBuffer
    private lateinit var mVertexBuffer: FloatBuffer
    private var vCount: Int=0
    override var UNIT_SIZE: Float
        get() = 0.15f
        set(value) {}

    override fun initShader(mv: BaseOpenGl3SurfaceView) {
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.glsl",mv.resources)!!
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.glsl",mv.resources)!!
        mProgram=ShaderUtil.createProgram(mVertexShader,mFragmentShader)
        maPositionHandler=GLES30.glGetAttribLocation(mProgram,"aPosition")
        maTexCoordHandle=GLES30.glGetAttribLocation(mProgram,"aTexCoor")
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")


    }

    override fun initVertData() {
        vCount=3
        var vertices= floatArrayOf(
            0f,11*UNIT_SIZE,0f,
            -11*UNIT_SIZE,-11*UNIT_SIZE,0f,
            11*UNIT_SIZE,-11*UNIT_SIZE,0f
        )

        mVertexBuffer=ByteBuffer.allocateDirect(vertices.size*4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mVertexBuffer.put(vertices)
        mVertexBuffer.position(0)

        val texCoor= floatArrayOf(
            0f,0f,
            0f,1f,
            1f,1f
        )

        mTexCoorBuffer=ByteBuffer.allocateDirect(texCoor.size*4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mVertexBuffer.put(texCoor)
        mVertexBuffer.position(0)

    }

    override fun drawSelf() {

    }

    fun drawSelf(texId:Int){
        GLES30.glUseProgram(mProgram)
        MatrixState.setInitStack()
        MatrixState.translate(0f,0f,1f)
        MatrixState.rotate(yAngle,0f,1f,0f)
        MatrixState.rotate(zAngle,0f,0f,1f)
        MatrixState.rotate(xAngle,1f,0f,0f)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)
        GLES30.glVertexAttribPointer(maPositionHandler,3,GLES30.GL_FLOAT,false,3*4,mVertexBuffer)
        GLES30.glVertexAttribPointer(maTexCoordHandle,2,GLES30.GL_FLOAT,false,2*4,mTexCoorBuffer)

        GLES30.glEnableVertexAttribArray(maPositionHandler)
        GLES30.glEnableVertexAttribArray(maTexCoordHandle)

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount)

    }

}