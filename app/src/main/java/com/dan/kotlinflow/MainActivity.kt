package com.dan.kotlinflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAge.setOnClickListener {
            //make operation.
            getMyAge()

        }
    }
    fun getMyAge(){
        val userDOB:Int=Integer.parseInt(inputdate.text.toString())
        val currentYear:Int=Calendar.getInstance().get(Calendar.YEAR)
        val userAge=currentYear-userDOB
        seeAge.text="Your Age is $userAge Year"
    }
}