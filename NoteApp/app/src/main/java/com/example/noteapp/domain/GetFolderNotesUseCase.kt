package com.example.noteapp.domain

import com.example.noteapp.data.Repository
import javax.inject.Inject

class GetFolderNotesUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(folderId: Int) = repository.getFolderNotes(folderId)
}