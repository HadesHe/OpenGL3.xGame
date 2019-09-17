package com.example.sample7.surfaceviews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLUtils
import android.view.MotionEvent
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.sample7.R
import com.example.sample7.shapes.TextureRect
import java.io.IOException
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class RepeatView(context: Context):BaseOpenGl3SurfaceView(context) {
    var textureMIId=0
    var textureREId=0
    var textureCTId=0
    var trIndex=2
    var currTextureId=0
    private val TOUCH_SCALE_FACTOR=180.0f/320.toFloat()
    private var mPreviousX:Float=0f
    private var mPreviousY: Float=0f

    var texRect= Array<TextureRect?>(3,{null})
    override fun getRender(): Renderer {
        return RepeatRenderer()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            var y=it.y
            var x=it.x
            when(it.action){
                MotionEvent.ACTION_MOVE->{
                    var dy=y-mPreviousY
                    var dx=x-mPreviousX

                    texRect?.let {
                        it.forEach {
                            it!!.yAngle += dx * TOUCH_SCALE_FACTOR
                            it!!.zAngle += dy * TOUCH_SCALE_FACTOR
                        }
                    }

                }
            }
            mPreviousY=y
            mPreviousX=x
            return true
        }
        return true
    }

    inner class RepeatRenderer : Renderer {
        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)
            texRect[trIndex]?.drawSelf(currTextureId)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0,0,width, height)
            val ratio=width.toFloat()/height.toFloat()
            MatrixState.setProjectFrustum(-ratio,ratio,-1f,1f,1f,10f)
            MatrixState.setCamera(0f,0f,3f,0f,0f,0f,0f,1f,0f)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(1f,1f,1f,1.0f)

            texRect[0]=TextureRect(this@RepeatView,1f,1f)
            texRect[1]=TextureRect(this@RepeatView,4f,2f)
            texRect[2]=TextureRect(this@RepeatView,4f,4f)

            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            textureCTId=initTexture(1)
            textureREId=initTexture(0)
            textureMIId=initTexture(2)

            currTextureId=textureREId
            GLES30.glDisable(GLES30.GL_CULL_FACE)
        }
    }

    fun initTexture(repeatIndex:Int):Int{
        var textures=IntArray(1)
        GLES30.glGenTextures(1,textures,0)

        val textureId=textures[0]
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,textureId)
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST.toFloat())
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR.toFloat())

        when(repeatIndex){
            0-> {
                GLES30.glTexParameterf(
                    GLES30.GL_TEXTURE_2D,
                    GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT.toFloat()
                )
                GLES30.glTexParameterf(
                    GLES30.GL_TEXTURE_2D,
                    GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT.toFloat()
                )
            }
            1->{
                GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,
                    GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE.toFloat())
                GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,
                    GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE.toFloat())
            }
            2->{
                GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,
                    GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_MIRRORED_REPEAT.toFloat())
                GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,
                    GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_MIRRORED_REPEAT.toFloat())

            }
        }

        //ͨ������������ͼƬ===============begin===================
        val `is` = this.resources.openRawResource(R.raw.timg2)
        val bitmapTmp: Bitmap
        try {
            bitmapTmp = BitmapFactory.decodeStream(`is`)
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        GLUtils.texImage2D(
            GLES30.GL_TEXTURE_2D,
            0,
            bitmapTmp,
            0
        )
        bitmapTmp.recycle()
        return textureId
    }


}
