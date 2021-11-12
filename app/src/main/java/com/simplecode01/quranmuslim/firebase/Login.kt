package com.simplecode01.quranmuslim.firebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.simplecode01.quranmuslim.MainActivity
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivityLoginBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //Proses Dialog
    private lateinit var progressDialog: ProgressDialog
    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure action bar
//        actionBar = supportActionBar!!
//        actionBar.title = "Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, open register, activity
        binding.noAccountTv.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        //handle click, begin login
        binding.loginBtn.setOnClickListener {
            validateData()
        }

    }
    private fun validateData() {
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEdt.text.toString().trim()


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error = "Invalid email format"
        }else if(TextUtils.isEmpty(password)){
            binding.passwordEdt.error = "Please enter password"
        }else{
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                //get user Info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}