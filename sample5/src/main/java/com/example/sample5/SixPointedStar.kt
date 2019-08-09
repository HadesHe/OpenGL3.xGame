package com.example.sample5

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class SixPointedStar(var orthoView: OrthoView,var radiu:Float,var R:Float,var z:Float){

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

    private fun initVertexData(radiu: Float, r: Float, z: Float) {
        var flist=ArrayList<Float>()

        for(i in 0 until 360 step 60){
            flist.add(0f)
            flist.add(0f)
            flist.add(z)

            flist.add(((R* UNIT_SIZE*Math.cos(Math.toRadians(i.toDouble()))).toFloat()))
            flist.add(((R* UNIT_SIZE*Math.sin(Math.toRadians(i.toDouble()))).toFloat()))
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

            flist.add(((R* UNIT_SIZE*Math.cos(Math.toRadians((i+60f).toDouble()))).toFloat()))
            flist.add(((R* UNIT_SIZE*Math.sin(Math.toRadians((i+60f).toDouble()))).toFloat()))
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
}