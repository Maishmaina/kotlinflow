package com.dan.kotlinflow

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.media.projection.MediaProjection
import android.widget.Toast

class DbManager {
     var dbName="myNote"
     var dbTable="Notes"
     var colId="ID"
     var colTitle="title"
     var colDesc="Description"
     var dbVersion=1
     var sqlCreateTable="CREATE TABLE IF NOT EXISTS "+dbTable+" ("+colId+" INTEGER PRIMARY KEY,"+colTitle+" TEXT, "+colDesc+" TEXT);"
     var sqlData:SQLiteDatabase?=null

    constructor(context:Context){
        var db=DatabaseHelperNotes(context)
        sqlData=db.writableDatabase
    }

    //create a helper class

    inner class DatabaseHelperNotes:SQLiteOpenHelper{
        var context: Context?=null
        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context=context
        }
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(context,"Database Created successfully",Toast.LENGTH_LONG).show()
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS "+dbTable)
        }

    }


    fun Insert(values: ContentValues):Long{
     var ID=sqlData!!.insert(dbTable,"",values)
        return ID
    }

    fun Query(projection: Array<String>,selection:String,selectionArgs:Array<String>,SortOrder:String): Cursor {
      var db=SQLiteQueryBuilder()
        db.tables=dbTable
         var mycursor=db.query(sqlData,projection,selection,selectionArgs,null,null,SortOrder)
        return mycursor
    }
    fun Delete(selection:String,selectionArgs:Array<String>):Int{
        val del=sqlData!!.delete(dbTable,selection,selectionArgs)
        return del

    }
    fun Update(values:ContentValues,selection:String,selectionArgs: Array<String>):Int{
       var upd=sqlData!!.update(dbTable,values,selection,selectionArgs)
        return upd
    }
}