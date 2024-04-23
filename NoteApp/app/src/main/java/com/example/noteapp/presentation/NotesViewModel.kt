package com.example.noteapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.domain.AddFolderUseCase
import com.example.noteapp.domain.AddNoteUseCase
import com.example.noteapp.domain.ChangeFolderUseCase
import com.example.noteapp.domain.ChangeNoteUseCase
import com.example.noteapp.domain.DeleteFolderUseCase
import com.example.noteapp.domain.DeleteNoteUseCase
import com.example.noteapp.domain.GetFolderNotesUseCase
import com.example.noteapp.domain.GetFoldersUseCase
import com.example.noteapp.domain.GetNotesUseCase
import com.example.noteapp.domain.SearchFoldersUseCase
import com.example.noteapp.domain.SearchNoteUseCase
import com.example.noteapp.entity.Folder
import com.example.noteapp.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val addFolderUseCase: AddFolderUseCase,
    private val changeNoteUseCase: ChangeNoteUseCase,
    private val changeFolderUseCase: ChangeFolderUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val getFoldersUseCase: GetFoldersUseCase,
    private val getFolderNotesUseCase: GetFolderNotesUseCase,
    private val searchNoteUseCase: SearchNoteUseCase,
    private val searchFoldersUseCase: SearchFoldersUseCase
) : ViewModel() {

    fun addNote(note: Note) =
        viewModelScope.launch {
            addNoteUseCase.execute(note)
        }

    fun addFolder(folder: Folder) =
        viewModelScope.launch {
            addFolderUseCase.execute(folder)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            deleteNoteUseCase.execute(note)
        }

    fun changeNote(note: Note) =
        viewModelScope.launch {
            changeNoteUseCase.execute(note)
        }

    fun changeFolder(folder: Folder) =
        viewModelScope.launch {
            changeFolderUseCase.execute(folder)
        }

    fun deleteFolder(folder: Folder) =
        viewModelScope.launch {
            deleteFolderUseCase.execute(folder)
        }

    fun notes() = getNotesUseCase.execute()

    fun folders() = getFoldersUseCase.execute()

    fun folderNotes(folderId: Int) = getFolderNotesUseCase.execute(folderId)

    fun searchNotes(search: String?, folderId: Int) = searchNoteUseCase.execute(search, folderId)

    fun searchFolders(search: String?) = searchFoldersUseCase.execute(search)
}