package com.example.noteapp.domain

import com.example.noteapp.data.Repository
import com.example.noteapp.entity.Note
import javax.inject.Inject

class SearchNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(search: String?) = repository.searchNote(search)
}