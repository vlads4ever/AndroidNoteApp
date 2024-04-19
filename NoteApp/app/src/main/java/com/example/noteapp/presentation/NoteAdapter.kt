package com.example.noteapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.RecordCardBinding
import com.example.noteapp.entity.Note

class NoteAdapter(
    private val onClick: (Note?) -> Unit
) : ListAdapter<Note, NoteHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RecordCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = getItem(position)

        with(holder.binding) {
            recordCardTitle.text = currentNote.noteTitle
            recordCardBody.text = currentNote.noteBody
            recordCardDate.text = currentNote.noteData
        }

        holder.binding.root.setOnClickListener {
            currentNote?.let {
                onClick(currentNote)
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem == newItem
}

class NoteHolder(val binding: RecordCardBinding) : RecyclerView.ViewHolder(binding.root)