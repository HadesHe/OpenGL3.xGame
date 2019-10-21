package com.example.baseopengl

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.nio.ByteBuffer

fun convertPicsToBuffer(res:Resources,resIds:IntArray,width:Int,height:Int): ByteBuffer? {

    //每幅图数据字节数
    val perPicByteCount = width * height * 4
    //计算一组图片对应缓冲的大小，即总缓存
    val buf = ByteBuffer.allocateDirect(perPicByteCount * resIds.size)
    //遍历每一幅图
    for (i in resIds.indices) {
        val id = resIds[i]//获取每一幅图的id
        //通过输入流将图片加载到内存===============begin===================
        val `is` = res.openRawResource(id)//建立指向纹理图的流
        val bitmapTmp: Bitmap//加载后的图片对象引用
        try {
            bitmapTmp = BitmapFactory.decodeStream(`is`)    //从流中加载图片内容
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        //通过输入流加载图片===============end=====================
        buf.position(i * perPicByteCount)//设置缓冲区起始位置
        bitmapTmp.copyPixelsToBuffer(buf)//将图像的像素拷贝到总缓存
        bitmapTmp.recycle()//加载成功后释放图片
    }

    return buf        //返回加载了数据后的缓冲
}