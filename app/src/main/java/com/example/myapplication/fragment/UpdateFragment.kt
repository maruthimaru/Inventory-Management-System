package com.example.myapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.Update_ProductAdapter
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.LoginDao
import com.example.myapplication.db.dao.ProductDetailsDao
import com.example.myapplication.db.dao.RepairProductDao
import com.example.myapplication.db.table.Login
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.db.table.RepairProduct
import com.example.myapplication.helper.CommonMethods
import java.util.*

class UpdateFragment:Fragment(), Update_ProductAdapter.ListAdapterListener{
    private val TAG: String= UpdateFragment::class.java.simpleName
    internal var list= ArrayList<ProductDetails>()
    internal var repairlist= ArrayList<RepairProduct>()
    internal var listlogin= ArrayList<Login>()
    internal lateinit var view: View
    internal lateinit var recyclerView: RecyclerView
    lateinit var repairadapter: Update_ProductAdapter
    lateinit var appDatabase: AppDatabase
    lateinit var productDao: ProductDetailsDao
    lateinit var repairporductDao: RepairProductDao
    lateinit var empLoginDao: LoginDao
    internal lateinit var commonMethods: CommonMethods

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
//        repairlist= repairporductDao.getempid(empLoginDao.getemp_id()) as ArrayList<RepairProduct>
        repairlist=repairporductDao.updatelist(empLoginDao.getemp_id()) as ArrayList<RepairProduct>
        Log.e(TAG,"repairlistttt " + repairlist.size )
        repairadapter = Update_ProductAdapter( activity!!,repairlist,this@UpdateFragment )
        recyclerView.adapter = repairadapter


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