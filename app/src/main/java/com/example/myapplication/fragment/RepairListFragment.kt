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
import com.example.myapplication.db.dao.LoginDao
import com.example.myapplication.db.dao.ProductDetailsDao
import com.example.myapplication.db.dao.RepairProductDao
import com.example.myapplication.db.table.Login
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
    internal var listlogin=ArrayList<Login>()
    internal lateinit var view: View
    internal lateinit var recyclerView: RecyclerView
    lateinit var repairadapter: Repair_ProductAdapter

    lateinit var fab: FloatingActionButton
//    private var lLayout: GridLayoutManager? = null
    //internal lateinit var dialogToast: DialogToast
    lateinit var appDatabase: AppDatabase
    lateinit var productDao: ProductDetailsDao
    lateinit var repairporductDao:RepairProductDao
    lateinit var empLoginDao: LoginDao
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
        empLoginDao=appDatabase.loginDao()
        empLoginDao.getemp_id()

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = lLayout
        repairlist= repairporductDao.getempid(empLoginDao.getemp_id()) as ArrayList<RepairProduct>
        Log.e(TAG,"repairlistttt " + repairlist.size )
        repairadapter = Repair_ProductAdapter( activity!!,repairlist,this@RepairListFragment )
        recyclerView.adapter = repairadapter

        //Log.e(TAG,"getemp_id " + empLoginDao.getemp_id())

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
                    Constants.pcode=pcode
                    setfragment(AlertdialogFragment())
                    Log.e("TAG", " product_code  " + productdetails.size)


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

    override fun onClickButton(position: Int, repairProduct: RepairProduct) {

    }

    override fun onClickCheckOut(repairProduct: RepairProduct) {
    }

    override fun onClickButtonInfo(position: Int, repairProduct: RepairProduct) {

    }


}