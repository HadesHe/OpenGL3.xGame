package com.example.sample8.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.abstracts.AbstractShape
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * scale:大小
 * r:半径
 * n:切分的份数
 */
class Circle(mv:BaseOpenGl3SurfaceView,val scale:Float,val r:Float,val n:Int) :AbstractShape(mv){
    private var maTexCoorHandle: Int=0
    private var maNormalHandle: Int=0
    private var maPositionHandle: Int=0
    private var muCameraHandle: Int=0
    private var muLightLocationHandle: Int=0
    private var muMMatrixHandle: Int=0
    private var muMVPMatrixHandle: Int=0
    private lateinit var mTexCoorBuffer: FloatBuffer
    private lateinit var mNormalBuffer: FloatBuffer
    var vCount=0

    override fun getVertice(): FloatArray {
        val R=r*scale
        val angdegSpan=360.0f/n
        vCount=3*n

        val vertices=FloatArray(vCount*3)
        var count=0
        var angdeg = 0f
        while (Math.ceil(angdeg.toDouble()) < 360) {//生成每个三角形的顶点数据
            val angrad = Math.toRadians(angdeg.toDouble())//当前弧度
            val angradNext = Math.toRadians((angdeg + angdegSpan).toDouble())//下一弧度
            //圆面中心点的顶点坐标
            vertices[count++] = 0f//顶点坐标
            vertices[count++] = 0f
            vertices[count++] = 0f
            //当前弧度对应的边缘顶点坐标
            vertices[count++] = (-R * Math.sin(angrad)).toFloat()//顶点坐标
            vertices[count++] = (R * Math.cos(angrad)).toFloat()
            vertices[count++] = 0f
            //当前弧度对应的边缘顶点纹理坐标
            //下一弧度对应的边缘顶点坐标
            vertices[count++] = (-R * Math.sin(angradNext)).toFloat()//顶点坐标
            vertices[count++] = (R* Math.cos(angradNext)).toFloat()
            vertices[count++] = 0f
            //下一弧度对应的边缘顶点纹理坐标
            angdeg += angdegSpan
        }

        return vertices

    }

    override fun initNormalVertexBuffer() {
        val normals=FloatArray(vCount*3){
            i->
            if(i%3==2){
                1f
            }else{
                0f
            }
        }
        mNormalBuffer=ByteBuffer.allocateDirect(normals.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mNormalBuffer.put(normals)
        mNormalBuffer.position(0)

        val angdegSpan=360.0f/n
        val textures=FloatArray(vCount*2)
        var stCount=0

        var angdeg = 0f
        while (Math.ceil(angdeg.toDouble()) < 360) {//生成每个三角形的顶点数据
            val angrad = Math.toRadians(angdeg.toDouble())//当前弧度
            val angradNext = Math.toRadians((angdeg + angdegSpan).toDouble())//下一弧度
            //圆面中心点的顶点纹理坐标
            textures[stCount++] = 0.5f//st坐标
            textures[stCount++] = 0.5f
            //当前弧度对应的边缘顶点纹理坐标
            textures[stCount++] = (0.5f - 0.5f * Math.sin(angrad)).toFloat()//st坐标
            textures[stCount++] = (0.5f - 0.5f * Math.cos(angrad)).toFloat()
            //下一弧度对应的边缘顶点纹理坐标
            textures[stCount++] = (0.5f - 0.5f * Math.sin(angradNext)).toFloat()//st坐标
            textures[stCount++] = (0.5f - 0.5f * Math.cos(angradNext)).toFloat()
            angdeg += angdegSpan
        }

        mTexCoorBuffer=ByteBuffer.allocateDirect(textures.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mTexCoorBuffer.put(textures)
        mTexCoorBuffer.position(0)

    }

    override fun needNormal()=true

    override fun getVertexName(): String {
        return "vertex_tex_light.glsl"
    }

    override fun getFragName(): String {
        return "frag_tex_light.glsl"
    }

    override fun initLocationHandle() {
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")
        muMMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMMatrix")
        muLightLocationHandle=GLES30.glGetUniformLocation(mProgram,"uLightLocation")
        muCameraHandle=GLES30.glGetUniformLocation(mProgram,"uCamera")

        maPositionHandle=GLES30.glGetAttribLocation(mProgram,"aPosition")
        maNormalHandle=GLES30.glGetAttribLocation(mProgram,"aNormal")
        maTexCoorHandle=GLES30.glGetAttribLocation(mProgram,"aTexCoor")


    }

    override fun drawSelf(texId: Int) {

        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)
        GLES30.glUniformMatrix4fv(muMMatrixHandle,1,false,MatrixState.getMMatrix(),0)
        GLES30.glUniform3fv(muCameraHandle,1,MatrixState.cameraFB)
        GLES30.glUniform3fv(muLightLocationHandle,1,MatrixState.lightPositionFB)

        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,false,3*4,mVertexBuffer)
        GLES30.glVertexAttribPointer(maTexCoorHandle,2,GLES30.GL_FLOAT,false,2*4,mTexCoorBuffer)
        GLES30.glVertexAttribPointer(maNormalHandle,4,GLES30.GL_FLOAT,false,3*4,mNormalBuffer)

        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maTexCoorHandle)
        GLES30.glEnableVertexAttribArray(maNormalHandle)

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN,0,vCount)
    }

}