package com.example.renyu.frescodemo.libs

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Administrator on 2017/6/5.
 */
class CommonUtils {
    companion object {

        val IMAGEDIR: String = Environment.getExternalStorageDirectory().absolutePath

        /**
         * 获取单帧图片
         */
        fun getGif1File(sourceFile: File) : File {
            val fileImageName=sourceFile.path.substring(sourceFile.path.lastIndexOf("/")+1, sourceFile.path.lastIndexOf("."))+System.currentTimeMillis()+ ".jpg"
            val newFilePath=File(IMAGEDIR + File.separator + fileImageName)
            if (!newFilePath.exists()) {
                newFilePath.createNewFile()
                val bmp: Bitmap = BitmapFactory.decodeFile(sourceFile.path)
                val bmpNewArray: ByteArray = getBitmapArray(bmp, 100)
                var os = FileOutputStream(newFilePath)
                os.write(bmpNewArray)
                os.flush()
                os.close()
            }
            return newFilePath
        }

        fun getBitmapArray(bitmap: Bitmap, quality: Int) : ByteArray {
            var os: ByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os)
            return os.toByteArray()
        }

        /**
         * 获取rect范围内的Bitmap
         */
        fun getDecodeRegion(bytes: ByteArray, width: Int, height: Int) : Bitmap {
            val decodeRegion: BitmapRegionDecoder = BitmapRegionDecoder.newInstance(bytes, 0, bytes.size, true)
            val rect = Rect(0, 0, width, height)
            return decodeRegion.decodeRegion(rect, null)
        }
    }
}