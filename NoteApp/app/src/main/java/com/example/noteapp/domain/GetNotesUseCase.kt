package com.example.noteapp.domain

import com.example.noteapp.data.Repository
import com.example.noteapp.entity.Note
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute() = repository.getNotes()
}