package com.example.notepadapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notepadapp.ui.component.NoteListComponent
import com.example.notepadapp.ui.navigation.AppScreen
import com.example.notepadapp.ui.viewmodel.NoteViewModel
import com.example.notepadapp.ui.widget.DeleteAlertDialog

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun NoteListScreen(
    viewModel: NoteViewModel,
    navController: NavController,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit


){
    val noteList = viewModel.noteList.collectAsState().value

    //DropDownMenu
    val showmenu=remember{ mutableStateOf(false) }

    //search
    val searchText = remember{ mutableStateOf("") }
    val isSearchState= remember { mutableStateOf(false) }


    //AlertDialog
    val showDeleteAllAlertDialog=remember{ mutableStateOf(false) }

    if(showDeleteAllAlertDialog.value){
        DeleteAlertDialog(
            onDismissRequest = {showDeleteAllAlertDialog.value=false},
            onConfirmation = {viewModel.removeAllNote()},
            dialogTitle = "Sil",
            dialogText = "Tüm notları silmek istediğinizden emin misiniz?"

        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if(isSearchState.value){
                        TextField(
                            modifier = Modifier.padding().fillMaxWidth(),
                            value = searchText.value, onValueChange = {
                            searchText.value=it
                            viewModel.searchNote(it)
                        },
                            textStyle = TextStyle(
                                fontSize = 17.sp
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            label = {
                                Text(text = "Search")
                            },
                            maxLines = 1,
                            singleLine = true

                        )
                    }
                    else{
                        Text(text = AppScreen.NOTE_LIST.topBarName,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary)
                    }

                },
                actions = {//actions toppapp barın en sağında yer alır.navigationıcon ise en solunda
                    if(isSearchState.value){
                        IconButton(onClick = {
                            isSearchState.value= false
                            viewModel.getAllNoteData()
                            searchText.value=""
                        }) {
                            Icon(imageVector = Icons.Default .Close, contentDescription = "Close Button",
                                tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                    else{
                        IconButton(onClick ={
                            isSearchState.value=true

                        }) {
                            Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search Button", tint = MaterialTheme.colorScheme.onPrimary)
                        }

                        IconButton(onClick = {
                            showmenu.value=true
                        }){

                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Vertical Icon", tint = MaterialTheme.colorScheme.onPrimary)
                        }

                    }

                    DropdownMenu(expanded = showmenu.value, onDismissRequest = {
                        showmenu.value=false
                    }) {
                        DropdownMenuItem(text = {
                            Text(text = "Tümünü Sil", fontSize = 15.sp) },
                           onClick = {
                               if(noteList.isNotEmpty()) showDeleteAllAlertDialog.value=true
                           } )

                        DropdownMenuItem(text = {
                            Text(text="Tema Değiştir", fontSize = 15.sp)
                        },
                            onClick = {onThemeChange.invoke(isDarkTheme)})
                    }
                    
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick ={  navController.navigate(AppScreen.NOTE_ADD.name)},
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                text = {
                    Text(text = "Add Note")
                },
                icon = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note Icon")
                }

            )
        }
    ) {innerPadding ->

        Column(modifier = Modifier.fillMaxWidth()
            .padding(innerPadding)){
            NoteListComponent(noteList,viewModel)

        }


    }

}
