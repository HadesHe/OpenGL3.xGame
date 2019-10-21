package com.example.baseopengl

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.nio.ByteBuffer

fun convertPicsToBuffer(res:Resources,resIds:IntArray,width:Int,height:Int): ByteBuffer? {

    //ÿ��ͼ�����ֽ���
    val perPicByteCount = width * height * 4
    //����һ��ͼƬ��Ӧ����Ĵ�С�����ܻ���
    val buf = ByteBuffer.allocateDirect(perPicByteCount * resIds.size)
    //����ÿһ��ͼ
    for (i in resIds.indices) {
        val id = resIds[i]//��ȡÿһ��ͼ��id
        //ͨ����������ͼƬ���ص��ڴ�===============begin===================
        val `is` = res.openRawResource(id)//����ָ������ͼ����
        val bitmapTmp: Bitmap//���غ��ͼƬ��������
        try {
            bitmapTmp = BitmapFactory.decodeStream(`is`)    //�����м���ͼƬ����
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        //ͨ������������ͼƬ===============end=====================
        buf.position(i * perPicByteCount)//���û�������ʼλ��
        bitmapTmp.copyPixelsToBuffer(buf)//��ͼ������ؿ������ܻ���
        bitmapTmp.recycle()//���سɹ����ͷ�ͼƬ
    }

    return buf        //���ؼ��������ݺ�Ļ���
}