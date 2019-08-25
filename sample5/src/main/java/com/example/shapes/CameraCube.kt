package com.example.shapes

import com.example.baseopengl.MatrixState
import com.example.glsurfaceviews.CameraSurfaceView


class CameraCube(mv:CameraSurfaceView) {
    private var cr: ColorRect
    private val UNIT_SIZE=1.0f

    init {
        cr=ColorRect(mv)
    }

    fun drawSelf(){
        MatrixState.pushMatrix()
        MatrixState.pushMatrix()
        MatrixState.translate(0f,0f,UNIT_SIZE)
        cr.drawSelf()
        MatrixState.popMatrix()

        MatrixState.pushMatrix()
        MatrixState.translate(0f,0f,-UNIT_SIZE)
        MatrixState.rotate(180f,0f,1f,0f)
        cr.drawSelf()
        MatrixState.popMatrix()

        MatrixState.pushMatrix()
        MatrixState.translate(0f, UNIT_SIZE, 0f)
        MatrixState.rotate(-90f, 1f, 0f, 0f)
        cr.drawSelf()
        MatrixState.popMatrix()

        //�����´���
        MatrixState.pushMatrix()
        MatrixState.translate(0f, -UNIT_SIZE, 0f)
        MatrixState.rotate(90f, 1f, 0f, 0f)
        cr.drawSelf()
        MatrixState.popMatrix()

        //���������
        MatrixState.pushMatrix()
        MatrixState.translate(UNIT_SIZE, 0f, 0f)
        MatrixState.rotate(-90f, 1f, 0f, 0f)
        MatrixState.rotate(90f, 0f, 1f, 0f)
        cr.drawSelf()
        MatrixState.popMatrix()

        //�����Ҵ���
        MatrixState.pushMatrix()
        MatrixState.translate(-UNIT_SIZE, 0f, 0f)
        MatrixState.rotate(90f, 1f, 0f, 0f)
        MatrixState.rotate(-90f, 0f, 1f, 0f)
        cr.drawSelf()
        MatrixState.popMatrix()

        //�ָ��ֳ�
        MatrixState.popMatrix()

    }

}