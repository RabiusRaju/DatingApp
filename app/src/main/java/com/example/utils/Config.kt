package com.example.utils

import android.app.AlertDialog
import android.content.Context
import com.example.datingapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


/**
 * Created by MD.Rabius sani raju on 10/11/22.
 */
object Config {
   private var dialog : AlertDialog? = null

    fun showDialog(context: Context){
        /*dialog = MaterialAlertDialogBuilder(context)
            .setView(R.layout.loading_layout)
            .setCancelable(false)
            .create()

        dialog!!.show()*/
    }

    fun hideDialog(){
        dialog!!.dismiss()
    }


}