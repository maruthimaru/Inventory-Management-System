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
import com.example.myapplication.adapter.AdminProductDetailsAdapter
import com.example.myapplication.adapter.Repair_ProductAdapter
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.LoginDao
import com.example.myapplication.db.dao.ProductDetailsDao
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.db.table.RepairProduct
import com.example.myapplication.helper.BitmapUtility
import com.example.myapplication.helper.CommonMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class AdminProductFragment:Fragment(),AdminProductDetailsAdapter.ListAdapterListener{
    private val TAG: String= AdminProductFragment::class.java.simpleName

    internal lateinit var recyclerView: RecyclerView
    lateinit var fab: FloatingActionButton
    internal var productdetaillist= ArrayList<ProductDetails>()
    lateinit var appDatabase: AppDatabase
    lateinit var productDetailsDao: ProductDetailsDao
    internal lateinit var commonMethods: CommonMethods
    lateinit var adminProductDetailsAdapter: AdminProductDetailsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_admin_product, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.fab)
        recyclerView=view.findViewById(R.id.recyclerView)
        commonMethods= CommonMethods(activity!!)
        appDatabase = AppDatabase.getDatabase(activity!!)
        productDetailsDao=appDatabase.productDetailDao()


        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = lLayout
        productdetaillist= productDetailsDao.getAll() as ArrayList<ProductDetails>
        Log.e(TAG,"repairlistttt " + productdetaillist.size )
        adminProductDetailsAdapter = AdminProductDetailsAdapter( activity!!,productdetaillist,this@AdminProductFragment )
        recyclerView.adapter = adminProductDetailsAdapter

        fab.setOnClickListener{
            setfragment(AdminAddProductListFragment())
        }
    }

    private fun setfragment(_fragment: Fragment) {
        val fm = fragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClickButton(position: Int, productDetails: ProductDetails) {

    }

    override fun onClickButtonInfo(position: Int, productDetails: ProductDetails) {
    }
}