package com.example.sample7.surfaceviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLUtils
import android.view.MotionEvent
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.sample7.R
import com.example.sample7.shapes.Triangle
import java.io.IOException
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MySurfaceView(context: Context) :BaseOpenGl3SurfaceView(context){
    private val TOUCH_SCALE_FACTOR=180/320.toFloat()
    private var mPreviousX=0f
    private var mPreviousY=0f
    private var textureId: Int=0

    private lateinit var sceneRender:SceneRender

    override fun getRender(): Renderer {
        sceneRender=SceneRender()
        return sceneRender

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val y=it.y
            val x=it.x
            when(it.action){
                MotionEvent.ACTION_MOVE->{
                    val dy=y-mPreviousY
                    val dx=x-mPreviousX
                    sceneRender.texRect.yAngle=dx*TOUCH_SCALE_FACTOR
                    sceneRender.texRect.xAngle=dy*TOUCH_SCALE_FACTOR


                }
            }
            mPreviousY=y
            mPreviousX=x
        }
        return true
    }

    fun initTexture(){
        val textures=IntArray(1)
        GLES30.glGenTextures(
            1,textures,0
        )
        textureId=textures[0]
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,textureId)
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST.toFloat())
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR.toFloat())
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE.toFloat())
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE.toFloat())

        val imgIs=resources.openRawResource(R.raw.wall)

        var bitmapTmp:Bitmap?=null
        try {
            bitmapTmp=BitmapFactory.decodeStream(imgIs)
        }catch (e:Exception){

        }
        finally {
            try {
                imgIs.close()
            }catch (e:IOException){
                e.printStackTrace()
            }
        }
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D,0,bitmapTmp,0)
        bitmapTmp?.recycle()

    }

    inner class SceneRender:Renderer{
         lateinit var texRect: Triangle

        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)
            texRect.drawSelf(textureId)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0,0,width, height)
            val ratio=width/height.toFloat()
            MatrixState.setProjectFrustum(-ratio,ratio,-1f,1f,1f,10f)
            MatrixState.setCamera(0f,0f,5f,0f,0f,0f,0f,1f,0f)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(1.0f,1.0f,1.0f,1.0f)
            texRect=Triangle(this@MySurfaceView)
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            initTexture()
            GLES30.glDisable(GLES30.GL_CULL_FACE)
        }

    }

}