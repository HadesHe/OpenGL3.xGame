package com.example.shapes

import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import java.nio.Buffer
import java.nio.ByteBuffer

class DrawElementCircle(mv:BaseOpenGl3SurfaceView) :Circle(mv){

    private lateinit var mIndexBuffer: Buffer

    override fun initVertData() {
        super.initVertData()
        val incides= byteArrayOf(
            0,1,2,
            0,2,3,
            0,3,4,
            0,4,5,
            0,5,6,
            0,6,7,
            0,7,8,
            0,8,9,
            0,9,10,
            0,10,1
        )

        mIndexBuffer=ByteBuffer.allocateDirect(incides.size)
            .put(incides)
            .position(0)
    }

    override fun drawSelf() {
        GLES30.glUseProgram(mProgram)
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,
            MatrixState.getFinalMatrix(),0)
        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,
            false,3*4,mVertexBuffer)
        GLES30.glVertexAttribPointer(maColorHandle,4,GLES30.GL_FLOAT,false,4*4,mColorBuffer)
        GLES30.glEnableVertexAttribArray(maPositionHandle)
        GLES30.glEnableVertexAttribArray(maColorHandle)
        GLES30.glDrawElements(GLES30.GL_TRIANGLES,10*3,GLES30.GL_UNSIGNED_BYTE,mIndexBuffer)
    }

}