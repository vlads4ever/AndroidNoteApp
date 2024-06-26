package com.example.noteapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.entity.Folder
import com.example.noteapp.entity.Note

@Database(
    entities = [
        Note::class,
        Folder::class
    ], version = 1
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}