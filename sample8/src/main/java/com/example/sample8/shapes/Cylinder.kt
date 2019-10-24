package com.example.sample8.shapes

import com.example.baseopengl.BaseOpenGl3SurfaceView

class Cylinder(val mv:BaseOpenGl3SurfaceView,val scale:Float,val r:Float,val h:Float,val n:Int,
               val topTexId:Int,val bottomTexId:Int,val sideTexId:Int) {

    private var bottomCircle: Circle
    private var topCircle: Circle

    init {
        topCircle=Circle(mv,scale, r, n)
        bottomCircle=Circle(mv, scale, r, n)
        // TODO: 2019-10-24 增加侧面
    }

   fun drawSelf() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}