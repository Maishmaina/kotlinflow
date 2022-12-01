package com.dan.kotlinflow

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_num_calculator.*

class NumCalculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_num_calculator);

    }
fun butNumberEvent(view:View){
if(isNewOprt===true){
    editshowdata.setText("");
}
    isNewOprt=false
    val buSelect =view as Button
    var btClickedValue:String = editshowdata.text.toString()
    when(buSelect.id){
        bu0.id->{
            btClickedValue+="0"
        }
        bu1.id->{
            btClickedValue+="1"
        }
        bu2.id->{
            btClickedValue+="2"
        }
        bu3.id->{
            btClickedValue+="3"
        }
        bu4.id->{
            btClickedValue+="4"
        }
        bu5.id->{
            btClickedValue+="5"
        }
        bu6.id->{
            btClickedValue+="6"
        }
        bu7.id->{
            btClickedValue+="7"
        }
        bu8.id->{
            btClickedValue+="8"
        }
        bu9.id->{
            btClickedValue+="9"
        }
        budot.id->{
            btClickedValue+="."
        }
        buplusminus.id->{
            btClickedValue="-"+ btClickedValue
        }
    }
    editshowdata.setText(btClickedValue)
}
    var opt="*"
    var oldNum=""
    var isNewOprt=true
    fun buOpEvent(view: View){
        val buSelect =view as Button

        when(buSelect.id) {
            budiv.id -> {
                opt= "/"
            }
            bumult.id -> {
                opt= "*"
            }
            buminu.id -> {
                opt= "-"
            }
            buplus.id -> {
                opt= "+"
            }
        }
        oldNum=editshowdata.text.toString()
        isNewOprt= true
    }
    fun buOpEqual(view: View){
        var newNumber=editshowdata.text.toString()
        var resuls:Double?=null
        when(opt){
            "*"->{
                resuls=oldNum.toDouble()*newNumber.toDouble()
            }
            "/"->{
                resuls=oldNum.toDouble()/newNumber.toDouble()
            }
            "-"->{
                resuls=oldNum.toDouble()-newNumber.toDouble()

            }
            "+"->{
                resuls=oldNum.toDouble()+newNumber.toDouble()
            }
        }
        editshowdata.setText(resuls.toString())
        isNewOprt=true
    }
    fun buPercent(view: View){
        var number:Double=editshowdata.text.toString().toDouble()/100
        editshowdata.setText(number.toString())
        isNewOprt=true
    }
    fun buClean(view: View){
        editshowdata.setText("0");
        isNewOprt=true
    }

}