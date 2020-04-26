package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RepairListFragment : Fragment() {
    internal lateinit var view: View
    internal lateinit var recyclerView: RecyclerView
    lateinit var fab: FloatingActionButton

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

        fab.setOnClickListener{

//            val connectIntent = Intent(activity, QRCodeScannerPortait::class.java)
//            startActivityForResult(connectIntent, 20)
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