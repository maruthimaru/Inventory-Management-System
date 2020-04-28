package com.example.myapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.db.table.RepairProduct
import com.example.myapplication.helper.CommonMethods
import java.util.*

class Repair_ProductAdapter(private val context: Context, private val list: MutableList<RepairProduct>, private val mListener: ListAdapterListener) : RecyclerView.Adapter<Repair_ProductAdapter.MyViewHolder>() {
    private val tempItems: List<RepairProduct>

    private var commonMethods: CommonMethods? = null

    private val TAG = "suplierview"
    interface ListAdapterListener { // create an interface
        fun onClickButton(position: Int, list: RepairProduct,type:String)  // create callback function
        fun onClickCheckOut(list: RepairProduct)
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal lateinit var pname: TextView
        internal lateinit var pcode: TextView
        internal lateinit var last_date: TextView
        internal lateinit var pro_date:TextView
        internal lateinit var pro_time:TextView
        internal lateinit var product_image: ImageView
        internal lateinit var layout: CardView

        init {
            this.pname=view.findViewById(R.id.pname)
            this.pcode = view.findViewById(R.id.pcode)
            this.last_date = view.findViewById(R.id.last_date)
            this.pro_date=view.findViewById(R.id.pro_date)
            this.pro_time = view.findViewById(R.id.pro_time)
            this.product_image=view.findViewById(R.id.product_image)
            this.layout=view.findViewById(R.id.cardview)
        }
    }

    init {

        this.tempItems = ArrayList(list)

        this.commonMethods= CommonMethods(context)

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



        holder.layout.setOnClickListener { v ->
            if (position != -1 ) {
                Log.e(TAG, "POSITION: $position")
                // use callback function to Return the Position

            }
        }


    }

    override fun getItemCount(): Int {
        return this.list.size
    }


    fun remove(int: Int) {
        list.removeAt(int)
        notifyDataSetChanged()
    }


}
