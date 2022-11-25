package com.dan.kotlinflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tic_tao.*
import java.util.*
import kotlin.collections.ArrayList

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
       playGame(cellid,btselected);
    }
    var activePlay=1;

    var player1=ArrayList<Int>()
    var player2=ArrayList<Int>()

    fun playGame(cellid:Int,btselected:Button){
        if (activePlay==1){
            btselected.text="X"
            btselected.setBackgroundResource(R.color.blue)
            player1.add(cellid)
            activePlay=2
            autoPlay()
        }else{
            btselected.text="O"
            btselected.setBackgroundResource(R.color.darkGreen)
            player2.add(cellid)
            activePlay=1

        }
        btselected.isEnabled=false
        checkWinner()
    }
    fun checkWinner(){
        var win=-1
        //row 1
        if(player1.contains(1) && player1.contains(2) && player1.contains(3) ){
          win=1
        }
        if(player2.contains(1) && player2.contains(2) && player2.contains(3) ){
         win=2
        }
        //row 2
        if(player1.contains(4) && player1.contains(5) && player1.contains(6) ){
            win=1
        }
        if(player2.contains(4) && player2.contains(5) && player2.contains(6) ){
            win=2
        }
        //row 3
        if(player1.contains(7) && player1.contains(8) && player1.contains(9) ){
            win=1
        }
        if(player2.contains(6) && player2.contains(8) && player2.contains(9) ){
            win=2
        }

        //col 1
        if(player1.contains(1) && player1.contains(4) && player1.contains(7) ){
            win=1
        }
        if(player2.contains(1) && player2.contains(4) && player2.contains(7) ){
            win=2
        }
        //col 2
        if(player1.contains(2) && player1.contains(5) && player1.contains(8) ){
            win=1
        }
        if(player2.contains(2) && player2.contains(5) && player2.contains(8) ){
            win=2
        }
        //col 3
        if(player1.contains(3) && player1.contains(6) && player1.contains(9) ){
            win=1
        }
        if(player2.contains(3) && player2.contains(6) && player2.contains(9) ){
            win=2
        }
        //diag 1
        if(player1.contains(1) && player1.contains(5) && player1.contains(9) ){
            win=1
        }
        if(player2.contains(1) && player2.contains(5) && player2.contains(9) ){
            win=2
        }
        //diag 2
        if(player1.contains(3) && player1.contains(5) && player1.contains(7) ){
            win=1
        }
        if(player2.contains(3) && player2.contains(5) && player2.contains(7) ){
            win=2
        }

        if(win==1){
            play1WinsCount+=1
            Toast.makeText(this,"Player One is Winner",Toast.LENGTH_LONG).show()
            restartGame()
        }else if(win==2){
            play2WinsCount+=1
            Toast.makeText(this,"Player Two is Winner",Toast.LENGTH_LONG).show()
            restartGame()

        }
    }
    fun autoPlay(){
        var emptyCell=ArrayList<Int>();
        for (cellid:Int in 1..9){
            if(!(player1.contains(cellid)) || player2.contains(cellid)){
                emptyCell.add(cellid)
            }
        }
        if(emptyCell.size==0){
            restartGame()
        }
        val r= Random()
        val randIndex:Int=r.nextInt(emptyCell.size)
        val cellid:Int=emptyCell[randIndex]

        var btselected:Button?

        btselected= when(cellid){
            1->but1
            2->but2
            3->but3
            4->but4
            5->but5
            6->but6
            7->but7
            8->but8
            9->but9
            else-> {but1}
        }
        playGame(cellid,btselected)



    }
    var play1WinsCount=0
    var play2WinsCount=0

     fun restartGame() {
         activePlay = 1
         player1.clear()
         player2.clear()
         for (cellid in 1..9) {
             var btselected: Button?

             btselected = when (cellid) {
                 1 -> but1
                 2 -> but2
                 3 -> but3
                 4 -> but4
                 5 -> but5
                 6 -> but6
                 7 -> but7
                 8 -> but8
                 9 -> but9
                 else -> {
                     but1
                 }
             }
             btselected!!.text=""
             btselected!!.setBackgroundResource(R.color.white);
             btselected.isEnabled=true
         }
         Toast.makeText(this,"Player 1: $play1WinsCount, Player 2: $play2WinsCount",Toast.LENGTH_LONG).show()
     }
}