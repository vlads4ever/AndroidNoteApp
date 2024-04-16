package com.example.noteapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.Repository
import com.example.noteapp.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun addNote(note: Note) =
        viewModelScope.launch {
            repository.insertNote(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            repository.deleteNote(note)
        }

    fun changeNote(note: Note) =
        viewModelScope.launch {
            repository.changeNote(note)
        }

    fun getNotes() = repository.getNotes()

    fun searchNote(search: String?) = repository.searchNote(search)
}