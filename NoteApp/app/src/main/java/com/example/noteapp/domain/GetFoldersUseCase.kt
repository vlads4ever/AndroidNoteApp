package com.example.noteapp.domain

import com.example.noteapp.data.Repository
import javax.inject.Inject

class GetFoldersUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute() = repository.getFolders()
}