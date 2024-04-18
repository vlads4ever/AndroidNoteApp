package com.example.noteapp

import android.app.Application
import androidx.room.Room
import com.example.noteapp.data.db.NotesDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    lateinit var notesDatabase: NotesDatabase

    override fun onCreate() {
        super.onCreate()

        notesDatabase = Room.inMemoryDatabaseBuilder(
            this,
            NotesDatabase::class.java
            )
            .fallbackToDestructiveMigration()
            .build()

//        notesDatabase = Room.databaseBuilder(
//            applicationContext,
//            NotesDatabase::class.java,
//            "notesDataBase"
//        ).build()
    }
}