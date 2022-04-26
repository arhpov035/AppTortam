package com.arhi_app.tortam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.arhi_app.tortam.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var rootElement: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        init()
    }

    private fun init() {
        val toggle =
            ActionBarDrawerToggle(this, rootElement.drawerLayout, rootElement.mainContent.toolbar, R.string.open, R.string.close)
        rootElement.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        rootElement.navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_categories_cakes -> {
                Toast.makeText(this, "Preset id_categories_cakes", Toast.LENGTH_LONG).show()
            }
            R.id.id_categories_cupcakes -> {
                Toast.makeText(this, "Preset id_categories_cupcakes", Toast.LENGTH_LONG).show()
            }
            R.id.id_categories_bouquets -> {
                Toast.makeText(this, "Preset id_categories_bouquets", Toast.LENGTH_LONG).show()
            }
            R.id.id_categories_baskets -> {
                Toast.makeText(this, "Preset id_categories_baskets", Toast.LENGTH_LONG).show()
            }
            R.id.id_account_sign_up -> {
                Toast.makeText(this, "Preset id_account_sign_up", Toast.LENGTH_LONG).show()
            }
            R.id.id_account_sign_in -> {
                Toast.makeText(this, "Preset id_account_sign_in", Toast.LENGTH_LONG).show()
            }
            R.id.id_account_sign_out -> {
                Toast.makeText(this, "Preset id_account_sign_out", Toast.LENGTH_LONG).show()
            }
        }
        rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}