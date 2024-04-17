package com.example.noteapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.RecordCardBinding
import com.example.noteapp.entity.Note

class NoteAdapter(
    private val onClick: (Note?) -> Unit
) : RecyclerView.Adapter<NoteHolder>() {
    private var notes: List<Note> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RecordCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = notes[position]

        holder.binding.recordCardTitle.text = currentNote.noteTitle
        holder.binding.recordCardBody.text = currentNote.noteBody

        holder.binding.root.setOnClickListener {
            onClick(notes[position])
        }
    }

    fun setData(notesList: List<Note>?) {
        if (notesList != null) notes = notesList
        notifyDataSetChanged()
    }
}

class NoteHolder(val binding: RecordCardBinding) : RecyclerView.ViewHolder(binding.root)