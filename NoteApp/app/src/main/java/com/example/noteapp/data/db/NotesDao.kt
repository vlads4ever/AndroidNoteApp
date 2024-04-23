package com.example.noteapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.entity.Folder
import com.example.noteapp.entity.Note

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: Folder)

    @Update
    suspend fun changeNote(note: Note)

    @Update
    suspend fun changeFolder(folder: Folder)

    @Delete
    suspend fun deleteNote(note: Note)

    @Delete
    suspend fun deleteFolder(folder: Folder)

    @Query("DELETE FROM notes WHERE folderid=:folderId")
    suspend fun deleteNotesByFolderId(folderId: Int)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM folders ORDER BY id DESC")
    fun getFolders(): LiveData<List<Folder>>

    @Query("SELECT * FROM notes WHERE folderId=:searchFolderId")
    fun getFolderNotes(searchFolderId: Int): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE (folderId=:searchFolderId AND noteTitle LIKE :search) OR " +
            "(folderId=:searchFolderId AND noteBody LIKE :search)")
    fun searchNote(search: String?, searchFolderId: Int): LiveData<List<Note>>

    @Query("SELECT * FROM folders WHERE folderTitle LIKE :search")
    fun searchFolder(search: String?): LiveData<List<Folder>>
}
