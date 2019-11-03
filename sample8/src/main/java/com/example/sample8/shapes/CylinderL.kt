package com.example.sample8.shapes

import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState

class CylinderL(mv: BaseOpenGl3SurfaceView, val scale: Float, val r: Float, val h: Float, val n: Int) {
    var xAngle: Float = 0f
    var yAngle: Float = 0f
    var zAngle: Float = 0f
    private var cylinderSide: CylinderSideL
    private var bottomCircle: CircleL
    private var topCircle: CircleL

    init {
        topCircle = CircleL(mv, scale, r, n)
        topCircle.initData()
        bottomCircle = CircleL(mv, scale, r, n)
        bottomCircle.initData()
        cylinderSide = CylinderSideL(mv, scale, r, h, n)
        cylinderSide.initData()

    }

    public fun drawSelf() {
        MatrixState.rotate(xAngle, 1f, 0f, 0f)
        MatrixState.rotate(yAngle, 0f, 1f, 0f)
        MatrixState.rotate(zAngle, 0f, 0f, 1f)

        MatrixState.pushMatrix()

        MatrixState.translate(0f,h/2*scale,0f)
        MatrixState.rotate(-90f,1f,0f,0f)
        topCircle.drawSelf(0)
        MatrixState.popMatrix()

        MatrixState.pushMatrix()
        MatrixState.translate(0f,-h/2*scale,0f)
        MatrixState.rotate(90f,1f,0f,0f)
        MatrixState.rotate(180f,0f,0f,1f)
        bottomCircle.drawSelf(0)
        MatrixState.popMatrix()

        MatrixState.pushMatrix()
        MatrixState.translate(0f,-h/2*scale,0f)
        cylinderSide.drawSelf(0)
        MatrixState.popMatrix()
    }
}