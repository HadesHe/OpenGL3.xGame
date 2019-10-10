package com.example.baseopengl.abstracts

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import java.io.IOException
import java.nio.ByteBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

 abstract class AbstractRender :GLSurfaceView.Renderer{
    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(
            GLES30.GL_DEPTH_BUFFER_BIT
        or GLES30.GL_COLOR_BUFFER_BIT
        )
        onRenderDrawed()
    }

     override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0,0,width, height)
        onRenderChanged(width,height)
    }

     override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(0f,0f,0f,1f)
        GLES30.glEnable(GLES30.GL_DEPTH_TEST)
        GLES30.glEnable(GLES30.GL_CULL_FACE)
        onRenderCreated()
    }

     abstract fun onRenderCreated()
     abstract fun onRenderChanged(width: Int,height: Int)
     abstract fun onRenderDrawed()

     protected fun initTexture(context: Context, picId:Int): Int {
         var textures=IntArray(1)
         GLES30.glGenTextures(1,textures,0)

         val textureId=textures[0]
         GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,textureId)
         GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST.toFloat())
         GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR.toFloat())
         GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE.toFloat())
         GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE.toFloat())

         //ͨ������������ͼƬ===============begin===================
         val `is` = context.resources.openRawResource(picId)
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

     /**
      * 加载3D 纹理
      * 返回3D 纹理 ID
      */
     protected fun init3DTexture(texData:ByteArray,width: Int,height: Int,depth:Int): Int {
         val textures=IntArray(1)
         GLES30.glGenTextures(1,textures,0)
         val textureId=textures[0]
         GLES30.glBindTexture(GLES30.GL_TEXTURE_3D,textureId)
         GLES30.glTexParameterf(GLES30.GL_TEXTURE_3D,GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST.toFloat())
         GLES30.glTexParameterf(GLES30.GL_TEXTURE_3D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_NEAREST.toFloat())
         GLES30.glTexParameterf(GLES30.GL_TEXTURE_3D,GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_REPEAT.toFloat())
         GLES30.glTexParameterf(GLES30.GL_TEXTURE_3D,GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_REPEAT.toFloat())
         GLES30.glTexParameterf(GLES30.GL_TEXTURE_3D,GLES30.GL_TEXTURE_WRAP_R,GLES30.GL_REPEAT.toFloat())

         val texels=ByteBuffer.allocateDirect(texData.size)
         texels.put(texData)
         texels.position(0)

         GLES30.glTexImage3D(
             GLES30.GL_TEXTURE_3D,
             0,
             GLES30.GL_RGBA8,
             width,
             height,
             depth,
             0,
             GLES30.GL_RGBA,
             GLES30.GL_UNSIGNED_BYTE,
             texels
         )

         return textureId
     }

 }