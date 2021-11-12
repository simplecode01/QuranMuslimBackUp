package com.simplecode01.quranmuslim.firebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.databinding.FragmentUserBinding

class UserFragment: Fragment(R.layout.fragment_user) {

    private val binding: FragmentUserBinding by viewBinding()
    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

    }
    private fun checkUser() {
        //Check user is logged in or no
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            val email = firebaseUser.email
            binding.emailTv.text = email
        }else{
            val Pindah = Intent(context, Login::class.java)
            startActivity(Pindah)
        }
    }

}