package com.example.sample7.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.BaseTextureShape
import com.example.baseopengl.MatrixState
import com.example.baseopengl.ShaderUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.ArrayList

class Earth (mv:BaseOpenGl3SurfaceView,val r:Float):BaseTextureShape(mv){
    private var muMMatrixHandle: Int=0
    private var uNightTexHandle: Int=0
    private var uDayTexHandle: Int=0
    private var maSunLightLocationHandle: Int=0
    private var maCameraHandle: Int=0
    private var muMVPMatrixHandle: Int=0
    private var maNormalHandle: Int=0
    private var maTexCoorHandle: Int=0
    private var maPositionHandle: Int=0
    private var mProgram: Int=0
    private lateinit var mFragmentShader: String
    private lateinit var mVertexShader: String
    private lateinit var mTexCoorBuffer: FloatBuffer
    private lateinit var mVertexBuffer: FloatBuffer
    private var vCount: Int=0

    override fun initShader(mv: BaseOpenGl3SurfaceView) {
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_earth.glsl",mv.resources)!!
        ShaderUtil.checkGlError("==ss==")
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_earth.glsl",mv.resources)!!
        ShaderUtil.checkGlError("==ss==")
        mProgram=ShaderUtil.createProgram(mVertexShader,mFragmentShader)

        //获取程序中顶点位置属性引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition")
        //获取程序中顶点纹理属性引用
        maTexCoorHandle = GLES30.glGetAttribLocation(mProgram, "aTexCoor")
        //获取程序中顶点法向量属性引用
        maNormalHandle = GLES30.glGetAttribLocation(mProgram, "aNormal")
        //获取程序中总变换矩阵引用
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix")
        //获取程序中摄像机位置引用
        maCameraHandle = GLES30.glGetUniformLocation(mProgram, "uCamera")
        //获取程序中光源位置引用
        maSunLightLocationHandle = GLES30.glGetUniformLocation(mProgram, "uLightLocationSun")
        //获取白天、黑夜两个纹理引用
        uDayTexHandle = GLES30.glGetUniformLocation(mProgram, "sTextureDay")
        uNightTexHandle = GLES30.glGetUniformLocation(mProgram, "sTextureNight")
        //获取位置、旋转变换矩阵引用
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix")
    }

