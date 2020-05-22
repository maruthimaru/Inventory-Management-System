package com.example.myapplication.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.UploadimageAdapter
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.*
import com.example.myapplication.db.table.Notification
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.db.table.RepairProduct
import com.example.myapplication.db.table.RepairProductImage
import com.example.myapplication.helper.BitmapUtility
import com.example.myapplication.helper.CommonMethods
import com.example.myapplication.helper.Constants
import com.example.myapplication.helper.RecyclerTouchListener
import com.example.myapplication.helper.pojo.ImagesModel
import id.zelory.compressor.Compressor
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class AlertdialogFragment:Fragment(){
    private var nextId: Int = 0
    private lateinit var productdetails: List<ProductDetails>
    private val TAG: String= AlertdialogFragment::class.java.simpleName
    lateinit var appDatabase: AppDatabase
    lateinit var productDao: ProductDetailsDao
    lateinit var notificationDao: NotificationDao
    lateinit var adminContactDao: AdminContactDao
    lateinit var empLoginDao: LoginDao
    lateinit var repairporductDao: RepairProductDao
    lateinit var repairporductImageDao: RepairProductImageDao
    internal lateinit var commonMethods: CommonMethods
    lateinit var recyclerView_doc_img: RecyclerView
    private lateinit var uploadimageAdapter: UploadimageAdapter
    lateinit var docpic: TextView
    internal var repairlist= ArrayList<RepairProduct>()
    private val imgList = ArrayList<ImagesModel>()
    private val repairProductImage = ArrayList<RepairProductImage>()
    private val imagelist = ArrayList<String>()
    private val imagelistByteArray = ArrayList<ByteArray>()
    private val imagepath = ArrayList<String>()
    private val Document = 18
    private var actualImage: File? = null
    private var compressedImage: File? = null
    private var mCurrentPhotoPath: String? = null
    lateinit var bitmapUtility: BitmapUtility
    private var phtobitmap: Bitmap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.alert_dialog,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabase = AppDatabase.getDatabase(activity!!)
        commonMethods= CommonMethods(activity!!)
        bitmapUtility = BitmapUtility(activity!!)
        productDao=appDatabase.productDetailDao()
        repairporductDao=appDatabase.repairProductDao()
        empLoginDao=appDatabase.loginDao()
        adminContactDao=appDatabase.adminContactDao()
        notificationDao= appDatabase.notificationDao()
        repairporductImageDao=appDatabase.repairProductImage()
        recyclerView_doc_img = view.findViewById(R.id.recyclerView_doc_img)
        docpic = view.findViewById(R.id.docpic)

        var lastId=repairporductDao.getLastId()
        Log.e(TAG,"last id: "+lastId )
         nextId = 0
//        if (lastId==0){
//            nextId = lastId
//        }else{
        nextId = lastId+1

        recyclerView_doc_img.setHasFixedSize(true)
        recyclerView_doc_img.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_doc_img.itemAnimator = DefaultItemAnimator()
        recyclerView_doc_img.addOnItemTouchListener(RecyclerTouchListener(activity!!, recyclerView_doc_img, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {}

            override fun onLongClick(view: View?, position: Int) {
                if (imagelist.isEmpty() || imagepath.isEmpty()) {
                    val t = Toast.makeText(activity, "You Can't delete this Photo!!!", Toast.LENGTH_SHORT)
                    t.setGravity(17, 0, 0)
                    t.show()
                    return
                }
                val builder = AlertDialog.Builder(activity)
                builder.setMessage(resources.getString(R.string.sureDelete))
                builder.setPositiveButton("OK") { dialog, id ->
                    imgList.removeAt(position)
                    imagelist.removeAt(position)
                    imagelistByteArray.removeAt(position)
                    val path = imagepath.get(position)
                    val fdelete = File(path)
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            imagepath.removeAt(position)
                            uploadimageAdapter.notifyDataSetChanged()
                        } else {
                        }
                    }
                }
                builder.setNegativeButton("Cancel") { dialog, id -> dialog.dismiss() }
                builder.create().show()
            }
        }))

        docpic.setOnClickListener {
            //                imgmsg.setVisibility( View.VISIBLE );
            recyclerView_doc_img.visibility = View.VISIBLE
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            actualImage = createImageFile()
            if (actualImage != null) {
                val photoURI: Uri
                if (Build.VERSION.SDK_INT > 19) {
                    photoURI = FileProvider.getUriForFile(activity!!, "com.example.myapplication.fileprovider", actualImage!!)
                } else {
                    photoURI = Uri.fromFile(actualImage)
                }
                intent.putExtra("output", photoURI)
                startActivityForResult(intent, Document)
            }
        }

        val producttime = view.findViewById<TextView>(R.id.product_time)
        val productname = view.findViewById<TextView>(R.id.product_name)
        val productcode = view.findViewById<TextView>(R.id.product_code)
        val productimage = view.findViewById<ImageView>(R.id.product_image)
        val prod_date = view.findViewById<TextView>(R.id.product_date)
        val buttonCancel = view.findViewById<Button>(R.id.buttonCancel)
        val buttonOk = view.findViewById<Button>(R.id.buttonOk)
        val next_s_date = view.findViewById<TextView>(R.id.next_s_date)
        val last_s_date = view.findViewById<TextView>(R.id.last_s_date)

        try {
            productdetails = productDao.getid(Constants.pcode)
            Log.e("TAG", " alertproduct_code  " + productdetails.size)

            producttime.setText( productdetails[0].time)
            productname.setText( productdetails[0].pName)
            productcode.setText( productdetails[0].pCode)
            productimage.setImageBitmap( bitmapUtility.base64toBitmap(productdetails[0].image))
            prod_date.setText( productdetails[0].date)
            next_s_date.setText( "Next Service : "+productdetails[0].nextServiceDate)
            last_s_date.setText( "Last Service : "+productdetails[0].lastServiceDate)
            empLoginDao.getemp_id()
            Log.e(TAG,"getemp_id " + empLoginDao.getemp_id())



            buttonOk.setOnClickListener { v ->
                repairlist.add(
                    RepairProduct(productdetails[0].pName,productdetails[0].pCode,productdetails[0].image,
                        commonMethods.getdate(Constants.dateformat1),commonMethods.getdate(
                            Constants.timeformat12),"",empLoginDao.getemp_id(),
                        "",imagelist,nextId))
                Log.e("TAG", " doctorregister  " + repairlist.size)
                repairporductDao.insert(repairlist)
                repairporductImageDao.insert(repairProductImage)
                var notification=Notification(productdetails[0].pName+" is damaged product code is "+ productdetails[0].pCode)
                notificationDao.insert(notification)
                Log.e(TAG,"phone: "+adminContactDao.getPhone())
                if (adminContactDao.getPhone()==null|| adminContactDao.getPhone()==""){
                    Toast.makeText(activity!!,"Update the admin phone number to send sms notification",Toast.LENGTH_LONG).show()
                }else{
                    commonMethods.sendSMS(adminContactDao.getPhone(),productdetails[0].pName+" is damaged product code is "+ productdetails[0].pCode)
                }
                Constants.pcode=""
                setfragment(RepairListFragment())
                //        Log.e(TAG,"insertdata " + repairporductDao.getAll().size)
            }
            buttonCancel.setOnClickListener{

            }
        }catch (e:Exception){
            Log.e("TAG", " alerttry " + e.localizedMessage)
        }
    }

    private fun createImageFile(): File {
        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera")
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdir()
        }
        val image = File(mediaStorageDir, "IMG_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + ".jpg")
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == 0) {

                    val bp = data!!.extras!!.get("data") as Bitmap
                    val resized = Bitmap.createScaledBitmap(bp, 100, 100, true)
                    phtobitmap = resized
                    val conv_bm = getRoundedRectBitmap(resized, 100)

                    Log.e("TAG", "select event for photo=$resultCode")
                    //customerImg=getBytes(conv_bm);
                }
