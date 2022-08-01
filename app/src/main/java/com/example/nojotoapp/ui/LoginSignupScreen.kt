package com.example.nojotoapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nojotoapp.Adapters.ViewPagerAdapter
import com.example.nojotoapp.R
import com.example.nojotoapp.extensions.autoScroll
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import kotlinx.android.synthetic.main.activity_login_signup_screen.*
import java.util.concurrent.TimeUnit


class LoginSignupScreen : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var adapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup_screen)
        setAdapter()
        setViewPager()



        supportActionBar?.hide()

        btnFb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

       getLogin()
    }

    private fun getLogin() {
        cont.setOnClickListener {
            if (!ed1.getText().toString().trim { it <= ' ' }.isEmpty()) {
                if (ed1.getText().toString().trim { it <= ' ' }.length == 10) {
                    // progressBar.setVisibility(View.VISIBLE)
                    cont.setVisibility(View.INVISIBLE)
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + ed1.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        this,
                        object : OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                                //  progressBar.setVisibility(View.GONE)
                                cont.setVisibility(View.VISIBLE)
                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                // progressBar.setVisibility(View.GONE)
                                cont.setVisibility(View.VISIBLE)
//                                Toast.makeText(
//                                    this,
//                                    e.message,
//                                    Toast.LENGTH_SHORT
//                                ).show()
                            }

                            override fun onCodeSent(
                                backendotp: String,
                                forceResendingToken: ForceResendingToken
                            ) {
                                // progressBar.setVisibility(View.GONE)
                                cont.setVisibility(View.VISIBLE)
                                val intent = Intent(
                                    applicationContext,
                                    verifyenterotptwo::class.java
                                )
                                intent.putExtra("mobile", ed1.getText().toString())
                                intent.putExtra("backendotp", backendotp)
                                startActivity(intent)
                            }
                        }
                    )

//                        Intent intent = new Intent(getApplicationContext(),verifyenterotptwo.class);
//                        intent.putExtra("mobile",enternumber.getText().toString());
//                        startActivity(intent);
                } else {
                    Toast.makeText(
                        this,
                        "Please enter correct number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Enter Mobile number", Toast.LENGTH_SHORT)
                    .show()
            }


            FirebaseApp.initializeApp(this)

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            firebaseAuth = FirebaseAuth.getInstance()

            Signin.setOnClickListener { view: View? ->
                Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show()
                signInGoogle()
            }
        }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    // onActivityResult() function : this is where
    // we provide the task and data for the Google Account
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
       // finish()
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
           // handleResult(task)
        }
    }


    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
//                SavedPreference.setEmail(this, account.email.toString())
//                SavedPreference.setUsername(this, account.displayName.toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(
                Intent(
                    this, MainActivity
                    ::class.java
                )
            )
            finish()
        }

    }


    private fun setAdapter() {

        this.adapter = ViewPagerAdapter(
            listOf(
                R.drawable.one,
                R.drawable.two,
                R.drawable.three
            )
        )
    }

    private fun setViewPager() {
        viewPager.adapter = adapter

        /**
         * Start automatic scrolling with an
         * interval of 3 seconds.
         */
        viewPager.autoScroll(3000)
    }
}