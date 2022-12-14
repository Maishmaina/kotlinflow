package com.dan.kotlinflow

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_login.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Login : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    private var database= FirebaseDatabase.getInstance()
    private var myRef=database?.reference

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
    val storageRef=storage.getReferenceFromUrl("gs://my-firebase-60a1e.appspot.com")
    val df=SimpleDateFormat("ddMMyyyyHHmmss")
    val dateObj= Date()
    val imagePath=currentUser!!.email!!.substring(0,4)+df.format(dateObj)+".jpg"
    var imageRef=storageRef.child("images/"+imagePath)
    imageView.isDrawingCacheEnabled=true
    imageView.buildDrawingCache()

    val drawable=imageView.drawable as BitmapDrawable
    val bitmap=drawable.bitmap
    val baos=ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
    val data=baos.toByteArray()
    val uploadTask=imageRef.putBytes(data)
    uploadTask.addOnFailureListener{
       Toast.makeText(  this, "Upload Failed",Toast.LENGTH_SHORT).show()
    }.addOnSuccessListener {
        taskSnapshot->
        var DownloadURL= taskSnapshot.getMetadata()?.getReference()?.getDownloadUrl().toString();

        myRef!!.child("Users").child(currentUser.uid).child("email").setValue(currentUser.email)
        myRef!!.child("Users").child(currentUser.uid).child("ProfileImage").setValue(DownloadURL)
        LoadTweets()
    }
    }
    override fun onStart() {
        super.onStart()
        LoadTweets()
    }
    //fun load current user
    fun LoadTweets(){
        var currentUser=mAuth!!.currentUser
        if(currentUser !=null){
            var intent= Intent(this,MainActivityTwitter::class.java)
            intent.putExtra("email",currentUser.email)
            intent.putExtra("uid",currentUser.uid)
            startActivity(intent)
        }
    }
    //fun to split string
    fun SplitString(str: String): String{
        val array=str.split("@")
        return array[0]
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


