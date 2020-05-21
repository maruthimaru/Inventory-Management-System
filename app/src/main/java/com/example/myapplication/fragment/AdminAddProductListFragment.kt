package com.example.myapplication.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.EmpRegDao
import com.example.myapplication.db.dao.ProductDetailsDao
import com.example.myapplication.db.table.EmpReg
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.helper.BitmapUtility
import com.example.myapplication.helper.CommonMethods
import com.example.myapplication.helper.Constants
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class AdminAddProductListFragment:Fragment(){
    private val TAG: String= AdminAddProductListFragment::class.java.simpleName
    internal var list=ArrayList<ProductDetails>()
    lateinit var companyImg:ImageView
     lateinit var imageButton2:ImageButton
     lateinit var product_name:EditText
     lateinit var product_code:EditText
    lateinit var dates: TextView
    lateinit var date_image:ImageView
    lateinit var time: TextView
    lateinit var time_image:ImageView
    lateinit var submit_btn:Button
    lateinit var appDatabase: AppDatabase
    lateinit var productDetailsDao: ProductDetailsDao
    internal lateinit var commonMethods: CommonMethods
    private var phtobitmap: Bitmap?=null
    lateinit var bitmapUtility: BitmapUtility

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_admin_add_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        companyImg=view.findViewById(R.id.companyImg)
        imageButton2=view.findViewById(R.id.imageButton2)
        product_name=view.findViewById(R.id.product_name)
        product_code=view.findViewById(R.id.product_code)
        dates=view.findViewById(R.id.dates)
        date_image=view.findViewById(R.id.date_image)
        time=view.findViewById(R.id.time)
        time_image=view.findViewById(R.id.time_image)
        submit_btn=view.findViewById(R.id.submit_btn)
        commonMethods= CommonMethods(activity!!)
        bitmapUtility= BitmapUtility(activity!!)
        appDatabase = AppDatabase.getDatabase(activity!!)
        productDetailsDao=appDatabase.productDetailDao()

        submit_btn.setOnClickListener{
                askAppointment()
        }

        dates.text = commonMethods.date(Constants.dateformat1)
        time.text = commonMethods.date(Constants.dateformat2)
        date_image.setOnClickListener { commonMethods.clickDate(dates) }
        time_image.setOnClickListener { commonMethods.clickTime(time) }

        imageButton2.setOnClickListener{

            // setup the alert builder
            val builder = android.app.AlertDialog.Builder(activity)
            builder.setTitle("Choose an photo")
            // add a list
            val photo = arrayOf("Take Photo", "Select Photo", "Cancel")
            builder.setItems(photo) { dialog, which ->
                when (which) {
                    0 // Take Photo
                    -> {
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(takePicture, 0)//zero can be replaced with any action code
                        Log.e("TAG", "Click event for photo=$which")
                    }
                    1 // Select Photo
                    -> {
                        val pickPhoto = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, 1)//one can be replaced with any action code
                        Log.e("TAG", "Click event for photo=$which")
                    }

                    2 // Cancel
                    -> builder.setCancelable(true)
                }
            }
            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()

        }

    }

    private fun askAppointment(){
        val pname=product_name.text.toString()
        val pcode=product_code.text.toString()
        val NextDate=dates.text.toString()
        val Date=commonMethods.date(Constants.dateformat1)
        val Time=time.text.toString()
        var logo = ""

        if (pname.isNullOrEmpty()){
            product_name.requestFocus()
            product_name.error = "Please enter the product name"
        }else if (pcode.isNullOrEmpty()){
            product_code.requestFocus()
            product_code.error="Please enter the product code"
        }else if (Date.isNullOrEmpty()){
            dates.requestFocus()
            dates.error="please enter date"
        }else if (Time.isNullOrEmpty()){
            time.requestFocus()
            time.error="Please enter time"
        }else{
            if (phtobitmap != null) {
                logo = bitmapUtility.getStringImage(phtobitmap!!)
            } else {
                logo = commonMethods.getBaseImage(commonMethods.getBytes((companyImg.drawable as BitmapDrawable).bitmap))
            }

            list.add(ProductDetails(pname,pcode,logo,Date,Time,NextDate,"Not yet"))
            Log.e("TAG", " productregister  " + list.size)
            Toast.makeText(activity,"Product register successfull",Toast.LENGTH_SHORT).show()
            productDetailsDao.insert(list)
            Log.e(TAG,"insertdata " + productDetailsDao.getAll().size)
            setfragment(AdminProductFragment())

        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == 0) {

                    val bp = data!!.extras!!.get("data") as Bitmap
                    val resized = Bitmap.createScaledBitmap(bp, 100, 100, true)
                    phtobitmap=resized
                    val conv_bm = getRoundedRectBitmap(resized, 100)
                    companyImg.setImageBitmap(null)
                    companyImg.setImageBitmap(conv_bm)
                    Log.e("TAG", "select event for photo=$resultCode")
                    //customerImg=getBytes(conv_bm);
                }
//                Log.e("TAG", "select event for photo=$resultCode")
            }
            1 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == 1) {


                    var bm: Bitmap? = null
                    if (data != null) {
                        try {
                            bm = MediaStore.Images.Media.getBitmap(context!!.contentResolver, data.data)
                            //                            int size=Math.min(bm.getWidth(),bm.getHeight());
                            //                            int x=(bm.getWidth()-size)/2;
                            //                            int y=(bm.getHeight()-size)/2;
                            //                            Bitmap bitmap=Bitmap.createBitmap(bm,x,y,size,size);
                            //                            Log.e("bitmap_Resul "," = "+ bitmap);

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }
                    val resized = Bitmap.createScaledBitmap(bm!!, 100, 100, true)
                    val conv_bm = getRoundedRectBitmap(resized, 100)
                    companyImg.setImageBitmap(conv_bm)
                    Log.e("TAG", "select event for photo=$resultCode")
                }
//                Log.e("TAG", "select event for photo=$resultCode")
            }
        }
    }

    private fun getRoundedRectBitmap(bp: Bitmap, i: Int): Bitmap? {

        var result: Bitmap? = null
        try {
            result = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(result!!)
            val color = -0xbdbdbe
            val paint = Paint()
            val rect = Rect(0, 0, 200, 200)
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawCircle(50f, 50f, 50f, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bp, rect, rect, paint)

        } catch (e: NullPointerException) {
        } catch (o: OutOfMemoryError) {
        }
        return result
    }
    private fun setfragment(_fragment: Fragment) {
        val fm = fragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}