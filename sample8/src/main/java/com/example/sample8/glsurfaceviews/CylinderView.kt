package com.example.sample8.glsurfaceviews

import android.content.Context
import com.example.baseopengl.BaseOpenGl3SurfaceView
import com.example.baseopengl.abstracts.AbstractRender

class CylinderView(context:Context) :BaseOpenGl3SurfaceView(context){
    override fun getRender(): Renderer {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class CylinderRender:AbstractRender(){
        override fun onRenderCreated() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onRenderChanged(width: Int, height: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onRenderDrawed() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}