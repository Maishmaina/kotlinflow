package com.dan.kotlinflow.stageOne
import java.io.FileReader
import java.io.FileWriter

class ReadAndWriteFile {
}
fun main(args:Array<String>){

    println("Enter 1 for read\n 2 for write\n")
    var type:Int= readLine()!!.toInt()

    when(type){
        1->{
            ReadFile()
        }
        2->{
          println("Write to file")
          var str:String= readLine().toString()
            WriteToFile(str);
        }
    }

}

fun WriteToFile(str:String){
try {
  var fo=FileWriter("C:\\Users\\hp\\AndroidStudioProjects\\kotlinFlow\\app\\src\\main\\java\\com\\dan\\kotlinflow\\solution.txt",true);
    fo.write(str+ "\n")
    fo.close()

}catch (e:Exception){
    println(e.message)
}
}

fun ReadFile(){
    try {
var fin= FileReader("C:\\Users\\hp\\AndroidStudioProjects\\kotlinFlow\\app\\src\\main\\java\\com\\dan\\kotlinflow\\solution.txt");
        var c:Int?
        do {
            c=fin.read()
            print(c.toChar());
        }while (c!=-1)
    }catch (e:Exception){
        println(e.message)
    }
}


