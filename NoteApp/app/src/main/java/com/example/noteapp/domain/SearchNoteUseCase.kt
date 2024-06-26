package com.example.noteapp.domain

import com.example.noteapp.data.Repository
import javax.inject.Inject

class SearchNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(search: String?, folderId: Int) = repository.searchNote(search, folderId)
}