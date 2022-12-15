package com.dan.kotlinflow

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main_twitter.*

class MainActivityTwitter : AppCompatActivity() {
    var ListTweets=ArrayList<TwitterModel>()
    var adpater:MyTweetAdpater?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_twitter)

        //dummy data source
        ListTweets.add(TwitterModel("0","one", "url", "add"))
        ListTweets.add(TwitterModel("0","one", "url", "loading"))
        ListTweets.add(TwitterModel("0","one", "url", "daniel"))
        ListTweets.add(TwitterModel("0","one", "url", "daniel"))
        ListTweets.add(TwitterModel("0","one", "url", "daniel"))

        adpater= MyTweetAdpater(this,ListTweets)
        lstweets.adapter=adpater

    }
    inner class  MyTweetAdpater: BaseAdapter {
        var listNotesAdpater=ArrayList<TwitterModel>()
        var context: Context?=null
        constructor(context:Context, listNotesAdpater:ArrayList<TwitterModel>):super(){
            this.listNotesAdpater=listNotesAdpater
            this.context=context
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {


            var mytweet=listNotesAdpater[p0]

            if(mytweet.tweetPersonUID.equals("add")) {
                var myView = layoutInflater.inflate(R.layout.add_twitter, null)

//                myView.iv_attach.setOnClickListener(View.OnClickListener {
//                    loadImage()
//
//                })

//                myView.iv_post.setOnClickListener(View.OnClickListener {
//                    //upload server
//
//                    myRef.child("posts").push().setValue(
//                        PostInfo(UserUID!!,
//                            myView.etPost.text.toString(), DownloadURL!!))
//
//                    myView.etPost.setText("")
//                })
                return myView
            } else if(mytweet.tweetPersonUID.equals("loading")){
                var myView=layoutInflater.inflate(R.layout.loading_tweet,null)
                return myView
            } else if(mytweet.tweetPersonUID.equals("ads")){
                var myView=layoutInflater.inflate(R.layout.single_tweet,null)

//                var mAdView = myView.findViewById(R.id.adView) as AdView
//                val adRequest = AdRequest.Builder().build()
//                mAdView.loadAd(adRequest)
                return myView
            }else{
                var myView=layoutInflater.inflate(R.layout.single_tweet,null)
               // myView.txt_tweet.text = mytweet.tweetText

                //myView.tweet_picture.setImageURI(mytweet.tweetImageURL)
//                Picasso.with(context).load(mytweet.tweetImageURL).into(myView.tweet_picture)
//
//
//                myRef.child("Users").child(mytweet.tweetPersonUID!!)
//                    .addValueEventListener(object :ValueEventListener{
//
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                            try {
//
//                                var td= dataSnapshot!!.value as HashMap<String,Any>
//
//                                for(key in td.keys){
//
//                                    var userInfo= td[key] as String
//                                    if(key.equals("ProfileImage")){
//                                        Picasso.with(context).load(userInfo).into(myView.picture_path)
//                                    }else{
//                                        myView.txtUserName.text = userInfo
//                                    }
//
//
//
//                                }
//
//                            }catch (ex:Exception){}
//
//
//                        }
//
//                        override fun onCancelled(p0: DatabaseError) {
//
//                        }
//                    })

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
}