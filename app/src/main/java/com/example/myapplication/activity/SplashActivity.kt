package com.example.myapplication.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.activity.SplashActivity
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.LoginDao
import com.example.myapplication.db.table.Login
import com.example.myapplication.helper.CommonMethods
import java.util.*

class SplashActivity : AppCompatActivity() {

    internal var listlogin=ArrayList<Login>()
    lateinit var appDatabase: AppDatabase
    lateinit var empLoginDao: LoginDao
    private var dialog: AlertDialog? = null
    var result = false
    var companyLogo: ImageView? = null
    var commonMethods: CommonMethods? = null
    var TAG = SplashActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        appDatabase = AppDatabase.getDatabase(this)
        empLoginDao=appDatabase.loginDao()
        commonMethods = CommonMethods(this@SplashActivity)
        checkAppPermissions()
    }

    fun checkAppPermissions() {
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            )
                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NETWORK_STATE
            )
                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            )
                    != PackageManager.PERMISSION_GRANTED)
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.INTERNET
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_NETWORK_STATE
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                )
            ) {
                goNext()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.CAMERA
                        , Manifest.permission.ACCESS_NETWORK_STATE
                        , Manifest.permission.SEND_SMS
                    ),
                    MY_PERMISSIONS_REQUEST_WRITE_FILES
                )
            }
        } else {
            goNext()
        }
    }

    private fun bindLogo() { // Start animating the image
        val splash =
            findViewById<View>(R.id.companyLogo) as ImageView
        val animation1 = AlphaAnimation(0.2f, 1.0f)
        animation1.duration = 700
        val animation2 = AlphaAnimation(1.0f, 0.2f)
        animation2.duration = 700
        //animation1 AnimationListener
        animation1.setAnimationListener(object : AnimationListener {
            override fun onAnimationEnd(arg0: Animation) { // start animation2 when animation1 ends (continue)
                splash.startAnimation(animation2)
            }

            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationStart(arg0: Animation) {}
        })
        //animation2 AnimationListener
        animation2.setAnimationListener(object : AnimationListener {
            override fun onAnimationEnd(arg0: Animation) { // start animation1 when animation2 ends (repeat)
                splash.startAnimation(animation1)
            }

            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationStart(arg0: Animation) {}
        })
        splash.startAnimation(animation1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_FILES) {
            Log.e(TAG, "onRequestPermissionsResult: " + grantResults.size)
            val count = 0
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goNext()
            }
        } else {
            val builder =
                AlertDialog.Builder(this@SplashActivity)
            builder.setMessage("App required some permission please enable it")
                .setPositiveButton("Yes") { dialog, id ->
                    // FIRE ZE MISSILES!
                    openPermissionScreen()
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    // User cancelled the dialog
                    dialog.dismiss()
                    finish()
                }
            dialog = builder.show()
        }
    }

    fun goNext() {
        if (commonMethods!!.isNetworkAvailable(this)) {
        }
        bindLogo()

        val task: TimerTask = object : TimerTask() {
            override fun run() {

                Log.e("TAG", " splash  " + empLoginDao.getAll().size)
                var list=empLoginDao.getAll()
                if (list.size==1){
                  var type=list[0].type
                    Log.e("TAG", " splashtype  " + type)
                    if (type=="Admin"){
                        intent = Intent(this@SplashActivity, AdminMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        intent = Intent(this@SplashActivity, EmployeeMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }else{
                    commonMethods!!.intentFinish(this@SplashActivity, LoginActivity::class.java)
                }
//                if (!result) {
//
//                }
            }
        }
        // Show splash screen for 3 seconds
        Timer().schedule(task, 3000)
    }

    fun openPermissionScreen() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", this@SplashActivity.packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } //            public Map<String, String> getHeaders() {

    //                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
////                params.put("Authorization", auth);
//                return params;
//            }
    companion object {
        const val MY_PERMISSIONS_REQUEST_WRITE_FILES = 102
    }
}