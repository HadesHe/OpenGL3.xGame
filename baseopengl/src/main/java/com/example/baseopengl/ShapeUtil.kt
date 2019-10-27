package com.example.baseopengl

object ShapeUtil {


    /**
     * 绘制方式为 GL_TEXTURE_FAN：扇形绘制
     * 获取3维空间圆面的顶点坐标
     * vertivesCount:定点数
     * n:圆形分割的份数
     * R:原型半径
     * 顺时针切
     */
    fun get3DCircleVertices(verticesCount:Int,n:Int,R:Float,initX:Float=0f,initY:Float=0f):FloatArray?{

        if(verticesCount<=0||n<=0||R<=0){
            return null
        }

        val vertices=FloatArray(verticesCount)
        val angdegSpan=360.0f/n
        var count=0
        var angdeg=0f
        while (Math.ceil(angdeg.toDouble()) < 360) {//生成每个三角形的顶点数据
            val angrad = Math.toRadians(angdeg.toDouble())//当前弧度
            val angradNext = Math.toRadians((angdeg + angdegSpan).toDouble())//下一弧度
            //圆面中心点的顶点坐标
            vertices[count++] = initX//顶点坐标
            vertices[count++] = initY
            vertices[count++] = 0f
            //当前弧度对应的边缘顶点坐标
            vertices[count++] = (-R * Math.sin(angrad)).toFloat()+initX//顶点坐标
            vertices[count++] = (R * Math.cos(angrad)).toFloat()+initY
            vertices[count++] = 0f
            //当前弧度对应的边缘顶点纹理坐标
            //下一弧度对应的边缘顶点坐标
            vertices[count++] = (-R * Math.sin(angradNext)).toFloat()+initX//顶点坐标
            vertices[count++] = (R* Math.cos(angradNext)).toFloat()+initY
            vertices[count++] = 0f
            //下一弧度对应的边缘顶点纹理坐标
            angdeg += angdegSpan
        }

        return  vertices

    }
}