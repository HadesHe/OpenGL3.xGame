package com.example.glsurfaceviews

import android.content.Context
import android.opengl.GLES30
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.MatrixState
import com.example.shapes.Circle
import com.example.shapes.DrawElementCircle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class DrawRangeElementsView(context: Context) :BaseOpenGl3SurfaceView(context){
    override fun getRender(): Renderer {
        return DrawRangeElementsRender()
    }

    inner class DrawRangeElementsRender : Renderer {
        private lateinit var circle: Circle

        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClear(
                GLES30.GL_DEPTH_BUFFER_BIT	//�����Ȼ�������ɫ����
     or GLES30.GL_COLOR_BUFFER_BIT
            )
    		MatrixState.pushMatrix()							//�����ֳ�
    		//����Բ
    		MatrixState.pushMatrix()							//�����ֳ�
    		MatrixState.translate(-1.2f, 0f, 0f)					//��x������ƽ��
    		circle.drawSelf(0, 24)							//����Բ
    		MatrixState.popMatrix()							//�ָ��ֳ�
    		//���ư��Բ
    		MatrixState.pushMatrix()							//�����ֳ�
    		MatrixState.translate(1.2f, 0f, 0f)					//��x������ƽ��
    		circle.drawSelf(6, 12)							//���ư��Բ
    		MatrixState.popMatrix()							//�ָ��ֳ�
    		MatrixState.popMatrix()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0,0,width, height)
            val ratio=width/height.toFloat()
            MatrixState.setProjectFrustum(-ratio,ratio,-1f,1f,20f,100f)
            MatrixState.setCamera(-32f,16f,90f,0f,0f,0f,0f,1.0f,0.0f)
            MatrixState.setInitStack()
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glClearColor(0.5f,0.5f,0.5f,1.0f)
            circle= DrawElementCircle(this@DrawRangeElementsView)
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            GLES30.glEnable(GLES30.GL_CULL_FACE)
        }

    }

}