//                Log.e("TAG", "select event for photo=$resultCode")
            }
            18 -> {
                handleBigCameraPhoto()
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
                    //companyimage.setImageBitmap(conv_bm)
                    Log.e("TAG", "select event for photo=$resultCode")
                }
//                Log.e("TAG", "select event for photo=$resultCode")
            }
//            FilePickerConst.REQUEST_CODE_DOC -> {
//
//
//                if (resultCode === Activity.RESULT_OK && android.R.attr.data != null) {
//                    docPaths = ArrayList()
//                    docPaths.addAll(data!!.getStringArrayListExtra(KEY_SELECTED_DOCS))
//                }
//
////
////                val selectedFileUri: Uri = data!!.getData()!!
//                Log.i(TAG, "selectedFileUri : ${docPaths[0]}")
//
//                val filepath: String = docPaths[0] //assign it to a string(your choice).
//                Log.e(TAG, "filepath : " + filepath)
//                try {
//                    if (resultCode == Activity.RESULT_OK) {
//                        try {
//
//                            val file = FileReader(filepath)
//                            Log.e(TAG, "file : " + file)
//                            val buffer = BufferedReader(file)
//                            var line = ""
//                            var iteration = 0
//                            while (buffer.readLine().also({ line = it }) != null) {
//                                if (iteration == 0) {
//                                    iteration++;
//                                    continue;
//                                }
//                                val str: ArrayList<String> = line.split(",") as ArrayList<String> // defining
//                                Log.e(TAG, "str : " + str)
//                                if (str.size > 0) {
////                                    Log.e(TAG,"list : " +str)
////                                    list.add( PatientAppointmentTable(str[1],str[2],str[3],str[4],str[5],str[6],str[7]))
//                                }
//                            }
//                            Log.e(TAG, "List size demo")
//                            Log.e(TAG, "List size ${list.size}")
////                            patientRegisterDao.insert(list)
//
//                        } catch (e: Exception) {
//                            Log.e(TAG, e.localizedMessage)
//                            Log.e(TAG, "List size ${list.size}")
//                            patientRegisterDao.insert(list)
//                        }
//                    } else {
//                        Toast.makeText(activity!!, "Only csv file allowed", Toast.LENGTH_LONG).show()
//                    }
//                } catch (e: Exception) {
//                    Log.e(TAG, e.localizedMessage)
//                }
//
//
//            }


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

    private fun handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            customCompressImage()
            imagepath.add(mCurrentPhotoPath.toString())
            Log.e(TAG, "handleBigCameraPhoto: path : " + mCurrentPhotoPath!!)
            mCurrentPhotoPath = null
        }
    }

    fun customCompressImage() {
        if (actualImage != null) {
            try {
                compressedImage = Compressor(activity).setMaxWidth(540).setMaxHeight(500).setQuality(50).setCompressFormat(Bitmap.CompressFormat.JPEG).compressToFile(actualImage!!)
                setCompressedImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun setCompressedImage() {
        val e: FileNotFoundException
        var imagesModel: ImagesModel

//        }
        val bitmap = BitmapFactory.decodeFile(compressedImage!!.absolutePath)
        try {
            val outputStream = FileOutputStream(actualImage!!)
            val fileOutputStream: FileOutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            fileOutputStream = outputStream
        } catch (e3: FileNotFoundException) {
            e = e3
            e.printStackTrace()
//            imagelist.add(bitmapUtility.getStringImage(bitmap))
            imagelist.add(bitmapUtility.getBytes(bitmap).toString())
            imagelistByteArray.add(bitmapUtility.getBytes(bitmap))
            imagesModel = ImagesModel()
            imagesModel.bitmap = bitmap
//            imagesModel.byteArray=bitmapUtility.getBytes(bitmap)
//            imagesModel.imageList=String(bitmapUtility.getBytes(bitmap))
            imgList.add(imagesModel)

            repairProductImage.add(RepairProductImage(nextId,productdetails[0].pCode,bitmapUtility.getStringImage(bitmap)))
            uploadimageAdapter = UploadimageAdapter(imgList, activity!!)
            recyclerView_doc_img.adapter = uploadimageAdapter
        }

        Log.e(TAG,"image byte array")
        System.out.println(bitmapUtility.getBytes(bitmap))
        Log.e(TAG,"image byte array")
//        imagelist.add(bitmapUtility.getStringImage(bitmap))
        imagelist.add(bitmapUtility.getBytes(bitmap).toString())
        imagelistByteArray.add(bitmapUtility.getBytes(bitmap))
        imagesModel = ImagesModel()
        imagesModel.bitmap = bitmap
//        imagesModel.byteArray=bitmapUtility.getBytes(bitmap)
//        imagesModel.imageList=String(bitmapUtility.getBytes(bitmap))
        imgList.add(imagesModel)
        repairProductImage.add(RepairProductImage(nextId,productdetails[0].pCode,bitmapUtility.getStringImage(bitmap)))
        //        Log.e(TAG, "setCompressedImage: Image List Size : "+list.size() );
        if (imgList.size > 0) {
            //            imgmsg.setVisibility( View.VISIBLE );
            //            dottedview.setVisibility( View.GONE );
            recyclerView_doc_img.visibility = View.VISIBLE
        }
        uploadimageAdapter = UploadimageAdapter(imgList, activity!!)
        recyclerView_doc_img.adapter = uploadimageAdapter
    }


    private fun setfragment(_fragment: Fragment) {
        val fm = fragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}