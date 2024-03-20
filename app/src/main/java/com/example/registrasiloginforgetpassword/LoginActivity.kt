package com.example.registrasiloginforgetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.registrasiloginforgetpassword.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Tombol berpindah ke halaman Home
        binding.btnLogin.setOnClickListener {
            val activityHome = Intent(this,MainActivity::class.java)
            startActivity(activityHome)
        }

        // Link untuk berpindah ke halaman lupa Password
        binding.linkForgetPassword.setOnClickListener{
            val intent = Intent(this,ForgetPasswordActivity::class.java)
            startActivity(intent)

            // Pesan saat pindah ke halaman forget password
            Toast.makeText(this, "Halaman forget password", Toast.LENGTH_SHORT).show()
        }

        // Link untuk berpindah ke halaman Register
        binding.createAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

            //Pesan saat pindah ke halaman register
            Toast.makeText(this, "Halaman Registrasi", Toast.LENGTH_SHORT).show()
        }
    }
}