package com.example.notepadapp.data.repository

import com.example.notepadapp.data.database.NoteDatabaseDAO
import com.example.notepadapp.data.database.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class NoteRepository @Inject constructor(
    private val noteDatabaseDao:NoteDatabaseDAO
){

    fun getAllNotesDatabase()=noteDatabaseDao.getAllNotesFromDatabase()
        .flowOn(Dispatchers.IO)
        .conflate()

    suspend fun addNoteDatabase(addNote:NoteEntity){
        noteDatabaseDao.insertNoteToDatabase(addNote)
    }

    suspend fun searchNoteDatabase(noteSearchText:String) :List<NoteEntity>{
       return noteDatabaseDao.searchNoteToDatabase(noteSearchText)
    }

    suspend fun updateNoteDatabase(noteEntity: NoteEntity){
        noteDatabaseDao.updateNoteToDatabase(noteEntity)

    }

    suspend fun deleteAllNotes(){
        noteDatabaseDao.deleteAllNotesfromDatabase()
    }

    suspend fun deleteNotes(deleteNote:NoteEntity){
        noteDatabaseDao.deleteNote(deleteNote)
    }
}