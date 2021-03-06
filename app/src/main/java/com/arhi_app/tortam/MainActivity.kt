package com.arhi_app.tortam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.arhi_app.tortam.accounthelper.GoogleAccConst
import com.arhi_app.tortam.act.EditAdsAct
import com.arhi_app.tortam.databinding.ActivityMainBinding
import com.arhi_app.tortam.dialoghelper.DialogConst
import com.arhi_app.tortam.dialoghelper.DialogHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tvAccountEmail: TextView
    private lateinit var tvAccountLogin: TextView
    private lateinit var tvAccountAnd: TextView
    private lateinit var tvRegistr: TextView
    private lateinit var tvActExit: TextView

    private lateinit var rootElement: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        init()
    }

    // прослушивание меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_new_ads){
            // переход в активити
            val i = Intent(this, EditAdsAct::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == GoogleAccConst.GOOGLE_SIGN_IN_CODE){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null){
                    Log.d("MyLog", "Api 0")
                    dialogHelper.accHelper.signInFirebaseWithGoogle(account.idToken!!)
                }
            }catch (e:ApiException){
                Log.d("MyLog", "Api error : ${e.message}")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    private fun init() {
        //указываем какой toolbar использовать в actyvity вместо встроенного
        //и его нужно ставить выше остального кода, иначе работать не будет
        setSupportActionBar(rootElement.mainContent.toolbar)
        val toggle =
            ActionBarDrawerToggle(
                this,
                rootElement.drawerLayout,
                rootElement.mainContent.toolbar,
                R.string.open,
                R.string.close
            )
        rootElement.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        rootElement.navView.setNavigationItemSelectedListener(this)

        getTextFieldsHeader()
        setOnClickAuthentication()
    }

    private fun setOnClickAuthentication() {
        tvAccountLogin.setOnClickListener {
            dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)

            rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        }

        tvRegistr.setOnClickListener {
            dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
            rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        }

        tvActExit.setOnClickListener {
            uiUpdate(null)
            mAuth.signOut()
            dialogHelper.accHelper.signOutG()
            rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        }
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
                dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
            }
            R.id.id_account_sign_in -> {
                dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)
            }
            R.id.id_account_sign_out -> {
                uiUpdate(null)
                mAuth.signOut()
                dialogHelper.accHelper.signOutG()
            }
        }
        rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getTextFieldsHeader() {
        tvAccountEmail = rootElement.navView.getHeaderView(0).findViewById(R.id.tvAccountEmail)
        tvAccountLogin = rootElement.navView.getHeaderView(0).findViewById(R.id.tvLogIn)
        tvAccountAnd = rootElement.navView.getHeaderView(0).findViewById(R.id.tvAnd)
        tvRegistr = rootElement.navView.getHeaderView(0).findViewById(R.id.tvRegistr)
        tvActExit = rootElement.navView.getHeaderView(0).findViewById(R.id.tvActExit)
    }

    fun hideTextFieldsHeader() {
        tvAccountLogin.visibility = View.GONE
        tvAccountAnd.visibility = View.GONE
        tvRegistr.visibility = View.GONE

        tvAccountEmail.visibility = View.VISIBLE
        tvActExit.visibility = View.VISIBLE
    }

    private fun showTextFieldsHeader() {
        tvAccountLogin.visibility = View.VISIBLE
        tvAccountAnd.visibility = View.VISIBLE
        tvRegistr.visibility = View.VISIBLE

        tvAccountEmail.visibility = View.GONE
        tvActExit.visibility = View.GONE
    }

    fun uiUpdate(user: FirebaseUser?) {

        if (user == null) {
            tvAccountEmail.text = resources.getString(R.string.not_reg)
            showTextFieldsHeader()
        } else {
            tvAccountEmail.text = user.email
            hideTextFieldsHeader()
        }
    }
}