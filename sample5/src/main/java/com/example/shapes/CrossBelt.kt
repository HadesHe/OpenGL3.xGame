package com.example.shapes

import com.example.baseopengl.BaseOpenGl3SurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CrossBelt(mv:BaseOpenGl3SurfaceView) :Belt(mv){

    override fun initVertData() {
        val n1=6
        val n2=10

        vCount=2*(n1+n2+2)+2

        var angleBegin1=-45f
        var angleEnd1=45f

        var angleSpan1=(angleEnd1-angleBegin1)/n1


        var angleBegin2=135f
        var angleEnd2=225f

        var angleSpan2=(angleEnd2-angleBegin2)/n2

        var count=0
        var angdeg=angleBegin1
        val vertices=FloatArray(vCount*3)
        while(angdeg<=angleEnd1){
            var angrad=Math.toRadians(angdeg.toDouble())
            vertices[count++]=(-0.6f*UNIT_SIZE*Math.sin(angrad)).toFloat()
            vertices[count++]=(0.6f*UNIT_SIZE*Math.cos(angrad)).toFloat()
            vertices[count++]=0f

            vertices[count++]=(-UNIT_SIZE*Math.sin(angrad)).toFloat()
            vertices[count++]=(UNIT_SIZE*Math.cos(angrad)).toFloat()
            vertices[count++]=0f
            angdeg+=angleSpan1
        }

        vertices[count++]=vertices[count-4]
        vertices[count++]=vertices[count-4]
        vertices[count++]=0f

        angdeg=angleBegin2

        while (angdeg<=angleEnd2){
            val angrad=Math.toRadians(angdeg.toDouble())
            if(angdeg==angleBegin2){
                vertices[count++]=(-0.6f*UNIT_SIZE*Math.sin(angrad)).toFloat()
                vertices[count++]=(0.6f*UNIT_SIZE*Math.cos(angrad)).toFloat()
                vertices[count++]=0f
            }
            vertices[count++]=(-0.6f*UNIT_SIZE*Math.sin(angrad)).toFloat()
            vertices[count++]=(0.6f*UNIT_SIZE*Math.cos(angrad)).toFloat()
            vertices[count++]=0f

            vertices[count++]=(-UNIT_SIZE*Math.sin(angrad)).toFloat()
            vertices[count++]=(UNIT_SIZE*Math.cos(angrad)).toFloat()
            vertices[count++]=0f

            angdeg+=angleSpan2
        }

        mVertexBuffer=ByteBuffer.allocateDirect(vertices.size*4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mVertexBuffer.put(vertices)
        mVertexBuffer.position(0)

        count=0
        var colors=FloatArray(vCount*4)
        var i=0
        while(i<colors.size){
            colors[count++]=1f
            colors[count++]=1f
            colors[count++]=1f
            colors[count++]=0f

            colors[count++]=0f
            colors[count++]=1f
            colors[count++]=1f
            colors[count++]=0f
            i+=8

        }

        mColorBuffer=ByteBuffer.allocateDirect(colors.size*4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mColorBuffer.put(colors)
        mColorBuffer.position(0)

    }

}