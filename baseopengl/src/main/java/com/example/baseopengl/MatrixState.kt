package com.example.baseopengl

import android.opengl.Matrix

public object MatrixState {

    private var mProjectMatrix = FloatArray(16)
    private var mVMatrix = FloatArray(16)
    private var mMVPMatrix = FloatArray(16)
    fun setCamera(
        cx: Float, cy: Float, cz: Float,
        tx: Float, ty: Float, tz: Float,
        upx: Float, upy: Float, upz: Float
    ) {
        Matrix.setLookAtM(mVMatrix, 0, cx, cy, cz, tx, ty, tz, upx, upy, upz)
    }

    fun setProjectOrtho(
        left: Float, right: Float,
        bottom: Float, top: Float,
        near: Float, far: Float
    ) {
        Matrix.orthoM(mProjectMatrix, 0, left, right, bottom, top, near, far)
    }

    fun getFinalMatrix(spec: FloatArray): FloatArray {
        mMVPMatrix = FloatArray(16)
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0)
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mMVPMatrix, 0)
        return mMVPMatrix
    }

//    透视 投影
     fun setProjectFrustum(left:Float,right: Float,bottom: Float,top: Float,
                           near: Float,far: Float){
    Matrix.frustumM(mProjectMatrix,0,left,right,bottom,top, near, far)

    }

}
