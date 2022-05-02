package com.arhi_app.tortam.dialoghelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.arhi_app.tortam.MainActivity
import com.arhi_app.tortam.R
import com.arhi_app.tortam.accounthelper.AccountHelper
import com.arhi_app.tortam.databinding.SignDialogBinding

class DialogHelper(act: MainActivity) {
    private val act = act
    private val accHelper = AccountHelper(act)

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(act)
        val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater)
        val view = rootDialogElement.root
        builder.setView(view)


        setDialogState(index, rootDialogElement)

        val dialog = builder.create()
        rootDialogElement.btSignUpIn.setOnClickListener {
            setOnClickSignUpIn(index, rootDialogElement, dialog)
        }

        rootDialogElement.btForgetP.setOnClickListener {
            setOnClickResetPassword(rootDialogElement, dialog)
        }
        dialog.show()
    }

    private fun setOnClickResetPassword(
        rootDialogElement: SignDialogBinding,
        dialog: AlertDialog?
    ) {
        if (rootDialogElement.edSignEmail.text.isNotEmpty()) {
            act.mAuth.sendPasswordResetEmail(rootDialogElement.edSignEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            act,
                            R.string.email_reset_password_was_sent,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            dialog?.dismiss()
        } else {
            rootDialogElement.edSignPassword.visibility = View.GONE
            rootDialogElement.btSignUpIn.visibility = View.GONE
            rootDialogElement.tvDialogMessage.visibility = View.VISIBLE
            rootDialogElement.btForgetP.text = act.resources.getString(R.string.account_reset_password)
        }
    }

    private fun setOnClickSignUpIn(
        index: Int,
        rootDialogElement: SignDialogBinding,
        dialog: AlertDialog?
    ) {
        dialog?.dismiss()
        if (index == DialogConst.SIGN_UP_STATE) {
            accHelper.signUpWithEmail(
                rootDialogElement.edSignEmail.text.toString(),
                rootDialogElement.edSignPassword.text.toString()
            )
        } else {
            accHelper.signInWithEmail(
                rootDialogElement.edSignEmail.text.toString(),
                rootDialogElement.edSignPassword.text.toString()
            )
        }
    }

    private fun setDialogState(index: Int, rootDialogElement: SignDialogBinding) {
        if (index == DialogConst.SIGN_UP_STATE) {
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.account_sign_up)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
        } else {
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.account_sign_in)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
            rootDialogElement.btForgetP.visibility = View.VISIBLE
        }
    }
}