package com.example.sample8.shapes

import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState

class Cylinder(val mv:BaseOpenGl3SurfaceView,val scale:Float,val r:Float,val h:Float,val n:Int,
               val topTexId:Int,val bottomTexId:Int,val sideTexId:Int) {

    private var cylinderSide: CylinderSide
    private var bottomCircle: Circle
    private var topCircle: Circle

    var xAngel=0f
    var yAngel=0f
    var zAngel=0f

    init {
        topCircle=Circle(mv,scale, r, n)
        topCircle.initData()
        bottomCircle=Circle(mv, scale, r, n)
        bottomCircle.initData()

        cylinderSide=CylinderSide(mv,scale, r, h, n)
        cylinderSide.initData()
    }

   fun drawSelf() {
       MatrixState.rotate(xAngel,1f,0f,0f)
       MatrixState.rotate(yAngel,0f,1f,0f)
       MatrixState.rotate(zAngel,0f,0f,1f)

       MatrixState.pushMatrix()
       MatrixState.translate(0f,h/2*scale,0f)
       MatrixState.rotate(-90f,1f,0f,0f)
       topCircle.drawSelf(topTexId)
       MatrixState.popMatrix()

       MatrixState.pushMatrix()
       MatrixState.translate(0f,-h/2*scale,0f)
       MatrixState.rotate(90f,1f,0f,0f)
       MatrixState.rotate(180f,0f,0f,1f)
       bottomCircle.drawSelf(bottomTexId)
       MatrixState.popMatrix()

       MatrixState.pushMatrix()
       MatrixState.translate(0f,-h/2*scale,0f)
       cylinderSide.drawSelf(sideTexId)
       MatrixState.popMatrix()
    }

}