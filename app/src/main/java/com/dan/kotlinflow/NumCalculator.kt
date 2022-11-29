package com.dan.kotlinflow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class NumCalculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_num_calculator);

    }
fun butNumberEvent(view:View){
Toast.makeText(this,"zero clicked",Toast.LENGTH_LONG).show()
}

}