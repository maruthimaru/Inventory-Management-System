package com.example.myapplication.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.LoginDao
import com.example.myapplication.fragment.EmployeeHomeFragment
import com.example.myapplication.fragment.FragmentDrawer
import com.example.myapplication.helper.CommonMethods

class EmployeeMainActivity : AppCompatActivity(), FragmentDrawer.FragmentDrawerListener {

    override fun onDrawerItemSelected(view: View, position: Int) {
        displayPosition(position)
    }

    override fun onDrawerItemView(view: View) {
        fragmentDrawerView = view
    }
    private fun displayPosition(position: Int) {
        var title = getString(R.string.app_name)
        when (position) {
            0 -> {
                setfragment(EmployeeHomeFragment())

            }
            1 -> {
//                setfragment(EmployeeProfileFragment())
                commonClass.Logoutscreen(empLoginDao)

            }
            2 -> {
                logout()

            }
            3 -> {
//                setFragment(TimeTableFragment(), title)
            }
            4 -> {
//                setFragment(ViewAttendanceFragment(), title)
            }
            5 -> {
//                intent = Intent(this, LocateBusActivity::class.java)
//                startActivity(intent)
            }
//            6 -> {
//                setFragment(WalletFragment(), title)
//            }
//            7 -> {
//                setFragment(LocateStudentFragment(), title)
//            }
//            8 -> {
//                setFragment(FindIDCardFragment(), title)
//            }
//            9 -> {
//                setFragment(AssignmentFragment(), title)
//            }
//            10 -> {
//                setFragment(LibraryFragment(), title)
//            }
//            11 -> {
//                setFragment(ApplyLeaveListFragment(), title)
//            }
//            12 -> {
//                setFragment(PersonalMessageFragment(), title)
//            }
//            13 -> {
//
//            }
        }
    }

    private lateinit var commonClass: CommonMethods
    private var drawerFragment: FragmentDrawer? = null
    lateinit var mToolbar: Toolbar
    var customdialog: CommonMethods? = null
    private var drawerLayout: DrawerLayout? = null
    var fragmentDrawerView: View? = null
    lateinit var empLoginDao: LoginDao
    lateinit var appDatabase: AppDatabase
    internal var TAG = EmployeeMainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        customdialog = CommonMethods(this)
        commonClass = CommonMethods(this)
        appDatabase = AppDatabase.getDatabase(this)
        empLoginDao=appDatabase.loginDao()
        mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        supportActionBar!!.setHomeButtonEnabled(true)
        mToolbar.setNavigationIcon(R.drawable.ic_menunew)
        setfragment(EmployeeHomeFragment())

        drawerFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_navigation_drawer) as FragmentDrawer
        drawerFragment!!.setUp(
            R.id.fragment_navigation_drawer,
            findViewById<DrawerLayout>(R.id.drawer_layout),
            mToolbar
        )
        drawerFragment!!.setDrawerListener(this)
        drawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun setfragment(_fragment: Fragment) {
        val fm = supportFragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        val fragmentList = supportFragmentManager.fragments
        var result = removeAll(fragmentList)
        if (count == 0) {
            //additional code
            super.onBackPressed()
            customdialog!!.showConfirmation()
        } else if (count == 1) {
            customdialog!!.showConfirmation()

        } else {
            if (result.size > 0) {
                for (model in result) {
                    Log.e(TAG, " onBackPressed model " + model::class.java)
                    if (model::class.java.equals(EmployeeHomeFragment::class.java)) {
                        customdialog!!.showConfirmation()
                    }else if (model::class.java.equals(EmployeeHomeFragment::class.java)) {
                        setfragment(EmployeeHomeFragment())
                    }else{
                        supportFragmentManager.popBackStackImmediate()
                    }
                }
            }
        }

    }

    internal fun removeAll(list: MutableList<Fragment>): MutableList<Fragment> {
        val mutableList: MutableList<Fragment> = ArrayList()
        for (number in list) {
            if (!number.toString().startsWith("SupportRequestManagerFragment") &&
                !number.toString().startsWith("FragmentDrawer")
            ) {
                mutableList.add(number)
            }
        }
        return mutableList
    }

    fun logout() {
        AlertDialog.Builder(CommonMethods.context).setTitle(CommonMethods.context.getString(R.string.confirmation))
            .setMessage(CommonMethods.context.getString(R.string.log_out))
            .setPositiveButton(CommonMethods.context.getString(R.string.yes)) { _, _ ->
                intent = Intent(this@EmployeeMainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setNegativeButton(CommonMethods.context.getString(R.string.no)) { dialogInterface, i ->
                dialogInterface.dismiss()
            }.show()
    }

}
