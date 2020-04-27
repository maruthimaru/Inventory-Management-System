package com.example.myapplication.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import me.sudar.zxingorient.Barcode
import me.sudar.zxingorient.ZxingOrient

class QRCodeScannerPortait : AppCompatActivity() {
    var TAG = QRCodeScannerPortait::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val integrator = ZxingOrient(this)
        integrator.setInfo("Scan a barcode or QRcode")
        integrator.initiateScan(Barcode.QR_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e(TAG, "intent: $data")
        val result = ZxingOrient.parseActivityResult(requestCode, resultCode, data)
        Log.e(TAG, "result " + result!!)
        if (result != null) {
            val mBundle = Bundle()
            mBundle.putString(StringsValue.param, result.contents)
            val mBackIntent = Intent()
            mBackIntent.putExtras(mBundle)
            setResult(Activity.RESULT_OK, mBackIntent)
            finish()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
