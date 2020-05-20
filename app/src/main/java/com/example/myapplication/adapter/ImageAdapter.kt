package com.example.myapplication.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64.URL_SAFE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.helper.BitmapUtility
import com.example.myapplication.helper.CommonMethods
import java.util.*


class ImageAdapter(private val context: Context, private val list: List<String>) : RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {
    private val tempItems: List<String>

    private var commonMethods: CommonMethods? = null
    internal lateinit var bitmapUtility: BitmapUtility

    private val TAG = "suplierview"
//    interface ListAdapterListener { // create an interface
//        fun onClickButton(position: Int, repairProduct: RepairProduct)  // create callback function
//
//    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView



        init {
           this.img = view.findViewById(R.id.imageViewDocument)
            bitmapUtility= BitmapUtility(context)


        }
    }

    init {

        this.tempItems = ArrayList(list)

        this.commonMethods= CommonMethods(context)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.demo_image, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = this.list[position]
        Log.e(TAG,"model " + model)
        val bmp =bitmapUtility.base64toBitmap(model)
        Log.e(TAG,"bmp " + bmp)
        holder.img.setImageBitmap(bmp)








    }

    override fun getItemCount(): Int {
        return this.list.size
    }




}
