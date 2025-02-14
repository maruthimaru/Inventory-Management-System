package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.activity.EmployeeRegsiterActivity

class EmployeeHomeFragment: Fragment(){
    private val TAG: String= EmployeeHomeFragment::class.java.simpleName
    lateinit var entry_update_btn:Button
    lateinit var repair_btn:Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_employee_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entry_update_btn =view.findViewById(R.id.entry_update_btn)
        repair_btn=view.findViewById(R.id.repair_btn)

        entry_update_btn.setOnClickListener{
            setfragment(EntryFragment())
        }
        repair_btn.setOnClickListener{
            setfragment(RepairListFragment())
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
