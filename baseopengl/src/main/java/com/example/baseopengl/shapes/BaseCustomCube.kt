package com.example.baseopengl.shapes

import com.example.baseopengl.BaseOpenGl3SurfaceView

abstract class BaseCustomCube(var mv:BaseOpenGl3SurfaceView,var unitSize:Float,var color:FloatArray){

    abstract fun drawSelf()
}