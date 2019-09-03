package com.example.baseopengl

import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * 首先调用 setInitStack 初始化当前矩阵，否则可能会造成程序不能正常运行
 */
public object MatrixState {

    private var mProjectMatrix = FloatArray(16)
    private var mVMatrix = FloatArray(16)
    private var mMVPMatrix = FloatArray(16)
    private var currMatrix=FloatArray(16)

    //散射光光源
    var lightLocation= floatArrayOf(0f,0f,0f)
    //光源位置的缓冲
    var lightPositionFB:FloatBuffer?=null
    //待用的字节缓冲
    var llbbL=ByteBuffer.allocateDirect(3*4)

    var statckTop=-1
    var mStack:Array<FloatArray> = Array(10){FloatArray(16)}

    fun setInitStack() {
        currMatrix= FloatArray(16)
        Matrix.setRotateM(currMatrix,0,0f,1f,0f,0f)

    }

    fun pushMatrix() {
        statckTop++
        for (i in 0 until 16){
            mStack[statckTop][i]= currMatrix[i]
        }

    }

    fun popMatrix(){
        for(i in 0 until 16){
            currMatrix[i]= mStack[statckTop][i]
        }
        statckTop--
    }

    fun translate(x:Float,y:Float,z:Float){
        Matrix.translateM(currMatrix,0,x,y,z)
    }

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

    fun scale(x:Float,y:Float,z:Float){
        Matrix.scaleM(currMatrix,0,x,y,z)
    }

    fun rotate(angle:Float,x:Float,y:Float,z:Float){
        Matrix.rotateM(currMatrix,0,angle,x,y,z)
    }

    fun getFinalMatrix(spec: FloatArray): FloatArray {
        mMVPMatrix = FloatArray(16)
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0)
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mMVPMatrix, 0)
        return mMVPMatrix
    }

    fun getFinalMatrix():FloatArray{
        Matrix.multiplyMM(mMVPMatrix,0, mVMatrix,0, currMatrix,0)
        Matrix.multiplyMM(mMVPMatrix,0, mProjectMatrix,0, mMVPMatrix,0)
        return mMVPMatrix
    }

//    透视 投影
     fun setProjectFrustum(left:Float,right: Float,bottom: Float,top: Float,
                           near: Float,far: Float){
          Matrix.frustumM(mProjectMatrix,0,left,right,bottom,top, near, far)

    }

    fun getMMatrix(): FloatArray {
        return currMatrix
    }

    //设置散射光源
    fun setLightLocation(x:Float,y: Float,z: Float){
        llbbL.clear()
        lightLocation[0]=x
        lightLocation[1]=y
        lightLocation[2]=z

        llbbL.order(ByteOrder.nativeOrder())
        lightPositionFB= llbbL.asFloatBuffer()
        lightPositionFB?.let {
            it.put(lightLocation)
            it.position(0)
        }
    }

}
