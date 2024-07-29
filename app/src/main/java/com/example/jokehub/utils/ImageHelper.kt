package com.example.jokehub.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.example.jokehub.databinding.ItemJokeListBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ImageHelper(context: Context) {
    private val mContext = context


    fun textViewToImage(binding: ItemJokeListBinding): Uri {
        val bitmap = Bitmap.createBitmap(
            binding.textView.width,
            binding.textView.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        binding.textView.draw(canvas)
        val uri = getImageFromBitmap(bitmap)
        return uri
    }


    fun getImageFromBitmap(bitmap: Bitmap): Uri {

        /*  val bytes: ByteArrayOutputStream = ByteArrayOutputStream()
          bitMap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
          val path: String =
              MediaStore.Images.Media.insertImage(mContext.contentResolver, bitMap, "Title", null)
          return Uri.parse(path)*/
        /* val filePath = "/path/to/save/image.jpeg"
         var outputStream: FileOutputStream? = null
         try {
             outputStream = FileOutputStream(filePath)
             bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
         } catch (e: FileNotFoundException) {
             e.printStackTrace()
         } finally {
             try {
                 outputStream?.close()
             } catch (e: IOException) {
                 e.printStackTrace()
             }
         }*/
        val file = File(mContext.externalCacheDir, "text_image.png")
        try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("prabhat5555","we are getting error here.")
        }
        val contentUri = FileProvider.getUriForFile(mContext, "${mContext.packageName}.fileprovider", file)

        return contentUri
    }
}