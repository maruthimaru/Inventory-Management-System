package com.example.myapplication.helper

import android.content.Context
import android.widget.Toast




class DialogToast {

    fun toastMethod(_contex: Context, toastMsg: String) {

        Toast.makeText(_contex, toastMsg, Toast.LENGTH_SHORT).show()

    }
}
