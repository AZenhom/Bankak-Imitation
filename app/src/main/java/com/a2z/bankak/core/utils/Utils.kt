package com.a2z.bankak.core.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.NumberFormat
import java.util.*


fun Number.formatNumber(): String {
    return NumberFormat.getNumberInstance(Locale.US).format(this)
}

fun View.convertToBitmap(backgroundColor: Int): Bitmap? {
    Log.d("convertToBitmap", "${this.width} ${this.height}")
    val bitmap =
        Bitmap.createBitmap(this.width, this.height, Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(backgroundColor);
    this.layout(this.left, this.top, this.right, this.bottom)
    this.draw(canvas)
    return bitmap
}

fun Bitmap.saveImage(imageName: String, context: Context): Uri? {
    val filename = "$imageName.jpg"

    var fos: OutputStream? = null
    var uri: Uri? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let {
                uri = it
                resolver.openOutputStream(it)
            }
        }
    } else {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        uri = Uri.fromFile(image)
        fos = FileOutputStream(image)
    }
    fos?.use {
        this.compress(Bitmap.CompressFormat.JPEG, 100, it)
        return uri
    }
    return null
}

fun Uri.shareVia(context: Context) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_STREAM, this)
    intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image")
    intent.putExtra(Intent.EXTRA_SUBJECT, "Choose an application")
    intent.type = "image/jpg"
    context.startActivity(Intent.createChooser(intent, "Share Via"))
}