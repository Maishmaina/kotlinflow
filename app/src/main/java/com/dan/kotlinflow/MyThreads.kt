package com.dan.kotlinflow

import java.lang.Exception

class MyThreads:Thread {
    var ThreadName:String=""
       constructor(ThreadName:String):super(){
           this.ThreadName=ThreadName
           println("${this.ThreadName} is starting")
       }
    override fun run() {
        //super.run()
        var count=0;
        while (count<10){
            println("${this.ThreadName} Print:$count")
            count++
            try {
Thread.sleep(1000);
            }catch (e:Exception){
                print(e.message)
            }
        }
    }
}

fun main(args:Array<String>){
var th=MyThreads("thread1")
    th.start()

    var th1=MyThreads("thread2")
    th1.start()
    th1.join()
    println("This is multi-thread")
}