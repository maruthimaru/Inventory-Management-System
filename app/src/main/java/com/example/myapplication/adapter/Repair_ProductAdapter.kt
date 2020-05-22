package com.example.myapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.RepairProductImageDao
import com.example.myapplication.db.table.RepairProduct
import com.example.myapplication.helper.BitmapUtility
import com.example.myapplication.helper.CommonMethods
import java.util.*

class Repair_ProductAdapter(private val context: Context, private val list: MutableList<RepairProduct>, private val mListener: ListAdapterListener,isAdmin:Boolean) : RecyclerView.Adapter<Repair_ProductAdapter.MyViewHolder>() {
    private val tempItems: List<RepairProduct>

    private var commonMethods: CommonMethods? = null
    private var bitmapUtility: BitmapUtility?= null
    private var isAdmin=false
    private lateinit var repairProductImageDao: RepairProductImageDao;
    lateinit var appDatabase: AppDatabase
    private val TAG = "suplierview"
    interface ListAdapterListener { // create an interface
        fun onClickButtonDelete(position: Int, repairProduct: RepairProduct)  // create callback function
        fun onClickCheckOut(repairProduct: RepairProduct)
        fun onClickButtonInfo(position: Int, repairProduct: RepairProduct)
    }

    fun removeData(position: Int){
        list.removeAt(position)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal lateinit var pname: TextView
        internal lateinit var pcode: TextView
        internal lateinit var last_date: TextView
        internal lateinit var pro_date:TextView
        internal lateinit var pro_time:TextView
        internal lateinit var product_image: ImageView
        internal lateinit var cardview: CardView
        internal lateinit var message:ImageView
        internal lateinit var imagerecyclerView: RecyclerView
        internal lateinit var delete: TextView

        init {
            this.pname=view.findViewById(R.id.pname)
            this.pcode = view.findViewById(R.id.pcode)
            this.last_date = view.findViewById(R.id.last_date)
            this.pro_date=view.findViewById(R.id.pro_date)
            this.pro_time = view.findViewById(R.id.pro_time)
            this.product_image=view.findViewById(R.id.product_image)
            this.message=view.findViewById(R.id.message)
            this.cardview=view.findViewById(R.id.cardview)
            this.imagerecyclerView=view.findViewById(R.id.recycle_image)
            this.delete= view.findViewById(R.id.delete)
        }
    }

    init {

        this.tempItems = ArrayList(list)
    this.isAdmin=isAdmin
        this.commonMethods= CommonMethods(context)
        bitmapUtility = BitmapUtility(context)
        appDatabase= AppDatabase.getDatabase(context)
        repairProductImageDao=appDatabase.repairProductImage()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.repair_product_list, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = this.list[position]
//        holder.profpic.text=model.employeeID
        holder.pname.text = model.pName
        holder.pcode.text=model.pCode
        holder.pro_date.text=model.date
        holder.pro_time.text=model.time

        holder.product_image.setImageBitmap(bitmapUtility!!.base64toBitmap(model.image))


        holder.cardview.setOnClickListener { v ->
            if (position != -1 ) {
                Log.e(TAG, "POSITION: $position")
                // use callback function to Return the Position
                mListener.onClickButtonDelete(position,model)

            }
        }

        if(isAdmin){
            holder.message.visibility=View.VISIBLE
            holder.delete.visibility=View.INVISIBLE
        }else{
            holder.message.visibility=View.GONE
            holder.delete.visibility=View.VISIBLE
        }
        holder.message.setOnClickListener {
            mListener.onClickButtonInfo(position, model)
        }

        holder.delete.setOnClickListener {
            mListener.onClickButtonDelete(position,model)
        }

        var imageList=repairProductImageDao.getpId(model.imgId)
        model.imagelist
        Log.e(TAG,"imagesize " + model.imagelist!!.size)
        val imageAdapter:RepairImageAdapter
        imageAdapter= RepairImageAdapter(context,imageList)
        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        holder.imagerecyclerView.layoutManager = layoutManager
        holder.imagerecyclerView.setHasFixedSize(true)

       holder.imagerecyclerView .adapter = imageAdapter


    }

    override fun getItemCount(): Int {
        return this.list.size
    }


    fun remove(int: Int) {
        list.removeAt(int)
        notifyDataSetChanged()
    }


}
