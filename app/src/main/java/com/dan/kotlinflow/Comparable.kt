package com.dan.kotlinflow

import java.util.*
import kotlin.collections.ArrayList

class Person(var name:String,var age:Int):Comparable<Person>{
    override fun compareTo(other: Person): Int {
        return this.age-other.age
    }
}

fun main(args:Array<String>){
    var listOfNames=ArrayList<Person>();
    listOfNames.add(Person("Daniel",12));
    listOfNames.add(Person("Maina",14))
    listOfNames.add(Person("Kabiru",9))
    println("see before i sort")
    for (name in listOfNames){
        println("Name:"+name.name)
        println("Age:"+name.age)
    }
    println("see sorted list")
    Collections.sort(listOfNames)
    for (n in listOfNames){
        println("Name:"+n.name)
        println("Age:"+n.age)
    }
}