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
import com.example.sample7.shapes.Celestial
import com.example.sample7.shapes.Earth
import com.example.sample7.shapes.Moon
import java.io.IOException
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class EarthView(context: Context) :BaseOpenGl3SurfaceView(context){
    var threadFlag=true
    private var mPreviousY=0f
    //太阳灯光线绕 y 轴旋转的角度
    private var yAngle: Float=0f
    //摄像机绕 X 轴旋转的角度
    private var xAngle: Float=0f

    //地球自转角度
    private var eAngle=0f
    //天球自转角度
    private var cAngle=0f

    //系统分配的地球纹理 id
    private var textureIdEarth=0
    //系统分配的地球夜晚纹理 id
    private var textureIdEarthNight=0
    //系统分配的月球 id
    private var textureIdMoon=0
    private var mPreviousX=0f
    private val TOUCH_SCALE_FACTOR=180.0f/320

    override fun getRender(): Renderer {
        return EarthRender()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x=event.x
        var y=event.y
        when(event.action){
            MotionEvent.ACTION_MOVE->{
                var dx=x-mPreviousX
                yAngle+=dx*TOUCH_SCALE_FACTOR

                val sunx:Float= (Math.cos(Math.toRadians(yAngle.toDouble()))*100).toFloat()
                val sunz:Float= (-(Math.sin(Math.toRadians(yAngle.toDouble())*100))).toFloat()

                MatrixState.setLightLocationSun(sunx,5f,sunz)

                var dy=y-mPreviousY
                xAngle+=dy*TOUCH_SCALE_FACTOR

                if(xAngle>90){
                    xAngle=90f
                }else if(xAngle<-90){
                    xAngle=-90f
                }

                val cy:Float= (7.2*Math.sin(Math.toRadians(xAngle.toDouble()))).toFloat()
                val cz:Float= (7.2*Math.cos(Math.toRadians(xAngle.toDouble()))).toFloat()
                val upy:Float= (Math.cos(Math.toRadians(xAngle.toDouble()))).toFloat()
                val upz:Float= (Math.sin(Math.toRadians(xAngle.toDouble()))).toFloat()

                MatrixState.setCamera(0f,cy,cz,0f,0f,0f,0f,upy, upz)
            }
        }
        mPreviousX=x
        mPreviousY=y
        return true
    }

    inner class EarthRender:Renderer{
        private lateinit var cBig: Celestial
        private lateinit var cSmall: Celestial
        private lateinit var moon: Moon
        private lateinit var earth: Earth

        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)

            MatrixState.pushMatrix()

            MatrixState.rotate(eAngle,0f,1f,0f)
            earth.drawSelf(textureIdEarth,textureIdEarthNight)
            MatrixState.translate(2f,0f,0f)
            MatrixState.rotate(eAngle,0f,1f,0f)
            moon.drawSelf(textureIdMoon)

            MatrixState.popMatrix()

            MatrixState.pushMatrix()
            MatrixState.rotate(cAngle,0f,1f,0f)
            cSmall.drawSelf()
            cBig.drawSelf()
            MatrixState.popMatrix()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0,0,width, height)
            val ratio=width/height.toFloat()
            MatrixState.setProjectFrustum(-ratio,ratio,-1f,1f,4f,100f)
            MatrixState.setCamera(0f,0f,7.5f,0f,0f,0f,0f,1.0f,0f)
            GLES30.glEnable(GLES30.GL_CULL_FACE)

            textureIdEarth=initTexture(R.raw.earth)
            textureIdEarthNight=initTexture(R.raw.earthn)
            textureIdMoon=initTexture(R.raw.moon)

            Thread({
                while (threadFlag){
                    eAngle=(eAngle+2)%360
                    cAngle=(cAngle+0.2f)%360

                    try {
                        Thread.sleep(100)
                    }catch (e:InterruptedException){
                        e.printStackTrace()
                    }
                }
            }).start()
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(0.0f,0.0f,0.0f,1.0f)
            earth=Earth(this@EarthView,2.0f)
            earth.initData()
            moon=Moon(this@EarthView,1.0f)
            moon.initData()

            cSmall=Celestial(this@EarthView,1f,0f,1000)
            cSmall.initData()

            cBig=Celestial(this@EarthView,2f,0f,500)
            cBig.initData()

            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            MatrixState.setInitStack()
        }

    }

    fun initTexture(drawableId:Int):Int{
        var textures=IntArray(1)
        GLES30.glGenTextures(1,textures,0)

        val textureId=textures[0]
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,textureId)
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST.toFloat())
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR.toFloat())
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE.toFloat())
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE.toFloat())

        //ͨ������������ͼƬ===============begin===================
        val `is` = this.resources.openRawResource(drawableId)
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