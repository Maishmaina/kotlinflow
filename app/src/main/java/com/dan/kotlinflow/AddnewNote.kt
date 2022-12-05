package com.dan.kotlinflow

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_addnew_note.*

class AddnewNote : AppCompatActivity() {
    var dbTable="Notes"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnew_note)
    }
    fun buAddNote(view: View){

      var DbManager=DbManager(this)

        var values=ContentValues()
        values.put("Title",editTitle.text.toString())
        values.put("Description",editdetails.text.toString())

        var ID=DbManager.Insert(values)

        if (ID>0){

            Toast.makeText(applicationContext,"Item Added ",Toast.LENGTH_LONG).show()
            finish()
        }else{

            Toast.makeText(applicationContext,"Sorry error occurred",Toast.LENGTH_LONG).show()
        }
    }
}