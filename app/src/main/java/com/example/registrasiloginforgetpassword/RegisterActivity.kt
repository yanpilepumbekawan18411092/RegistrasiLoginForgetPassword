package com.example.registrasiloginforgetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.registrasiloginforgetpassword.databinding.ActivityForgetPasswordBinding
import com.example.registrasiloginforgetpassword.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val activityLogin = Intent(this, LoginActivity::class.java )
            startActivity(activityLogin)
        }
        // Link untuk berpindah ke halaman Login
        binding.tvLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}