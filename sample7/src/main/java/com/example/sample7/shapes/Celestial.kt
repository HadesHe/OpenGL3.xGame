package com.example.sample7.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.BaseTextureShape
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * scale:星星尺寸
 * yAngle:天球绕 Y 轴旋转的角度
 * vCount:星星数量
 */
class Celestial(mv:BaseOpenGl3SurfaceView,val scale:Float,val yAngle:Float,val vCount:Int):BaseTextureShape(mv){
    private var uPointSizeHandle: Int=0
    private var muMVPMatrixHandle: Int=0
    private var maPositionHandle: Int=0
    private var mProgram: Int=0
    private lateinit var mFragmentShader: String
    private lateinit var mVertexShader: String
    private lateinit var mVertexBuffer: FloatBuffer
    private val UNIT_SIZE=10.0f//天球半径
    override fun initShader(mv: BaseOpenGl3SurfaceView) {
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_xk.glsl",mv.resources)!!
        ShaderUtil.checkGlError("==s==")
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_xk.glsl",mv.resources)!!

        ShaderUtil.checkGlError("==ss==")
        mProgram=ShaderUtil.createProgram(mVertexShader,mFragmentShader)

        maPositionHandle=GLES30.glGetAttribLocation(mProgram,"aPosition")
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")
        uPointSizeHandle=GLES30.glGetUniformLocation(mProgram,"uPointSize")
    }

    override fun initVertData() {
        val vertices=FloatArray(vCount*3);
        for(i in 0 until vCount){
            val angleTempJD=Math.PI*Math.random()
            val angleTempWD=Math.PI*(Math.random()-0.5f)
            vertices[i*3]= (UNIT_SIZE*Math.cos(angleTempWD)*Math.sin(angleTempJD)).toFloat()
            vertices[i*3+1]= (UNIT_SIZE*Math.sin(angleTempWD)).toFloat()
            vertices[i*3+2]= (UNIT_SIZE*Math.cos(angleTempWD)*Math.cos(angleTempJD)).toFloat()
        }

        mVertexBuffer=ByteBuffer.allocateDirect(vertices.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()

        mVertexBuffer.put(vertices)
        mVertexBuffer.position(0)
    }

    public fun drawSelf(){
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)
        GLES30.glUniform1f(uPointSizeHandle,scale)
        GLES30.glVertexAttribPointer(
            maPositionHandle,
            3,
            GLES30.GL_FLOAT,
            false,
            3*4,
            mVertexBuffer
        )
        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glDrawArrays(GLES30.GL_POINTS,0,vCount)
    }

}