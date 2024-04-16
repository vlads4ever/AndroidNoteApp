package com.example.noteapp.data

import android.content.Context
import com.example.noteapp.App
import com.example.noteapp.entity.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Repository @Inject constructor(
    @ApplicationContext context: Context?
) {
    private val notesDbDao = (context?.applicationContext as App).notesDatabase.notesDao()

    suspend fun insertNote(note: Note) = notesDbDao.insertNote(note)

    suspend fun deleteNote(note: Note) = notesDbDao.deleteNote(note)

    suspend fun changeNote(note: Note) = notesDbDao.changeNote(note)

    fun getNotes() = notesDbDao.getNotes()

    fun searchNote(search: String?) = notesDbDao.searchNote(search)

}
