package com.dan.kotlinflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class TicTao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tao)
    }
    fun btClicked(view:View){
   val btselected=view as Button
   var cellid=0
        when(btselected.id){
            R.id.but1 -> cellid=1
            R.id.but2 -> cellid=2
            R.id.but3 -> cellid=3
            R.id.but4 -> cellid=4
            R.id.but5 -> cellid=5
            R.id.but6 -> cellid=6
            R.id.but7 -> cellid=7
            R.id.but8 -> cellid=8
            R.id.but9 -> cellid=9
        }
        Toast.makeText(this,"clicked btn is "+btselected.id+" Implies "+cellid,Toast.LENGTH_LONG).show()
    }
}