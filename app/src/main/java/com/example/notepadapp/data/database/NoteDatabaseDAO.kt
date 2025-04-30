package com.example.notepadapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDAO {

    @Query("SELECT * FROM notes_table")
    fun getAllNotesFromDatabase(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table WHERE note_id=:selectedNoteID")
    suspend fun getNoteFromDatabase(selectedNoteID:String):NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)//Eğer aynı id'ye sahip bir kayıt zaten varsa:
   // Mevcut veriyi siler ve yerine yenisini koyar (yani güncelleme gibi çalışır).
    suspend fun insertNoteToDatabase(newNote:NoteEntity)

    @Query("SELECT * FROM notes_table WHERE note_title LIKE '%' || :noteSearchText || '%' OR note_subtitle LIKE '%' || :noteSearchText || '%'")
    suspend fun searchNoteToDatabase(noteSearchText:String):List<NoteEntity>

    @Update
    suspend fun updateNoteToDatabase(noteEntity: NoteEntity)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotesfromDatabase()

    @Delete
    suspend fun deleteNote(deleteNote: NoteEntity)
}