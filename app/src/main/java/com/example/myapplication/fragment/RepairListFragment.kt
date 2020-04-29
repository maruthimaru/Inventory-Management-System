package com.example.myapplication.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.Repair_ProductAdapter
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.ProductDetailsDao
import com.example.myapplication.db.dao.RepairProductDao
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.db.table.RepairProduct
import com.example.myapplication.helper.CommonMethods
import com.example.myapplication.helper.Constants
import com.example.myapplication.utils.QRCodeScannerPortait
import com.example.myapplication.utils.StringsValue
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception
import java.util.ArrayList

class RepairListFragment : Fragment(),Repair_ProductAdapter.ListAdapterListener {
    private val TAG: String= RepairListFragment::class.java.simpleName
    internal var list=ArrayList<ProductDetails>()
    internal var repairlist=ArrayList<RepairProduct>()
    internal lateinit var view: View
    internal lateinit var recyclerView: RecyclerView
    lateinit var repairadapter: Repair_ProductAdapter

    lateinit var fab: FloatingActionButton
//    private var lLayout: GridLayoutManager? = null
    //internal lateinit var dialogToast: DialogToast
    lateinit var appDatabase: AppDatabase
    lateinit var productDao: ProductDetailsDao
    lateinit var repairporductDao:RepairProductDao
    internal lateinit var commonMethods: CommonMethods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view=inflater.inflate(R.layout.fragment_repair_list,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.fab)
        recyclerView=view.findViewById(R.id.recyclerView)
        appDatabase = AppDatabase.getDatabase(activity!!)
        commonMethods= CommonMethods(activity!!)
        productDao=appDatabase.productDetailDao()
        repairporductDao=appDatabase.repairProductDao()

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = lLayout
        repairlist= repairporductDao.getAll() as ArrayList<RepairProduct>
        Log.e(TAG,"repairlistttt " + repairlist.size )
        repairadapter = Repair_ProductAdapter( activity!!,repairlist,this@RepairListFragment )
        recyclerView.adapter = repairadapter


        fab.setOnClickListener{

         val connectIntent = Intent(activity, QRCodeScannerPortait::class.java)
            startActivityForResult(connectIntent, 20)
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try{
            if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
                val bundle = data!!.extras
                val pcode = bundle!!.getString(StringsValue.param)
                Log.e("TAG", "onActivityResult: " + pcode!!)
                if (pcode == null) {
                    Toast.makeText(activity,"Cancel", Toast.LENGTH_SHORT).show()

                } else {
                    var productdetails = productDao.getid(pcode)
                    Log.e("TAG", " product_code  " + productdetails.size)
                    if (productdetails.size == 0) {
                        Toast.makeText(activity,"Invalid QRcode", Toast.LENGTH_SHORT).show()
                    } else {
                        productdetails[0].pCode
                        Log.e("TAG", " product_codeeqee  " + productdetails[0].pCode)

                        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(activity!!)
                        val inflater = activity!!.layoutInflater
                        val view = inflater.inflate(R.layout.alert_dialog, null)
                        alertDialog.setView(view)
                        val confirmDialog = alertDialog.create()
                        confirmDialog.show()
                        confirmDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        val producttime = view.findViewById<TextView>(R.id.product_time)
                        val productname = view.findViewById<TextView>(R.id.product_name)
                        val productcode = view.findViewById<TextView>(R.id.product_code)
                        val productimage = view.findViewById<TextView>(R.id.product_image)
                        val prod_date = view.findViewById<TextView>(R.id.product_date)
                        val buttonCancel = view.findViewById<Button>(R.id.buttonCancel)
                        val buttonOk = view.findViewById<Button>(R.id.buttonOk)

                        producttime.setText( productdetails[0].time)
                        productname.setText( productdetails[0].pName)
                        productcode.setText( productdetails[0].pCode)
                        productimage.setText( productdetails[0].image)
                        prod_date.setText( productdetails[0].date)


                        buttonOk.setOnClickListener { v ->
                            repairlist.add(RepairProduct(productdetails[0].pName,productdetails[0].pCode,productdetails[0].image,
                                commonMethods.getdate(Constants.dateformat1),commonMethods.getdate(Constants.timeformat12),""))
                            Log.e("TAG", " doctorregister  " + repairlist.size)
                            repairporductDao.insert(repairlist)
//                        Log.e(TAG,"insertdata " + repairporductDao.getAll().size)
                            confirmDialog.dismiss()


                        }
                        buttonCancel.setOnClickListener{
                            confirmDialog.dismiss()
                        }



                    }
                }
            }
        }catch (e:Exception){
            Log.e("TAG", " try " + e.localizedMessage)
        }

    }




    private fun setfragment(_fragment: Fragment) {
        val fm = fragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClickButton(position: Int, list: RepairProduct, type: String) {

    }

    override fun onClickCheckOut(list: RepairProduct) {
    }
}