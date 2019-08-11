package com.example.sample5

import android.opengl.GLES30
import android.opengl.Matrix
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class SixPointedStar(var orthoView: OrthoView,var radiu:Float,var R:Float,var z:Float){

    var xAngle=0.0f
    var yAngle=0.0f
    private var mMMatrix: FloatArray = FloatArray(16)
    private var muMVPMatrixHandle: Int=0
    private var maColorHandle: Int=0
    private var maPositionHandle: Int=0
    private var mProgram: Int = 0
    private var mFragmentShader: String? = null
    private var mVertexShader: String?=null
    private lateinit var mColorBuffer: FloatBuffer
    private lateinit var mVertexBuffer: FloatBuffer
    private var vCount: Int=0

    companion object{
        val UNIT_SIZE=1f
    }
    init {
        initVertexData(radiu,R,z)
        initShader(orthoView)
    }

    private fun initShader(orthoView: OrthoView) {
        mVertexShader=ShaderUtil.loadFromAssetsFile("sixpointvert.glsl",orthoView.resources)
        mFragmentShader=ShaderUtil.loadFromAssetsFile("sixpointfrag.glsl",orthoView.resources)
        mProgram=ShaderUtil.createProgram(mVertexShader!!,mFragmentShader!!)
        maPositionHandle=GLES30.glGetAttribLocation(mProgram,"aPosition")
        maColorHandle=GLES30.glGetAttribLocation(mProgram,"aColor")
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")
    }

    private fun initVertexData(radiu: Float, r: Float, z: Float) {
        var flist=ArrayList<Float>()

        for(i in 0 until 360 step 60){
            flist.add(0f)
            flist.add(0f)
            flist.add(z)

            flist.add(((radiu* UNIT_SIZE*Math.cos(Math.toRadians(i.toDouble()))).toFloat()))
            flist.add(((radiu* UNIT_SIZE*Math.sin(Math.toRadians(i.toDouble()))).toFloat()))
            flist.add(z)

            flist.add(((r* UNIT_SIZE*Math.cos(Math.toRadians((i+30f).toDouble()))).toFloat()))
            flist.add(((r* UNIT_SIZE*Math.sin(Math.toRadians((i+30f).toDouble()))).toFloat()))
            flist.add(z)

            flist.add(0f)
            flist.add(0f)
            flist.add(z)

            flist.add(((r* UNIT_SIZE*Math.cos(Math.toRadians((i+30f).toDouble()))).toFloat()))
            flist.add(((r* UNIT_SIZE*Math.sin(Math.toRadians((i+30f).toDouble()))).toFloat()))
            flist.add(z)

            flist.add(((radiu* UNIT_SIZE*Math.cos(Math.toRadians((i+60f).toDouble()))).toFloat()))
            flist.add(((radiu* UNIT_SIZE*Math.sin(Math.toRadians((i+60f).toDouble()))).toFloat()))
            flist.add(z)

        }

        vCount=flist.size/3
        var vertexArray=FloatArray(flist.size)

        for(i in 0 until vCount){
            vertexArray[i*3]=flist.get(i*3)
            vertexArray[i*3+1]=flist.get(i*3+1)
            vertexArray[i*3+2]=flist.get(i*3+2)
        }

        mVertexBuffer=ByteBuffer.allocateDirect(vertexArray.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mVertexBuffer.put(vertexArray)
        mVertexBuffer.position(0)

        var colorArray=FloatArray(vCount*4)
        for(i in 0 until vCount){
            if(i%3==0){
                colorArray[i*4]=1f
                colorArray[i*4+1]=1f
                colorArray[i*4+2]=1f
                colorArray[i*4+3]=0f
            }else{
                colorArray[i*4]=0.45f
                colorArray[i*4+1]=0.75f
                colorArray[i*4+2]=0.75f
                colorArray[i*4+3]=0f

            }
        }

        mColorBuffer=ByteBuffer.allocateDirect(colorArray.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mColorBuffer.put(colorArray)
        mColorBuffer.position(0)

    }

    fun drawSelf(index:Float=0.0f) {
        GLES30.glUseProgram(mProgram)
        Matrix.setRotateM(mMMatrix,0,0f,0f,1f,0f)
        Matrix.translateM(mMMatrix,0,index,0f,1f)
        Matrix.rotateM(mMMatrix,0,yAngle,0f,1f,0f)
        Matrix.rotateM(mMMatrix,0,xAngle,1f,0f,0f)

        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,
            false, MatrixState.getFinalMatrix(mMMatrix),0)
        GLES30.glVertexAttribPointer(maPositionHandle,3,
            GLES30.GL_FLOAT,false,3*4,mVertexBuffer)
        GLES30.glVertexAttribPointer(maColorHandle,4,
            GLES30.GL_FLOAT,false,4*4,mColorBuffer)
        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maColorHandle)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount)
    }
}