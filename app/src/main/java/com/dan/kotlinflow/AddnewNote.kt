package com.dan.kotlinflow

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_addnew_note.*
import java.lang.Exception

class AddnewNote : AppCompatActivity() {
    var dbTable="Notes"
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnew_note)

        var bundle: Bundle? =intent.extras
        try {
            id=bundle!!.getInt("ID",0)
            if (id !=0) {
                editTitle.setText(bundle.getString("title"))
                editdetails.setText(bundle.getString("Description"))
            }
        }catch (ex:Exception){

        }
    }
    fun buAddNote(view: View){

      var DbManager=DbManager(this)

        var values=ContentValues()
        values.put("Title",editTitle.text.toString())
        values.put("Description",editdetails.text.toString())

        if (id==0) {
            var ID=DbManager.Insert(values)
            if (ID > 0) {
                Toast.makeText(applicationContext, "Item Added ", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "Sorry error occurred", Toast.LENGTH_LONG).show()
            }
        }else{
            var selectionArgs= arrayOf(id.toString())
            var ID=DbManager.Update(values,"ID=?",selectionArgs)
            if (ID > 0) {
                Toast.makeText(applicationContext, "Item Updated successfully ", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "Sorry error occurred, not Updated", Toast.LENGTH_LONG).show()
            }
        }

    }
}