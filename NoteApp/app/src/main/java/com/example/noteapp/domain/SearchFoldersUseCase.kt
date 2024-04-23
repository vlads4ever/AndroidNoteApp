package com.example.noteapp.domain

import com.example.noteapp.data.Repository
import javax.inject.Inject

class SearchFoldersUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(search: String?) = repository.searchFolder(search)
}