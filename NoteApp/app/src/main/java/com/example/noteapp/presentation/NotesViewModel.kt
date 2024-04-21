package com.example.noteapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.domain.AddNoteUseCase
import com.example.noteapp.domain.ChangeNoteUseCase
import com.example.noteapp.domain.DeleteNoteUseCase
import com.example.noteapp.domain.GetNotesUseCase
import com.example.noteapp.domain.SearchNoteUseCase
import com.example.noteapp.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val changeNoteUseCase: ChangeNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val searchNoteUseCase: SearchNoteUseCase
) : ViewModel() {

    fun addNote(note: Note) =
        viewModelScope.launch {
            addNoteUseCase.execute(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            deleteNoteUseCase.execute(note)
        }

    fun changeNote(note: Note) =
        viewModelScope.launch {
            changeNoteUseCase.execute(note)
        }

    fun notes() = getNotesUseCase.execute()

    fun searchNotes(search: String?) = searchNoteUseCase.execute(search)
}