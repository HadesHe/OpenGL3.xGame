package com.example.sample7.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.abstracts.AbstractShape

class Points(mv:BaseOpenGl3SurfaceView) : AbstractShape(mv){
    private var muMVPMatrixHandle: Int=0
    private var maPositionHandle: Int=0
    private val unit_size=0.5f


    private val vertices= floatArrayOf(
        0f,0f,0f,
        0f,unit_size*2,0f,
        unit_size,unit_size/2,0f,
        -unit_size/3,unit_size,0f,
        -unit_size*0.4f,-unit_size*0.4f,0f,
        -unit_size,-unit_size,0f,
        unit_size*0.2f,-unit_size*0.7f,0f,
        unit_size/2,-unit_size*3/2,0f,
        -unit_size*4/5,-unit_size*3/2,0f
    )

    override fun getVertice(): FloatArray {
        return vertices
    }

    override fun getVertexName(): String {
        return "vertex_point.glsl"
    }

    override fun getFragName(): String {
        return "frag_point.glsl"
    }

    override fun initLocationHandle() {
        maPositionHandle= GLES30.glGetAttribLocation(mProgram,"aPosition")
        muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram,"uMVPMatrix")
    }

    override fun drawSelf(texId: Int) {
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,
            MatrixState.getFinalMatrix(),0)
        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,
            false,3*4,mVertexBuffer)
        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnable(GLES30.GL_TEXTURE_2D)
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId)

        GLES30.glDrawArrays(GLES30.GL_POINTS,0,vertices.size/3)
    }

}