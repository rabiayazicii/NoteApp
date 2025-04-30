package com.example.notepadapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepadapp.data.database.NoteEntity
import com.example.notepadapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) :ViewModel() {
    private val _noteList = MutableStateFlow<List<NoteEntity>>(emptyList())
    val noteList = _noteList.asStateFlow() //flow u state e çeviriyoruz


    init{
        getAllNoteData()
    }


    fun getAllNoteData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllNotesDatabase()
                .distinctUntilChanged()
                .collect{ 
                    _noteList.value= it
                }
        }
    }

    fun addNote(noteEntity:NoteEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addNoteDatabase(noteEntity)
        }
    }

    fun searchNote(searchText:String){
        viewModelScope.launch(Dispatchers.IO){
            _noteList.value=repository.searchNoteDatabase(searchText)
        }
    }

    fun updateNote(noteEntity: NoteEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateNoteDatabase(noteEntity)
        }
    }

    fun removeAllNote(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllNotes()
        }
    }

    fun removeNote(deleteNote: NoteEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteNotes(deleteNote)
        }
    }


}