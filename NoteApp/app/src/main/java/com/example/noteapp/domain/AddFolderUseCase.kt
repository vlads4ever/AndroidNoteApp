package com.example.noteapp.domain

import com.example.noteapp.data.Repository
import com.example.noteapp.entity.Folder
import javax.inject.Inject

class AddFolderUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(folder: Folder) = repository.insertFolder(folder)
}