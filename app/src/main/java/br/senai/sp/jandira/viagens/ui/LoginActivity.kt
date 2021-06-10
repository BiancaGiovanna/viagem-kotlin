package br.senai.sp.jandira.viagens.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import br.senai.sp.jandira.viagens.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

const val  RC_SIGN_IN = 666

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var btnlogin: SignInButton
    lateinit var mGoogleSignInClient:GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnlogin = findViewById(R.id.sign_in_button)
        btnlogin.setOnClickListener(this)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            updateUI()
        }
    }

    private fun updateUI() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.sign_in_button) {
            autenticar()
        }
    }

    private fun autenticar() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            val user = task.getResult(ApiException::class.java)
            if(user != null){
                Log.d("xpto", user.displayName.toString())
                Log.d("xpto", user.email.toString())
                Log.d("xpto", user.photoUrl.toString())


            }
        }
    }

}