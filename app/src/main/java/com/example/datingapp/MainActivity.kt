package com.example.datingapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.datingapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding : ActivityMainBinding
    var actionBarDrawerToggle : ActionBarDrawerToggle?=null

    var PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    var PERMISSION_ALL_REQUEST_CODE = 1




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("MainActivity", "onCreate: "+hasPermissions(this))

        if (!hasPermissions(this, *PERMISSIONS)){
            ActivityCompat.requestPermissions(this,PERMISSIONS,PERMISSION_ALL_REQUEST_CODE)
        }
        actionBarDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.navigationView.setNavigationItemSelectedListener(this)
        val navController = findNavController(R.id.fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)



    }


    private fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.favorite->{
                Toast.makeText(this, "favorite", Toast.LENGTH_SHORT).show()
            }
            R.id.rateUs->{
                Toast.makeText(this, "rate Us", Toast.LENGTH_SHORT).show()
            }
            R.id.shareApp->{
                Toast.makeText(this, "share App", Toast.LENGTH_SHORT).show()
            }
            R.id.termsCondition->{
                Toast.makeText(this, "terms Condition", Toast.LENGTH_SHORT).show()
            }
            R.id.developer->{
                Toast.makeText(this, "developer", Toast.LENGTH_SHORT).show()
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(actionBarDrawerToggle!!.onOptionsItemSelected(item)){
            true
        }else
            super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.close()
        }
    }
}