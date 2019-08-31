package com.example.shapes

import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.baseopengl.shapes.BaseColorRect
import com.example.baseopengl.shapes.BaseCustomCube

//class CustomCube(mv:BaseOpenGl3SurfaceView,unitSize:Float,color:FloatArray):BaseCustomCube(mv,unitSize,color){
//    private var cr:BaseColorRect
//
//    init {
//        cr=NearColorRect(mv,unitSize,colorIn = )
//    }
//
//    override fun drawSelf() {
//
//        MatrixState.pushMatrix()
//
//        MatrixState.pushMatrix()
//        MatrixState.translate(0f,0f,unitSize)
//        cr.drawSelf()
//        MatrixState.popMatrix()
//
//        MatrixState.pushMatrix()
//        MatrixState.translate(0f,0f,-unitSize)
//        MatrixState.rotate(180f,0f,1f,0f)
//        cr.drawSelf()
//        MatrixState.popMatrix()
//
//        MatrixState.pushMatrix()
//        MatrixState.translate(0f, unitSize, 0f)
//        MatrixState.rotate(-90f, 1f, 0f, 0f)
//        cr.drawSelf()
//        MatrixState.popMatrix()
//
//        //�����´���
//        MatrixState.pushMatrix()
//        MatrixState.translate(0f, -unitSize, 0f)
//        MatrixState.rotate(90f, 1f, 0f, 0f)
//        cr.drawSelf()
//        MatrixState.popMatrix()
//
//        //���������
//        MatrixState.pushMatrix()
//        MatrixState.translate(unitSize, 0f, 0f)
//        MatrixState.rotate(-90f, 1f, 0f, 0f)
//        MatrixState.rotate(90f, 0f, 1f, 0f)
//        cr.drawSelf()
//        MatrixState.popMatrix()
//
//        //�����Ҵ���
//        MatrixState.pushMatrix()
//        MatrixState.translate(-unitSize, 0f, 0f)
//        MatrixState.rotate(90f, 1f, 0f, 0f)
//        MatrixState.rotate(-90f, 0f, 1f, 0f)
//        cr.drawSelf()
//        MatrixState.popMatrix()
//
//        //�ָ��ֳ�
//        MatrixState.popMatrix()
//
//
//
//
//    }
//
//}