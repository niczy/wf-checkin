package com.smenu.android.checkin

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import java.nio.file.Files.size
import com.google.android.gms.vision.barcode.Barcode
import android.util.SparseArray
import com.google.android.gms.vision.Detector
import android.view.SurfaceHolder
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.barcode.BarcodeDetector
import android.widget.TextView
import android.view.SurfaceView
import android.view.View
import java.io.IOException


class BarcodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        var cameraView = findViewById<View>(R.id.camera_view) as SurfaceView
        var barcodeInfo = findViewById<View>(R.id.txtContent) as TextView


        val barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.CODE_128)//QR_CODE)
            .build()

        var cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(640, 480)
            .build()

        var t = this;
        cameraView.getHolder().addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {

                try {
                    if (ContextCompat.checkSelfPermission(t, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            cameraSource.start(cameraView.getHolder())
                        } else {
                        System.out.println("Failed to launch camera");
                    }
                } catch (ie: IOException) {
                    Log.e("CAMERA SOURCE", ie.message)
                }

            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })


        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {

                val barcodes = detections.detectedItems

                if (barcodes.size() != 0) {
                    barcodeInfo.post {
                        barcodeInfo.text = barcodes.valueAt(0).displayValue
                    }
                }
            }
        })
    }
}
