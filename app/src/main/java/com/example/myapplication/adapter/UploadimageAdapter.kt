package com.example.myapplication.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

import com.example.myapplication.R
import com.example.myapplication.helper.BitmapUtility
import com.example.myapplication.helper.CommonMethods
import com.example.myapplication.helper.Zoomimage
import com.example.myapplication.helper.pojo.ImagesModel

class UploadimageAdapter(private val list: List<ImagesModel>, private val context: Context) : RecyclerView.Adapter<UploadimageAdapter.MyViewHolder>() {
    private val dialog: Dialog? = null
    internal var TAG = UploadimageAdapter::class.java.simpleName
    internal var commonMethods: CommonMethods
    internal var bitmapUtility: BitmapUtility

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.demo_image, null)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val images = list[position]
        commonMethods.loadImage( bitmapUtility.getBytes(images.bitmap),holder.img)
        holder.img.setImageBitmap(images.bitmap)
//        holder.img.setImageBitmap(bitmapUtility.getImage(images.byteArray))
//        holder.img.setImageBitmap(bitmapUtility.getImage(images.imageList!!.toByteArray()))
        holder.img.setOnClickListener { this@UploadimageAdapter.fullscrndialog(images.bitmap) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView

        init {
            this.img = view.findViewById(R.id.imageViewDocument)
        }
    }

    init {
        bitmapUtility= BitmapUtility(context)
        commonMethods=CommonMethods(context)
    }


    private fun fullscrndialog(bitmap: Bitmap) {
        val dialog = Dialog(this.context)
        dialog.requestWindowFeature(1)
        dialog.setContentView(R.layout.fullprofimage_demo)
        dialog.window!!.setLayout(-1, -1)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val cancel = dialog.findViewById<ImageView>(R.id.cancel_button)
        (dialog.findViewById<View>(R.id.fullimageView) as Zoomimage).setImageBitmap(bitmap)
        cancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}
