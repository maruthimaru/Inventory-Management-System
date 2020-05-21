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
import com.example.myapplication.R
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.ProductDetailsDao
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.helper.CommonMethods
import com.example.myapplication.utils.QRCodeScannerPortait
import com.example.myapplication.utils.StringsValue
import java.lang.Exception
import java.util.ArrayList

class EntryFragment:Fragment(){
    private val TAG: String= EmployeeHomeFragment::class.java.simpleName
    lateinit var entry_btn:Button
    lateinit var update_btn:Button
    internal var list= ArrayList<ProductDetails>()
    lateinit var appDatabase: AppDatabase
    lateinit var productDao: ProductDetailsDao
    internal lateinit var commonMethods: CommonMethods

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_entry, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entry_btn=view.findViewById(R.id.entry_btn)
        update_btn=view.findViewById(R.id.update_btn)
        appDatabase = AppDatabase.getDatabase(activity!!)
        commonMethods= CommonMethods(activity!!)
        productDao=appDatabase.productDetailDao()

        entry_btn.setOnClickListener{
            val connectIntent = Intent(activity, QRCodeScannerPortait::class.java)
            startActivityForResult(connectIntent, 20)
        }

        update_btn.setOnClickListener{
            setfragment(UpdateFragment())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
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
                            confirmDialog.dismiss()


                        }
                        buttonCancel.setOnClickListener{
                            confirmDialog.dismiss()
                        }



                    }
                }
            }




        }
        catch (e: Exception){

            Log.e("TAG", " try_catch  " + e.localizedMessage)
        }

    }

    private fun setfragment(_fragment: Fragment) {
        val fm = fragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}