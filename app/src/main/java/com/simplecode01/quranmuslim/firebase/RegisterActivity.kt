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
import com.simplecode01.quranmuslim.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        actionBar = supportActionBar!!
//        actionBar.title = "Sign Up"
//        actionBar.setDisplayHomeAsUpEnabled(true)
//        actionBar.setDisplayHomeAsUpEnabled(true)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account ...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.signUpBtn.setOnClickListener {
            validateData()

        }

        binding.loginBckBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
    private fun validateData() {
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEdt.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error = "Invalid email format"
        }else if(TextUtils.isEmpty(password)){
            binding.passwordEdt.error = "Please enter password"
        }else if(password.length <6){
            binding.passwordEdt.error = "Password must at least 6 characters long"
        }
        else{
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{ e ->
                progressDialog.dismiss()
                Toast.makeText(this, "SignUp Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}