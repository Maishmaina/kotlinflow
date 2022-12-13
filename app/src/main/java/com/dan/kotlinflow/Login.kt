package com.dan.kotlinflow

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_login.*
import java.text.SimpleDateFormat
import java.util.*

class Login : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        //mAuth?.signInAnonymously()

        imageView.setOnClickListener(View.OnClickListener {
            checkPermission()

        })
    }
//loginToFirebase
    fun loginToFirebase(email: String,password: String){
        mAuth!!.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener (this){ task->
                if (task.isSuccessful){

                    UploadImageToFirebase()
//                    if(currentUser!=null){
//                        myRef.child("Users").child(SplitString(currentUser.email.toString())).child("Request").setValue(currentUser)
//                    }
                }else{
                    Toast.makeText(this@Login, task.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
    }
//upload image to firebase
    fun UploadImageToFirebase(){
    var currentUser=mAuth!!.currentUser

    val storage=FirebaseStorage.getInstance()
    val storageRef=storage.getReference("gs://my-firebase-60a1e.appspot.com")
    val df=SimpleDateFormat("ddMMyyyyHHmmss")
    val dateObj= Date()
    val imagePath=currentUser!!.email!!.substring(0,4)+df.format(dateObj)+".jpg"
    }
    val READPERM:Int=10002
    var PICK_IMAGE_CODE:Int =10003

    fun checkPermission(){
        if(Build.VERSION.SDK_INT>=23){
           if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
               ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),READPERM)
               return
           }
        }
        LoadImage()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            READPERM -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    LoadImage()
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun LoadImage(){

         var intent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,PICK_IMAGE_CODE)
    }
    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
         if(requestCode==PICK_IMAGE_CODE && resultCode== RESULT_OK && data != null){
             val selectedImage = data.data
             val filePathColumn=arrayOf(MediaStore.Images.Media.DATA)
             val cursor = contentResolver.query(selectedImage!!,filePathColumn,null,null,null)
             cursor!!.moveToFirst()
             val imagePath = cursor!!.getString(cursor!!.getColumnIndex(filePathColumn[0]))
             cursor.close()
             imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath))
         }
    }

    fun buLogin(view: View){
        loginToFirebase(editEmailAddress.text.toString(),editPassword.text.toString())
    }
}