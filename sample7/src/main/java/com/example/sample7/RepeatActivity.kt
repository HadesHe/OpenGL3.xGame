package com.example.sample7

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RadioButton
import com.example.sample7.surfaceviews.RepeatView

class RepeatActivity :AppCompatActivity(){

    private lateinit var mGLSurfaceView: RepeatView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_repeat)

        mGLSurfaceView= RepeatView(this@RepeatActivity)
        mGLSurfaceView.requestFocus()
        mGLSurfaceView.setFocusableInTouchMode(true)

        val clContent=findViewById<LinearLayout>(R.id.clContent)
        clContent.addView(mGLSurfaceView)

        var rbEdge=findViewById<RadioButton>(R.id.rbEdge)
        rbEdge.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mGLSurfaceView.currTextureId=mGLSurfaceView.textureCTId
            }
        }

        rbEdge=findViewById(R.id.rbRepeat)
        rbEdge.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mGLSurfaceView.currTextureId=mGLSurfaceView.textureREId
            }
        }

        rbEdge=findViewById(R.id.rbMirror)
        rbEdge.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mGLSurfaceView.currTextureId=mGLSurfaceView.textureMIId
            }
        }

        var rb=findViewById<RadioButton>(R.id.rbx11)
        rb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                mGLSurfaceView.trIndex=0
            }
        }

        rb=findViewById(R.id.rbx42)
        rb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mGLSurfaceView.trIndex=1
            }
        }

        rb=findViewById(R.id.rbx44)
        rb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mGLSurfaceView.trIndex=2
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mGLSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mGLSurfaceView.onResume()
    }

}