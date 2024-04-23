package com.example.noteapp.data

import android.content.Context
import com.example.noteapp.App
import com.example.noteapp.entity.Folder
import com.example.noteapp.entity.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Repository @Inject constructor(
    @ApplicationContext context: Context?
) {
    private val notesDbDao = (context?.applicationContext as App).notesDatabase.notesDao()

    suspend fun insertNote(note: Note) = notesDbDao.insertNote(note)

    suspend fun insertFolder(folder: Folder) = notesDbDao.insertFolder(folder)

    suspend fun deleteNote(note: Note) = notesDbDao.deleteNote(note)

    suspend fun deleteFolder(folder: Folder) = notesDbDao.deleteFolder(folder)

    suspend fun changeNote(note: Note) = notesDbDao.changeNote(note)

    suspend fun changeFolder(folder: Folder) = notesDbDao.changeFolder(folder)

    suspend fun deleteNotesByFolderId(folderId: Int) = notesDbDao.deleteNotesByFolderId(folderId)

    fun getNotes() = notesDbDao.getNotes()

    fun getFolders() = notesDbDao.getFolders()

    fun getFolderNotes(folderId: Int) = notesDbDao.getFolderNotes(folderId)

    fun searchNote(search: String?, folderId: Int) = notesDbDao.searchNote(search, folderId)

    fun searchFolder(search: String?) = notesDbDao.searchFolder(search)
}
