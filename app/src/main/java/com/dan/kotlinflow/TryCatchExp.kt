package com.dan.kotlinflow

class TryCatchExp {

}
fun main(args:Array<String>){
    try {
        println("Enter a Number")
        var no2:Int= readLine()!!.toInt()
        var div=100/no2
        println("DIV:$div")
    }catch (e:Exception){
       println(e.message)
    }

    println("result found");
}