    override fun initVertData() {
        //顶点坐标数据的初始化================begin============================
        val UNIT_SIZE = 0.5f
        val alVertix = ArrayList<Float>()//存放顶点坐标的ArrayList
        val angleSpan = 10f//将球进行单位切分的角度
        var vAngle = 90f
        while (vAngle > -90) {//垂直方向angleSpan度一份
            var hAngle = 360f
            while (hAngle > 0) {//水平方向angleSpan度一份
                //纵向横向各到一个角度后计算对应的此点在球面上的坐标
                var xozLength = r.toDouble() * UNIT_SIZE.toDouble() * Math.cos(Math.toRadians(vAngle.toDouble()))
                val x1 = (xozLength * Math.cos(Math.toRadians(hAngle.toDouble()))).toFloat()
                val z1 = (xozLength * Math.sin(Math.toRadians(hAngle.toDouble()))).toFloat()
                val y1 = (r.toDouble() * UNIT_SIZE.toDouble() * Math.sin(Math.toRadians(vAngle.toDouble()))).toFloat()
                xozLength =
                    r.toDouble() * UNIT_SIZE.toDouble() * Math.cos(Math.toRadians((vAngle - angleSpan).toDouble()))
                val x2 = (xozLength * Math.cos(Math.toRadians(hAngle.toDouble()))).toFloat()
                val z2 = (xozLength * Math.sin(Math.toRadians(hAngle.toDouble()))).toFloat()
                val y2 =
                    (r.toDouble() * UNIT_SIZE.toDouble() * Math.sin(Math.toRadians((vAngle - angleSpan).toDouble()))).toFloat()
                xozLength =
                    r.toDouble() * UNIT_SIZE.toDouble() * Math.cos(Math.toRadians((vAngle - angleSpan).toDouble()))
                val x3 = (xozLength * Math.cos(Math.toRadians((hAngle - angleSpan).toDouble()))).toFloat()
                val z3 = (xozLength * Math.sin(Math.toRadians((hAngle - angleSpan).toDouble()))).toFloat()
                val y3 =
                    (r.toDouble() * UNIT_SIZE.toDouble() * Math.sin(Math.toRadians((vAngle - angleSpan).toDouble()))).toFloat()
                xozLength = r.toDouble() * UNIT_SIZE.toDouble() * Math.cos(Math.toRadians(vAngle.toDouble()))
                val x4 = (xozLength * Math.cos(Math.toRadians((hAngle - angleSpan).toDouble()))).toFloat()
                val z4 = (xozLength * Math.sin(Math.toRadians((hAngle - angleSpan).toDouble()))).toFloat()
                val y4 = (r.toDouble() * UNIT_SIZE.toDouble() * Math.sin(Math.toRadians(vAngle.toDouble()))).toFloat()
                //构建第一三角形
                alVertix.add(x1)
                alVertix.add(y1)
                alVertix.add(z1)
                alVertix.add(x2)
                alVertix.add(y2)
                alVertix.add(z2)
                alVertix.add(x4)
                alVertix.add(y4)
                alVertix.add(z4)
                //构建第二三角形
                alVertix.add(x4)
                alVertix.add(y4)
                alVertix.add(z4)
                alVertix.add(x2)
                alVertix.add(y2)
                alVertix.add(z2)
                alVertix.add(x3)
                alVertix.add(y3)
                alVertix.add(z3)
                hAngle = hAngle - angleSpan
            }
            vAngle = vAngle - angleSpan
        }
        vCount = alVertix.size / 3//顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
        //将alVertix中的坐标值转存到一个float数组中
        val vertices = FloatArray(vCount * 3)
        for (i in alVertix.indices) {
            vertices[i] = alVertix[i]
        }
        //创建顶点坐标数据缓冲
        val vbb = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder())//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer()//转换为float型缓冲
        mVertexBuffer.put(vertices)//向缓冲区中放入顶点数据
        mVertexBuffer.position(0)//设置缓冲区起始位置
        //将alTexCoor中的纹理坐标值转存到一个float数组中
        val texCoor = generateTexCoor(//获取切分整图的纹理数组
            (360 / angleSpan).toInt(), //纹理图切分的列数
            (180 / angleSpan).toInt()  //纹理图切分的行数
        )
        val llbb = ByteBuffer.allocateDirect(texCoor.size * 4)
        llbb.order(ByteOrder.nativeOrder())//设置字节顺序
        mTexCoorBuffer = llbb.asFloatBuffer()
        mTexCoorBuffer.put(texCoor)
        mTexCoorBuffer.position(0)
    }

    fun drawSelf(texId:Int,texIdNight:Int){
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)
        GLES30.glUniformMatrix4fv(muMMatrixHandle,1,false,MatrixState.getMMatrix(),0)

        GLES30.glUniform3fv(
            maCameraHandle,1,MatrixState.cameraFB
        )
        GLES30.glUniform3fv(
            maSunLightLocationHandle,1,MatrixState.lightPositionFBSun
        )

        GLES30.glVertexAttribPointer(
            maPositionHandle,
            3,
            GLES30.GL_FLOAT,
            false,
            3*4,
            mVertexBuffer
        )
        GLES30.glVertexAttribPointer(
            maTexCoorHandle,
            2,
            GLES30.GL_FLOAT,
            false,
            2*4,
            mTexCoorBuffer
        )
        GLES30.glVertexAttribPointer(
            maNormalHandle,
            4,
            GLES30.GL_FLOAT,
            false,
            3*4,
            mVertexBuffer
        )

        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maTexCoorHandle)
        GLES30.glEnableVertexAttribArray(maNormalHandle)

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId)
        GLES30.glActiveTexture(GLES30.GL_TEXTURE1)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texIdNight)
        GLES30.glUniform1i(uDayTexHandle,0)
        GLES30.glUniform1i(uNightTexHandle,1)

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount)
    }


    //自动切分纹理产生纹理数组的办法
    public fun generateTexCoor(bw:Int,bh:Int):FloatArray{
        val result=FloatArray(bw*bh*6*2)
        val sizew=1.0f/bw
        val sizeh=1.0f/bh
        var c=0

        for(i in 0 until bh ) {
            for (j in 0 until bw) {
                //每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
                val s = j * sizew
                val t = i * sizeh//得到i行j列小矩形的左上点的纹理坐标值
                result[c++] = s
                result[c++] = t//该矩形左上点纹理坐标值
                result[c++] = s
                result[c++] = t + sizeh//该矩形左下点纹理坐标值
                result[c++] = s + sizew
                result[c++] = t            //该矩形右上点纹理坐标值
                result[c++] = s + sizew
                result[c++] = t//该矩形右上点纹理坐标值
                result[c++] = s
                result[c++] = t + sizeh//该矩形左下点纹理坐标值
                result[c++] = s + sizew
                result[c++] = t + sizeh;    //该矩形右下点纹理坐标值

            }
        }
        return result
    }

}