package com.example.camtest

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {


//comentario
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val buttonCam: Button = findViewById(R.id.btnCam)

        buttonCam.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            } else {
                openCamera()
            }

        }

        val buttonGal: Button = findViewById(R.id.btnGaleria)

        buttonGal.setOnClickListener {
            val intentGaleria = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentGaleria, REQUEST_IMAGE_GALLERY)
        }

    }

    fun getFile(fileName: String): File {
        val directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directory)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openCamera() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val foto = BitmapFactory.decodeFile(fotoFile.absolutePath)
            val image: ImageView = findViewById(R.id.imgView)
            image.setImageBitmap(foto)
        }else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {

            val selectedImageUri = data.data
            val image: ImageView = findViewById(R.id.imgView)
            image.setImageURI(selectedImageUri)
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



}

private val REQUEST_CAMERA_PERMISSION = 200
private val REQUEST_IMAGE_GALLERY = 14
private val REQUEST_IMAGE_CAPTURE = 13
private const val FILE_NAME = "photo.jpg"
private lateinit var fotoFile: File