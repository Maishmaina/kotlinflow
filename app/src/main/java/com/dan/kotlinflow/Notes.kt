package com.dan.kotlinflow

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.ticket_note.view.*

class Notes : AppCompatActivity() {
    var listNote=ArrayList<NoteModel>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
//loasd data from database sqlite
    LoadQuery("%")


    }
@SuppressLint("Range")
fun LoadQuery(title:String){
    var dbManager=DbManager(this)
    var projection= arrayOf("ID","title","Description")
    var selectionArgs= arrayOf(title)
    var cursor=dbManager.Query(projection,"title like ?",selectionArgs,"title" )
    if (cursor.moveToFirst()){
        listNote.clear()
        do {
            var ID= cursor.getInt(cursor.getColumnIndex("ID"))
            var Title= cursor.getString(cursor.getColumnIndex("title"))
            var Description= cursor.getString(cursor.getColumnIndex("Description"))

            listNote.add(NoteModel(ID,Title,Description))
        } while (cursor.moveToNext())

    }
    var myNotesAdapter =MyNotesAdapter(listNote)
    lvNote.adapter=myNotesAdapter
}
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        var sv=menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        var sm=getSystemService(Context.SEARCH_SERVICE)as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
//                Toast.makeText(applicationContext,query,Toast.LENGTH_LONG).show()
                LoadQuery("%"+query+"%")
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId){
             R.id.addNote->{
                 var int=Intent(this,AddnewNote::class.java)
                 startActivity(int)
             }
         }
        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdapter:BaseAdapter{
        var listNoteAdapter=ArrayList<NoteModel>()
        constructor(listNotesAdapter:ArrayList<NoteModel>):super(){
          this.listNoteAdapter=listNotesAdapter
        }
        override fun getCount(): Int {
           return listNoteAdapter.size
        }

        override fun getItem(position: Int): Any {
            return listNoteAdapter[position]
        }

        override fun getItemId(position: Int): Long {
           return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView=layoutInflater.inflate(R.layout.ticket_note,null);
            var myNote=listNoteAdapter[position]
            myView.title.text=myNote.noteName
            myView.content.text=myNote.noteDesc

            return myView
        }

    }
}