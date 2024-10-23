package com.example.camtest

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.btnCam)

        button.setOnClickListener {
            val intentPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            fotoFile = getFile(FILE_NAME)

            val providerFile = FileProvider.getUriForFile(this,"com.example.camtest.fileprovider",
                fotoFile)
            intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)

            if (intentPhoto.resolveActivity(this.packageManager) != null){
                startActivityForResult(intentPhoto, REQUEST_IMAGE_CAPTURE)
            }else{
                Toast.makeText(this,"Camara no disponible",Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun getFile(fileName: String): File {
        val directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val foto = BitmapFactory.decodeFile(fotoFile.absolutePath)
            val image: ImageView = findViewById(R.id.imgView)
            image.setImageBitmap(foto)
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}

private val REQUEST_IMAGE_CAPTURE = 13
private const val FILE_NAME = "photo.jpg"
private lateinit var fotoFile: File