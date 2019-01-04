package com.smenu.android.checkin

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.webkit.PermissionRequest
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private val TAKE_PHOTO_REQUEST = 101
    var now = 0;
    var barr = arrayOf(R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5, R.drawable.b6, R.drawable.b7, R.drawable.b8, R.drawable.b9, R.drawable.b10, R.drawable.b11, R.drawable.b12)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.click).setBackgroundResource(barr[now]);
        var b = findViewById<View>(R.id.click);
        findViewById<View>(R.id.click).setOnClickListener(){v -> updateBackground(b, 1)}
        findViewById<View>(R.id.back).setOnClickListener(){v -> updateBackground(b, -1)}
    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    fun updateBackground(v: View, d: Int) {
        now = now + d
        if (now == barr.size) now = 0
        if (now == -1) now = 0
        if (now == 3) {
           if (launchCamera()) {
               v.setBackgroundResource(barr[now]);
           }
        } else {
            v.setBackgroundResource(barr[now]);
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("now", now)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        now = savedInstanceState.getInt("now")
    }


    private fun launchCamera(): Boolean {
        val intent = Intent("com.google.zxing.client.android.SCAN")
        intent.setPackage("com.google.zxing.client.android")
        intent.putExtra("SCAN_MODE", "SCAN_MODE")
        startActivityForResult(intent, 0)
        return true
        /*
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 23)
            return false;
        } else {
            val values = ContentValues(1)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
            val fileUri = contentResolver
                .insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent.resolveActivity(packageManager) != null) {
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                intent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                startActivityForResult(intent, TAKE_PHOTO_REQUEST)
            }
            return true
        }
        */
    }

}
