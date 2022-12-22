package com.dan.kotlinflow

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.AdView
//import com.google.android.gms.ads.MobileAds
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main_twitter.*
import kotlinx.android.synthetic.main.add_twitter.view.*
import kotlinx.android.synthetic.main.single_tweet.view.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivityTwitter : AppCompatActivity() {
    private var database= FirebaseDatabase.getInstance()
    private var myRef=database?.reference
    var ListTweets=ArrayList<TwitterModel>()
    var adapter:MyTweetAdpater?=null
    var myemail:String?=null
    var UserUID: String? = null
//    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_twitter)
        var b: Bundle? =intent.extras
        if (b!=null) {
            myemail=b.getString("email")
            UserUID=b.getString("uid")
        }
        //data source
        LoadPost()
        adapter= MyTweetAdpater(this,ListTweets)
        lstweets.adapter=adapter

    }
    inner class  MyTweetAdpater: BaseAdapter {
        var listNotesAdpater=ArrayList<TwitterModel>()
        var context: Context?=null
        constructor(context:Context, listNotesAdpater:ArrayList<TwitterModel>):super(){
            this.listNotesAdpater=listNotesAdpater
            this.context=context
        }
        @SuppressLint("MissingPermission")
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var mytweet=listNotesAdpater[p0]

            if(mytweet.tweetPersonUID!!.equals("add")) {
                var myView = layoutInflater.inflate(R.layout.add_twitter, null)
                myView.iv_attach.setOnClickListener(View.OnClickListener {
                    loadImage()
                })
                myView.iv_post.setOnClickListener(View.OnClickListener {
                    //upload server
                    if(!DownloadURL!!.equals(null)){
                    myRef!!.child("posts").push().setValue(
                        PostInfo(UserUID!!,
                            myView.etPost.text.toString(), DownloadURL!!))

                    myView.etPost.setText("")
                    }else{
                        Toast.makeText(getApplicationContext(),"Some Fields are missing",Toast.LENGTH_SHORT).show()
                    }
                })
                return myView
            } else if(mytweet.tweetPersonUID!!.equals("loading")){
                var myView=layoutInflater.inflate(R.layout.loading_tweet,null)
                return myView
//            }
//            else if(mytweet.tweetPersonUID.equals("ads")){
//                var myView=layoutInflater.inflate(R.layout.ads_tweet,null)

//                var mAdView = myView.findViewById(R.id.adView) as AdView
//                val adRequest = AdRequest.Builder().build()
//                mAdView.loadAd(adRequest)

//                return myView
            }else{
                var myView=layoutInflater.inflate(R.layout.single_tweet,null)
                myView.txt_tweet.text = mytweet.tweetText

                Picasso.get().load(mytweet.tweetImageURL).into(myView.tweet_picture)
                myRef?.child("Users")?.child(mytweet.tweetPersonUID!!)
                    ?.addValueEventListener(object :ValueEventListener{
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            try {
                                var td= dataSnapshot!!.value as HashMap<String,Any>
                                for(key in td.keys){

                                    var userInfo= td[key] as String
                                    if(key.equals("ProfileImage")){
                                        Picasso.get().load(userInfo).into(myView.picture_path)
                                    }else{
                                        myView.txtUserName.text = userInfo
                                    }
                                }

                            }catch (ex:Exception){ex.printStackTrace()}

                        }
                        override fun onCancelled(p0: DatabaseError) {

                        }
                    })

                return myView
            }
        }

        override fun getItem(p0: Int): Any {
            return listNotesAdpater[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {

            return listNotesAdpater.size

        }
    }

    //load image...loadImage
    val PICK_IMAGE_CODE:Int=10003;
    fun loadImage(){
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
            UploadImage(BitmapFactory.decodeFile(imagePath))
        }
    }
    var DownloadURL:String?=null

    fun UploadImage(bitmap:Bitmap){
        ListTweets.add(0, TwitterModel("0","loading","loading","loading"))
        adapter?.notifyDataSetChanged()
        val storage= FirebaseStorage.getInstance()
        val storageRef=storage.getReferenceFromUrl("gs://my-firebase-60a1e.appspot.com")
        val df= SimpleDateFormat("ddMMyyyyHHmmss")
        val dateObj= Date()
        val imagePath=myemail!!.substring(0,4)+df.format(dateObj)+".jpg"
        var imageRef=storageRef.child("imagesPost/"+imagePath)
        val baos= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data=baos.toByteArray()
        val uploadTask=imageRef.putBytes(data)

      uploadTask.addOnCompleteListener(OnCompleteListener {
          if (it.isSuccessful)
          {
              it.getResult().getStorage().getDownloadUrl().addOnSuccessListener {
                  DownloadURL=it.toString()
                  ListTweets.removeAt(0)
                  adapter?.notifyDataSetChanged()
//                  Log.d("imageLink",DownloadURL!!)
              }
          }
      }).addOnFailureListener{
          Log.d("imageLinkException","Firebase Storage Exception")
      }
    }
    fun LoadPost(){
        myRef!!.child("posts").addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    ListTweets.clear()
                    var td= dataSnapshot!!.value as HashMap<String,Any>
                    Log.d("fireresult",td.toString())
                    ListTweets.add(TwitterModel("o","add","add","add"));
//                    ListTweets.add(TwitterModel("09","ads","ads","ads"));
                    for(key in td.keys){
                        var post= td[key] as HashMap<String,Any>
                        ListTweets.add(TwitterModel(key,post["text"] as String, post["postImage"] as String, post["userUID"] as String))
                    }
                    adapter!!.notifyDataSetChanged()
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"error found",Toast.LENGTH_LONG).show()
            }

        })
    }
}


