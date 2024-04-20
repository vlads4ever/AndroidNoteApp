package com.example.noteapp.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "noteTitle")
    val noteTitle: String,
    @ColumnInfo(name = "noteBody")
    val noteBody: String,
    @ColumnInfo(name = "noteData")
    val noteData: String,
    @ColumnInfo(name = "noteColor")
    val noteColor: Int
) : Parcelable