package com.example.registrasiloginforgetpassword

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.registrasiloginforgetpassword.databinding.ActivityHomeBinding
import com.example.registrasiloginforgetpassword.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    // uri of image to share, pick from galery
    private var imageUri: Uri? = null

    // Veriabel utk menampung Text yg akan dikirim dari edit text
    private var textToShare = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // handle click, pick image
        binding.imageIv.setOnClickListener{
            pickImage()
        }

        // handle click, share text
        binding.shareTextBtn.setOnClickListener {
            //get text from edit text
            textToShare = binding.textEt.text.toString().trim()
            //check if text empty or not
            if (textToShare.isEmpty()) {
                shoeToast("Masukkan text..")
            }
            else {
                shareText()
            }
        }

        // handle click, share Image
        binding.shareImageBtn.setOnClickListener {
            //check image is picked or not
            if (imageUri == null) {
                shoeToast("Pilih gambar...")
            }
            else {
                shareImage()
            }

        }

        // handle click, share image & text
        binding.shareBothBtn.setOnClickListener {
            //get text from edit text
            textToShare = binding.textEt.text.toString().trim()
            //check if text empty or not
            if (textToShare.isEmpty()) {
                shoeToast("Masukkan text..")
            }
            else if (imageUri == null) {
                shoeToast("Pilih gambar..")
            }

        }

        // Tombol untuk Logout dari aplikasi
        binding.btnLogout.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // tombol untuk membuka browser
        binding.btnOpenBrowser.setOnClickListener {
            // Membuat intent utk terhubung ke halaman Browser
            val intentOpenBrowser = Intent(Intent.ACTION_VIEW)
            intentOpenBrowser.data = Uri.parse("https://www.google.com/")
            startActivity(intentOpenBrowser)
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private var galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            // handle image pick result in both cases; either picked or not
            if (result.resultCode == Activity.RESULT_OK) {
                //image pick
                shoeToast("Image pick from gallery")
                // get image uri
                val intent = result.data
                imageUri = intent!!.data
                //set image to imagevies
                binding.imageIv.setImageURI(imageUri)

            }
            else {
                // cancelled
                shoeToast("Cancelled")
            }

        }
    )
    private fun shoeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun shareText() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
        intent.putExtra(Intent.EXTRA_TEXT, textToShare)
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    private fun shareImage() {
        val contentUri = getContentUri()

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
        intent.putExtra(Intent.EXTRA_STREAM, contentUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    private fun shareImageText() {
        val contentUri = getContentUri()

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
        intent.putExtra(Intent.EXTRA_TEXT, textToShare)
        intent.putExtra(Intent.EXTRA_STREAM, contentUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    private fun getContentUri(): Uri? {
        val bitmap: Bitmap
        //get bitmap from uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, imageUri!!)
            bitmap = ImageDecoder.decodeBitmap(source)
        }
        else {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        }

        //if your want to get bitmap from imageview instead of uri
        // val bitmapDrawable = binding.imageIv.drawable as BitmapDrawable
        // bitmap = bitmapDrawable.bitmap

        val imageFolder = File(cacheDir, "images")
        var contentUri: Uri? = null
        try {
            imageFolder.mkdirs()//create folder if not exisits
            val file = File(imageFolder, "shared_image.png")
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)
            stream.flush()
            stream.close()
            contentUri = FileProvider.getUriForFile(this, "com.example.registrasiloginforgetpassword.fileprovider", file)
        }
        catch (e: Exception) {
            shoeToast("${e.message}")
        }

        return contentUri
    }

}