package com.dan.kotlinflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.ticket_note.view.*

class Notes : AppCompatActivity() {
    var listNote=ArrayList<NoteModel>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)


        //item notes
        listNote.add(NoteModel(1,"Awake","A computer is a digital electronic machine that can be programmed to carry out sequences of arithmetic or logical operations (computation) automatically. Modern computers can perform generic sets of operations known as programs. These programs enable computers to perform a wide range of tasks"))
        listNote.add(NoteModel(2,"Code","A computer is a digital electronic machine that can be programmed to carry out sequences of arithmetic or logical operations (computation) automatically. Modern computers can perform generic sets of operations known as programs. These programs enable computers to perform a wide range of tasks"))
        listNote.add(NoteModel(3,"Money","A computer is a digital electronic machine that can be programmed to carry out sequences of arithmetic or logical operations (computation) automatically. Modern computers can perform generic sets of operations known as programs. These programs enable computers to perform a wide range of tasks"))

        var myNotesAdapter =MyNotesAdapter(listNote)
        Log.d("response",listNote.toString())
        lvNote.adapter=myNotesAdapter
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