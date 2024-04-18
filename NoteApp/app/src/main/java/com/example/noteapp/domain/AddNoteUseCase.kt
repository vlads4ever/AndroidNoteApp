package com.example.noteapp.domain

import com.example.noteapp.data.Repository
import com.example.noteapp.entity.Note
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(note: Note) = repository.insertNote(note)
}