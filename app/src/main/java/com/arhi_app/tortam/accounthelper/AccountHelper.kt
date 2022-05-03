package com.arhi_app.tortam.accounthelper

import android.widget.Toast
import com.arhi_app.tortam.MainActivity
import com.arhi_app.tortam.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase

class AccountHelper(act: MainActivity) {
    private val act = act
    private lateinit var signInClient: GoogleSignInClient

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            act.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendEmailVerification(task.result?.user!!)
                        act.uiUpdate(task.result?.user)
                    } else {
                        Toast.makeText(
                            act,
                            act.resources.getString(R.string.sign_up_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            act.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    act.uiUpdate(task.result?.user)
                    act.hideTextFieldsHeader()
                } else {
                    Toast.makeText(
                        act,
                        act.resources.getString(R.string.sign_in_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
// ПОЛУЧИЛИ КЛИЕНТА
    //GoogleSignInClient - создаёт специальный интент для того, чтобы отправить сообщение к системе
    private fun getSignInClient(): GoogleSignInClient {
//настройки сообщения
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // custom_default_web_client_id скоприровал из "D:\AppTortam\app\build\generated\res\google-services\debug\values\values.xml" - почемуто не работало
            .requestIdToken(act.getString(R.string.custom_default_web_client_id)).build()
        return GoogleSignIn.getClient(act, gso)
    }
    //ПОЛУЧАЕМ КЛИЕНТА
    fun signInWithGoogle(){
        signInClient = getSignInClient()
        // интент для входа
        val intent = signInClient.signInIntent
        //запускаем интенет на активити
        act.startActivityForResult(intent, GoogleAccConst.GOOGLE_SIGN_IN_CODE)

    }

    fun signInFirebaseWithGoogle(token:String){
        val credential = GoogleAuthProvider.getCredential(token, null)
        act.mAuth.signInWithCredential(credential).addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(act, "Sign in done", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    act,
                    act.resources.getString(R.string.send_verification_done),
                    Toast.LENGTH_LONG
                ).show()
                act.hideTextFieldsHeader()
            } else {
                Toast.makeText(
                    act,
                    act.resources.getString(R.string.send_verification_email_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}