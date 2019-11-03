package com.example.sample8.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.abstracts.AbstractShape
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class CylinderSideL(mv: BaseOpenGl3SurfaceView, val scale: Float, val r: Float, val h: Float, val n: Int) :
    AbstractShape(mv) {
    private var vCount: Int=0
    private var maNormalHandle: Int=0
    private var maPositionHandle: Int=0
    private var muCameraHandle: Int=0
    private var muLightLocationHandle: Int=0
    private var muMMatrixHandle: Int=0
    private var muMVPMatrixHandle: Int=0
    private var maColorHandle: Int = 0
    private lateinit var mColorBuffer: FloatBuffer
    private lateinit var mNormalBuffer: FloatBuffer

    override fun getVertice(): FloatArray {
        val R = scale * r
        val H= scale * h

        val angdegSpan = 360.0f / n
        vCount = 3 * n * 4//顶点个数，共有3*n*4个三角形，每个三角形都有三个顶点
        //坐标数据初始化
        val vertices = FloatArray(vCount * 3)//顶点法向量数组
        //坐标数据初始化
        var count = 0//顶点坐标数组的计数器
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

    override fun needNormal()=true

    override fun initNormalVertexBuffer() {

        val R = scale * r
        val H= scale * h

        val angdegSpan = 360.0f / n
        //坐标数据初始化
        val colors = FloatArray(vCount * 4)//顶点颜色值数组
        //坐标数据初始化
        var colorCount = 0
        var angdeg = 0f
        while (Math.ceil(angdeg.toDouble()) < 360)
        //侧面
        {
            val angrad = Math.toRadians(angdeg.toDouble())//当前弧度
            val angradNext = Math.toRadians((angdeg + angdegSpan).toDouble())//下一弧度
            //底圆当前点---0

            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            //顶圆下一点---3

            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f

            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f

            //底圆当前点---0

            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            colors[colorCount++] = 1f
            //底圆下一点---1

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

        val normals= FloatArray(getVertice().size){
            i->
            if(i%3==1){
                0f
            }else{
                getVertice()[i]

            }
        }
        val nbb = ByteBuffer.allocateDirect(normals.size * 4)//创建顶点法向量数据缓冲
        nbb.order(ByteOrder.nativeOrder())//设置字节顺序为本地操作系统顺序
        mNormalBuffer = nbb.asFloatBuffer()//转换为float型缓冲
        mNormalBuffer.put(normals)//向缓冲区中放入顶点法向量数据
        mNormalBuffer.position(0)//设置缓冲区起始位置

        //创建顶点着色数据缓冲
        val cbb = ByteBuffer.allocateDirect(colors.size * 4)
        cbb.order(ByteOrder.nativeOrder())//设置字节顺序为本地操作系统顺序
        mColorBuffer = cbb.asFloatBuffer()//转换为Float型缓冲
        mColorBuffer.put(colors)//向缓冲区中放入顶点着色数据
        mColorBuffer.position(0)//设置缓冲区起始位置
    }

    override fun getVertexName(): String {
        return "vertex_color_light.glsl"
    }

    override fun getFragName(): String {
        return "frag_color_light.glsl"
    }

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

        //将最终变换矩阵传入shader程序
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0)


        //将位置、旋转变换矩阵传入shader程序
        GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0)
        //将摄像机位置传入shader程序
        GLES30.glUniform3fv(muCameraHandle, 1, MatrixState.cameraFB)
        //将光源位置传入shader程序
        GLES30.glUniform3fv(muLightLocationHandle, 1, MatrixState.lightPositionFB)


        //传送顶点位置数据
        GLES30.glVertexAttribPointer(
            maPositionHandle,
            3,
            GLES30.GL_FLOAT,
            false,
            3 * 4,
            mVertexBuffer
        )
        //传送顶点坐标数据
        GLES30.glVertexAttribPointer(
            maColorHandle,
            4,
            GLES30.GL_FLOAT,
            false,
            4 * 4,
            mColorBuffer
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
        //启用顶点颜色数据
        GLES30.glEnableVertexAttribArray(maColorHandle)
        //启用顶点法向量数据
        GLES30.glEnableVertexAttribArray(maNormalHandle)
        //绘制线条的粗细
        GLES30.glLineWidth(2f)
        //绘制
        GLES30.glDrawArrays(GLES30.GL_LINES, 0, vCount)

    }

}