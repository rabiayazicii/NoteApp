package com.example.notepadapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.notepadapp.ui.navigation.MainNavigation
import com.example.notepadapp.ui.theme.NotePadAppDarkTheme
import com.example.notepadapp.ui.theme.NotePadAppLightTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint //bir hilt ,dependency injection olacağını belirtmek için

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember{ mutableStateOf(false) }

            MainContent(isDarkTheme= isDarkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainNavigation(isDarkTheme=isDarkTheme) {
                        isDarkTheme=!isDarkTheme
                    }
                }
            }


        }
    }

}

@Composable
fun MainContent (isDarkTheme:Boolean,onToggleTheme:@Composable ()->Unit) {
    if(isDarkTheme) NotePadAppDarkTheme { onToggleTheme.invoke() }
    else NotePadAppLightTheme { onToggleTheme() }
}