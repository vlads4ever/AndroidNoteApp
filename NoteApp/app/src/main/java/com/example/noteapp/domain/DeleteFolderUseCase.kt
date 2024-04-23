package com.example.noteapp.domain

import com.example.noteapp.data.Repository
import com.example.noteapp.entity.Folder
import javax.inject.Inject

class DeleteFolderUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(folder: Folder) {
        repository.deleteNotesByFolderId(folder.id)
        repository.deleteFolder(folder)
    }
}