package com.arhi_app.tortam.accounthelper

import android.util.Log
import android.view.View
import android.widget.Toast
import com.arhi_app.tortam.MainActivity
import com.arhi_app.tortam.R
import com.arhi_app.tortam.constants.FirebaseAuthConstants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
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
                        Log.d("MyLog", "Exception : ${task.exception}")
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            val exception = task.exception as FirebaseAuthUserCollisionException

                            if (exception.errorCode == FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE) {
//                                Log.d("MyLog", "Exception : ${exception.errorCode}")
                                Toast.makeText(
                                    act,
                                    FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE,
                                    Toast.LENGTH_LONG
                                ).show()
                                // Link email
                                linkEmailToG(email, password)
                            }
                        } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            val exception =
                                task.exception as FirebaseAuthInvalidCredentialsException
                            if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
                                Toast.makeText(
                                    act,
                                    FirebaseAuthConstants.ERROR_INVALID_EMAIL,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        if (task.exception is FirebaseAuthWeakPasswordException) {
                            val exception = task.exception as FirebaseAuthWeakPasswordException
                            Log.d("MyLog", "Exception : ${exception.errorCode}")
                            if (exception.errorCode == FirebaseAuthConstants.ERROR_WEAK_PASSWORD) {
                                Toast.makeText(
                                    act,
                                    FirebaseAuthConstants.ERROR_WEAK_PASSWORD,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
        }
    }

    private fun linkEmailToG(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        if (act.mAuth.currentUser != null) {

            act.mAuth.currentUser?.linkWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        act,
                        act.resources.getString(R.string.link_done),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                act,
                act.resources.getString(R.string.enter_to_g),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            act.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    act.uiUpdate(task.result?.user)
                    act.hideTextFieldsHeader()
                } else {
                    Log.d("MyLog", "Exception 1: ${task.exception}")


                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        val exception = task.exception as FirebaseAuthInvalidCredentialsException
                        Log.d("MyLog", "Exception 2: ${exception.errorCode}")
                        if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
                            Toast.makeText(
                                act,
                                FirebaseAuthConstants.ERROR_INVALID_EMAIL,
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (exception.errorCode == FirebaseAuthConstants.ERROR_WRONG_PASSWORD) {
                            Toast.makeText(
                                act,
                                FirebaseAuthConstants.ERROR_WRONG_PASSWORD,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else if (task.exception is FirebaseAuthInvalidUserException) {
                        val exception = task.exception as FirebaseAuthInvalidUserException
                        Log.d("MyLog", "Exception errorCode: ${exception.errorCode}")
                        if (exception.errorCode == FirebaseAuthConstants.ERROR_USER_NOT_FOUND) {
                            Toast.makeText(
                                act,
                                FirebaseAuthConstants.ERROR_USER_NOT_FOUND,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    // ???????????????? ??????????????
    //GoogleSignInClient - ?????????????? ?????????????????????? ???????????? ?????? ????????, ?????????? ?????????????????? ?????????????????? ?? ??????????????
    private fun getSignInClient(): GoogleSignInClient {
        //?????????????????? ??????????????????
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // custom_default_web_client_id ?????????????????????? ???? "D:\AppTortam\app\build\generated\res\google-services\debug\values\values.xml" - ???????????????? ???? ????????????????
            .requestIdToken(act.getString(R.string.custom_default_web_client_id)).requestEmail()
            .build()
        return GoogleSignIn.getClient(act, gso)
    }

    //???????????????? ??????????????
    fun signInWithGoogle() {
        signInClient = getSignInClient()
        // ???????????? ?????? ??????????
        val intent = signInClient.signInIntent
        //?????????????????? ?????????????? ???? ????????????????
        act.startActivityForResult(intent, GoogleAccConst.GOOGLE_SIGN_IN_CODE)

    }

    fun signOutG() {
        getSignInClient().signOut()
    }

    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        act.mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(act, "Sign in done", Toast.LENGTH_LONG).show()
                act.uiUpdate(task.result?.user)
            } else {
                Log.d("MyLog", "Google Sign In Exception : ${task.exception}")
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