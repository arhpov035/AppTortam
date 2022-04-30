package com.arhi_app.tortam.dialoghelper

import android.app.AlertDialog
import com.arhi_app.tortam.MainActivity
import com.arhi_app.tortam.R
import com.arhi_app.tortam.accounthelper.AccountHelper
import com.arhi_app.tortam.databinding.SignDialogBinding

class DialogHelper(act: MainActivity) {
    private val act = act
    private val accHelper = AccountHelper(act)

    fun createSignDialog(index:Int){
        val builder = AlertDialog.Builder(act)
        val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater)
        val view = rootDialogElement.root
        if(index == DialogConst.SIGN_UP_STATE){
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.account_sign_up)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
        }else{
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.account_sign_in)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
        }
        rootDialogElement.btSignUpIn.setOnClickListener {
            if (index == DialogConst.SIGN_UP_STATE){
                accHelper.signUpWithEmail(rootDialogElement.edSignEmail.text.toString(),
                    rootDialogElement.edSignPassword.text.toString())
            }else{

            }
        }
        builder.setView(view)
        builder.show()
    }
}