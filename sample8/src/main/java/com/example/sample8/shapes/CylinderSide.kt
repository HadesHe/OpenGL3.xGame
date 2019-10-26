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
 * h:高度
 * n:切分的份数
 */
class CylinderSide(mv:BaseOpenGl3SurfaceView,val scale:Float,val r:Float,val h:Float,val n:Int) :AbstractShape(mv){
    private var muMMatrixHandle: Int=0
    private var maLightLocationHandle=0
    private var maCameraHandle=0
    private var maNormalHandle=0
    private var muMVPMatrixHandle: Int=0
    private var maTexCoorHandle=0
    private var maPositionHandle: Int=0
    private lateinit var mTexCoorBuffer: FloatBuffer
    private lateinit var mNormalBuffer: FloatBuffer
    private var vCount=0

    override fun getVertice(): FloatArray {
        val R=scale*r
        val H=scale*h
        val angdegSpan=360.0f/n
        vCount=3*n*4

        val vertices=FloatArray(vCount*3)

        var count=0

        var angdeg = 0f
        while (Math.ceil(angdeg.toDouble()) < 360)
        //侧面
        {//生成每对三角形的顶点数据
            val angrad = Math.toRadians(angdeg.toDouble())//当前弧度
            val angradNext = Math.toRadians((angdeg + angdegSpan).toDouble())//下一弧度
            //底圆当前点---0
            vertices[count++] = (-R * Math.sin(angrad)).toFloat()//第一个三角形顶点1
            vertices[count++] = 0f
            vertices[count++] = (-R * Math.cos(angrad)).toFloat()

            //顶圆下一点---3
            vertices[count++] = (-R * Math.sin(angradNext)).toFloat()//第一个三角形顶点2
            vertices[count++] = H
            vertices[count++] = (-R * Math.cos(angradNext)).toFloat()

            //顶圆当前点---2
            vertices[count++] = (-R * Math.sin(angrad)).toFloat()//第一个三角形顶点3
            vertices[count++] = H
            vertices[count++] = (-R * Math.cos(angrad)).toFloat()


            //底圆当前点---0
            vertices[count++] = (-R * Math.sin(angrad)).toFloat()//第二个三角形顶点1
            vertices[count++] = 0f
            vertices[count++] = (-R * Math.cos(angrad)).toFloat()

            //底圆下一点---1
            vertices[count++] = (-R * Math.sin(angradNext)).toFloat()//第二个三角形顶点2
            vertices[count++] = 0f
            vertices[count++] = (-R * Math.cos(angradNext)).toFloat()

            //顶圆下一点---3
            vertices[count++] = (-R * Math.sin(angradNext)).toFloat()//第二个三角形顶点3
            vertices[count++] = H
            vertices[count++] = (-R * Math.cos(angradNext)).toFloat()

            angdeg += angdegSpan
        }

        return vertices

    }

    override fun needNormal(): Boolean=true

    override fun initNormalVertexBuffer() {
        val vertexSize=getVertice().size
        val normals=FloatArray(vertexSize){
            i->
            if(i%3==1){
                0f
            }else{
                getVertice()[i]
            }
        }

        mNormalBuffer=ByteBuffer.allocateDirect(vertexSize*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mNormalBuffer.put(normals)
        mNormalBuffer.position(0)

        val textures=FloatArray(vCount*2)
        var stCount = 0//顶点纹理坐标数组的计数器
        val angdegSpan=360.0f/n
        var angdeg = 0f
        while (Math.ceil(angdeg.toDouble()) < 360)
        //侧面
        {//生成每对三角形的顶点数据
            val angrad = Math.toRadians(angdeg.toDouble())//当前弧度
            val angradNext = Math.toRadians((angdeg + angdegSpan).toDouble())//下一弧度

            textures[stCount++] = (angrad / (2 * Math.PI)).toFloat()//纹理坐标
            textures[stCount++] = 1f
            //顶圆下一点---3

            textures[stCount++] = (angradNext / (2 * Math.PI)).toFloat()//纹理坐标
            textures[stCount++] = 0f
            //顶圆当前点---2

            textures[stCount++] = (angrad / (2 * Math.PI)).toFloat()//纹理坐标
            textures[stCount++] = 0f

            //底圆当前点---0

            textures[stCount++] = (angrad / (2 * Math.PI)).toFloat()//纹理坐标
            textures[stCount++] = 1f
            //底圆下一点---1

            textures[stCount++] = (angradNext / (2 * Math.PI)).toFloat()//纹理坐标
            textures[stCount++] = 1f
            //顶圆下一点---3

            textures[stCount++] = (angradNext / (2 * Math.PI)).toFloat()//纹理坐标
            textures[stCount++] = 0f
            angdeg += angdegSpan
        }
        mTexCoorBuffer=ByteBuffer.allocateDirect(textures.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mTexCoorBuffer.put(textures)
        mTexCoorBuffer.position(0)

    }

    override fun getVertexName(): String ="vertex_tex_light.glsl"

    override fun getFragName(): String ="frag_tex_light.glsl"

    override fun initLocationHandle() {
        //获取程序中顶点位置属性引用id
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition")
        //获取程序中顶点纹理坐标属性引用id
        maTexCoorHandle = GLES30.glGetAttribLocation(mProgram, "aTexCoor")
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix")

        //获取程序中顶点法向量属性引用id
        maNormalHandle = GLES30.glGetAttribLocation(mProgram, "aNormal")
        //获取程序中摄像机位置引用id
        maCameraHandle = GLES30.glGetUniformLocation(mProgram, "uCamera")
        //获取程序中光源位置引用id
        maLightLocationHandle = GLES30.glGetUniformLocation(mProgram, "uLightLocation")
        //获取位置、旋转变换矩阵引用id
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix")
    }

    override fun drawSelf(texId: Int) {
        GLES30.glUseProgram(mProgram)
        //将最终变换矩阵传入shader程序
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0)
        //将位置、旋转变换矩阵传入shader程序
        GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0)
        //将摄像机位置传入shader程序
        GLES30.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB)
        //将光源位置传入shader程序
        GLES30.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB)

        //传送顶点位置数据
        GLES30.glVertexAttribPointer(
            maPositionHandle,
            3,
            GLES30.GL_FLOAT,
            false,
            3 * 4,
            mVertexBuffer
        )
        //传送顶点纹理坐标数据
        GLES30.glVertexAttribPointer(
            maTexCoorHandle,
            2,
            GLES30.GL_FLOAT,
            false,
            2 * 4,
            mTexCoorBuffer
        )
        //传送顶点法向量数据
        GLES30.glVertexAttribPointer(
            maNormalHandle,
            4,
            GLES30.GL_FLOAT,
            false,
            3 * 4,
            mNormalBuffer
        )

        //启用顶点位置数据
        GLES30.glEnableVertexAttribArray(maPositionHandle)
        //启用顶点纹理数据
        GLES30.glEnableVertexAttribArray(maTexCoorHandle)
        //启用顶点法向量数据
        GLES30.glEnableVertexAttribArray(maNormalHandle)
        //绑定纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId)

        //绘制纹理矩形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount)
    }

}