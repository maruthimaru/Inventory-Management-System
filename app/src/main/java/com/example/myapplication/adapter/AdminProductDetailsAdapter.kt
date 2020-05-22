package com.example.myapplication.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.helper.BitmapUtility
import com.example.myapplication.helper.CommonMethods
import com.example.myapplication.helper.Zoomimage
import java.util.*

class AdminProductDetailsAdapter(private val context: Context, private val list: MutableList<ProductDetails>, private val mListener: ListAdapterListener) : RecyclerView.Adapter<AdminProductDetailsAdapter.MyViewHolder>() {
    private val tempItems: List<ProductDetails>

    private var commonMethods: CommonMethods? = null

    internal var bitmapUtility: BitmapUtility

    private val TAG = "suplierview"
    interface ListAdapterListener { // create an interface
        fun onClickButtonDelete(position: Int, productDetails: ProductDetails)  // create callback function

        fun onClickButtonInfo(position: Int, productDetails: ProductDetails)
    }

    fun removeData(position: Int){
        list.removeAt(position)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal lateinit var pname: TextView
        internal lateinit var pcode: TextView
        internal lateinit var pro_date:TextView
        internal lateinit var pro_time:TextView
        internal lateinit var product_image: ImageView
        internal lateinit var cardview: CardView
        internal lateinit var delete: TextView

        //internal lateinit var imagerecyclerView: RecyclerView

        init {
            this.pname=view.findViewById(R.id.pname)
            this.pcode = view.findViewById(R.id.pcode)
            this.pro_date=view.findViewById(R.id.date)
            this.pro_time = view.findViewById(R.id.time)
            this.product_image=view.findViewById(R.id.product_image)
            this.cardview=view.findViewById(R.id.product_card)
            this.delete= view.findViewById(R.id.delete)
        }
    }

    init {

        this.tempItems = ArrayList(list)

        bitmapUtility= BitmapUtility(context)
        commonMethods=CommonMethods(context)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_product_details_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = this.list[position]
//        holder.profpic.text=model.employeeID
        holder.pname.text = model.pName
        holder.pcode.text=model.pCode
        holder.pro_date.text=model.date
        holder.pro_time.text=model.time
        val bmp =bitmapUtility.base64toBitmap(model.image)
        Log.e(TAG,"bmp " + bmp)
        holder.product_image.setImageBitmap(bmp)


        holder.cardview.setOnClickListener { v ->
            if (position != -1 ) {
                Log.e(TAG, "POSITION: $position")
                // use callback function to Return the Position
//                mListener.onClickButton(position,model)

            }
        }

        holder.delete.setOnClickListener {
            mListener.onClickButtonDelete(position,model)
        }


//        model.imagelist
//        Log.e(TAG,"imagesize " + model.imagelist!!.size)
//        val imageAdapter:ImageAdapter
//        imageAdapter= ImageAdapter(context,model.imagelist!!)
//        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)
//        holder.imagerecyclerView.layoutManager = layoutManager
//        holder.imagerecyclerView.setHasFixedSize(true)
//
//       holder.imagerecyclerView .adapter = imageAdapter


    }

    override fun getItemCount(): Int {
        return this.list.size
    }


    fun remove(int: Int) {
        list.removeAt(int)
        notifyDataSetChanged()
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
