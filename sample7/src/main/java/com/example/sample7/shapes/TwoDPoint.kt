package com.example.sample7.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.abstracts.AbstractShape

class TwoDPoint(mv:BaseOpenGl3SurfaceView) :AbstractShape(mv){
    private var muMVPMatrixHandle: Int=0
    private var maPositionHandle: Int=0
    private val UNIT_SIZE=0.5f
    override fun getVertice(): FloatArray {
        val vertices= floatArrayOf(
            0f,0f,0f,

            -UNIT_SIZE,UNIT_SIZE,0f,
            UNIT_SIZE,-UNIT_SIZE,0f,
            UNIT_SIZE,UNIT_SIZE,0f,
            -UNIT_SIZE,-UNIT_SIZE,0f,

            -UNIT_SIZE*0.4f,-UNIT_SIZE*0.4f,0f,
            UNIT_SIZE*0.4f,-UNIT_SIZE*0.4f,0f,
            -UNIT_SIZE*0.4f,UNIT_SIZE*0.4f,0f,
            UNIT_SIZE*0.4f,UNIT_SIZE*0.4f,0f,

            -UNIT_SIZE,0f,0f,
            0f,-UNIT_SIZE,0f,
            UNIT_SIZE,0f,0f,
            0f,UNIT_SIZE,0f,

            -UNIT_SIZE,-UNIT_SIZE*2f,0f,
            UNIT_SIZE,-UNIT_SIZE*2f,0f,
            0f,-UNIT_SIZE*2f,0f
        )
        return  vertices
    }

    override fun getVertexName(): String {
        return "vertex_2darray.glsl"
    }

    override fun getFragName(): String {
        return "frag_2darray.glsl"
    }

    override fun initLocationHandle() {
        maPositionHandle=GLES30.glGetAttribLocation(mProgram,"aPosition")
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")

    }

    override fun drawSelf(texId: Int) {
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0)
        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,
            false,3*4,mVertexBuffer)
        GLES30.glEnableVertexAttribArray(maPositionHandle)

        GLES30.glEnable(GLES30.GL_TEXTURE_2D_ARRAY)
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D_ARRAY,texId)
        GLES30.glDrawArrays(GLES30.GL_POINTS,0,getVertice().size/3)
    }

}