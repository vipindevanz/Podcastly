package com.pns.podcastly.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.pns.podcastly.R
import com.pns.podcastly.utils.Constants
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity()  {

    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null){
            launchMainScreen()
        }

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleAuth.setOnClickListener {
            authProgress.visibility = View.VISIBLE
            googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, Constants.RC_SIGN_IN)
        }

        skipAuth.setOnClickListener { launchMainScreen() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.RC_SIGN_IN){

            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {

                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)

            } catch (e : Exception){

            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)

        firebaseAuth.signInWithCredential(credential).addOnSuccessListener{

            val firebaseUser = firebaseAuth.currentUser
            val uid = firebaseUser?.uid
            val email = firebaseUser?.email

            Log.d(Constants.DEBUG_TAG, "$email $uid")

            if (it.additionalUserInfo?.isNewUser == true){

                Log.d(Constants.DEBUG_TAG, "New user")

            } else{

                Log.d(Constants.DEBUG_TAG, "Existing user")
            }

            launchMainScreen()

        } .addOnFailureListener{

            Log.d(Constants.DEBUG_TAG, "${it.message}")
        }
    }

    private fun launchMainScreen(){
        authProgress.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}