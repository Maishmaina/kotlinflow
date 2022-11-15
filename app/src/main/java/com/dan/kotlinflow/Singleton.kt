package com.dan.kotlinflow

class Singleton {
    var name:String?=null
   private constructor(){
        println("its result found")
    }
    ///create companion object.
    companion object{
        val instance:Singleton by lazy { Singleton() }
    }
}
fun main(args:Array<String>){
    var sa=Singleton.instance
    sa.name="Daniel"
    println(sa.name)

    var sb=Singleton.instance
    println(sb.name)

    var ss=Singleton.instance
    println(ss.name)
}