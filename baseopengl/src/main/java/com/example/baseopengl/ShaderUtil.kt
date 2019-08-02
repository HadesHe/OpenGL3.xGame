package com.example.baseopengl

import android.content.res.Resources
import android.opengl.GLES30
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.Exception
import java.lang.RuntimeException

object ShaderUtil {
    fun loadShader(shaderType:Int,source:String):Int{
        var shader=GLES30.glCreateShader(shaderType)
        if(shader!=0){
            GLES30.glShaderSource(shader,source)
            GLES30.glCompileShader(shader)
            val compiled=IntArray(1)

            GLES30.glGetShaderiv(shader,GLES30.GL_COMPILE_STATUS,compiled,0)

            if(compiled[0]==0){
                Log.e("GLES30 loadShader","Could not compile shader $shaderType :")
                Log.e("GLES30 loadShader",GLES30.glGetShaderInfoLog(shader))
                GLES30.glDeleteShader(shader)
                shader=0
            }
        }
        return shader
    }

    fun createProgram(vertexSource:String,fragmentSource:String):Int{
        val vertexShader= loadShader(GLES30.GL_VERTEX_SHADER,vertexSource)
        if(vertexShader==0) {
            return 0
        }
        val pixelShader= loadShader(GLES30.GL_FRAGMENT_SHADER,fragmentSource)
        if(pixelShader==0){
            return 0
        }
        var program=GLES30.glCreateProgram()
        if(program!=0) {
            GLES30.glAttachShader(program,vertexShader)
            GLES30.glAttachShader(program,pixelShader)
            GLES30.glLinkProgram(program)
            val linkStatus=IntArray(1)
            GLES30.glGetProgramiv(program,GLES30.GL_LINK_STATUS,linkStatus,0)
            if(linkStatus[0]!=GLES30.GL_TRUE){
                Log.e("GLES30 createProgram","Could not link program")
                Log.e("GLES30 createProgram",GLES30.glGetProgramInfoLog(program))
                GLES30.glDeleteProgram(program)
                program=0
            }
        }
        return program
    }

    fun loadFromAssetsFile(fname: String, r: Resources): String? {
        var result:String?=null

        if(fname== null){
            return result
        }
        var buffer:ByteArray
        var inputStream:InputStream?=null
        try{
            inputStream=r.assets.open(fname)
            buffer= ByteArray(inputStream.available())
            inputStream.read(buffer)

            val os=ByteArrayOutputStream()
            os.write(buffer)

            os.close()

            inputStream.close()

            result=os.toString()


        }catch (e:Exception){
            inputStream=null
        }

        return result
    }
}