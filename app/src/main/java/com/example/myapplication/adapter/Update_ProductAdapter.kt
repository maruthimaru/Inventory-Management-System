package com.example.myapplication.adapter

import android.app.Dialog
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
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.db.table.RepairProduct
import com.example.myapplication.helper.BitmapUtility
import com.example.myapplication.helper.CommonMethods
import kotlinx.android.synthetic.main.activity_login.view.*
import java.util.*

class Update_ProductAdapter(private val context: Context, private val list: MutableList<RepairProduct>, private val mListener: ListAdapterListener) : RecyclerView.Adapter<Update_ProductAdapter.MyViewHolder>() {
    private val tempItems: List<RepairProduct>

    private var commonMethods: CommonMethods? = null
    private var bitmapUtility: BitmapUtility?= null

    private val TAG = "suplierview"
    interface ListAdapterListener { // create an interface
        fun onClickButton(position: Int, repairProduct: RepairProduct)  // create callback function
        fun onClickCheckOut(repairProduct: RepairProduct)
        fun onClickButtonInfo(position: Int, repairProduct: RepairProduct)
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

        this.commonMethods= CommonMethods(context)
        bitmapUtility = BitmapUtility(context)

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

        holder.delete.visibility=View.INVISIBLE


        holder.cardview.setOnClickListener { v ->
            if (position != -1 ) {
                Log.e(TAG, "POSITION: $position")
                // use callback function to Return the Position
                mListener.onClickButton(position,model)

            }
        }
        holder.message.setOnClickListener {
            mListener.onClickButtonInfo(position, model)


                val builder: Dialog = Dialog(context!!)
                val inflater = LayoutInflater.from(context)
                builder.setTitle("With RatingBar")
                val dialogLayout: View = inflater.inflate(R.layout.fragment_update_alert_dialog, null)
                builder.setContentView(dialogLayout)
                val message_edittxt = builder.findViewById<TextView>(R.id.message_edittxt)
                val text=message_edittxt.text.toString()
                message_edittxt.setText(model.reply_message)

//                update_txt.setOnClickListener {
//
//                    if (text.length>0){
//                        message_edittxt.setError(null)
//                        repairporductDao.update(text, repairProduct.id)
//                        Toast.makeText(activity,"Update Successfull", Toast.LENGTH_SHORT).show()
//
//                    }else{
//
//                        message_edittxt.requestFocus()
//                        message_edittxt.error= "Please enter the message"
//                    }
//
//                    Log.e(TAG,"repair " + repairProduct.id )
//                }
                builder.dismiss()

                builder.show()


        }

        model.imagelist
        Log.e(TAG,"imagesize " + model.imagelist!!.size)
        val imageAdapter:ImageAdapter
        imageAdapter= ImageAdapter(context,model.imagelist!!)
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
