package com.example.activitys

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.ToggleButton
import com.example.baseopengl.MatrixState
import com.example.glsurfaceviews.CameraSurfaceView
import com.example.sample5.R

class CameraAcitivity :AppCompatActivity(){

    private lateinit var mGLSurfaceView: CameraSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContentView(R.layout.camera_activity)

        mGLSurfaceView= CameraSurfaceView(this)
        mGLSurfaceView.requestFocus()
        mGLSurfaceView.setFocusableInTouchMode(true)

        var cl=findViewById<ConstraintLayout>(R.id.clContent)

        cl.addView(mGLSurfaceView)

        val tb=findViewById<ToggleButton>(R.id.tbCameraCheck)
        tb.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    MatrixState.setProjectFrustum(mGLSurfaceView.ratio*-0.7f,mGLSurfaceView.ratio*0.7f,
                        -0.7f,0.7f,1f,10f)
                    MatrixState.setCamera(0f,4f,4.0f,
                        0f,0f,0f,
                        0.0f,1.0f,0.0f)
                }else{
                    MatrixState.setProjectFrustum(-mGLSurfaceView.ratio,
                        mGLSurfaceView.ratio,-1.0f,1f,20f,100f)
                    MatrixState.setCamera(0f,20f,45f,
                        0f,0f,0f,
                        0f,1f,0f)

                }
            }

        })

    }

    override fun onResume() {
        super.onResume()
        mGLSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mGLSurfaceView.onPause()
    }

}