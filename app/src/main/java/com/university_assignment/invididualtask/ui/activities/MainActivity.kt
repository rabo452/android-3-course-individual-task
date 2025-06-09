package com.university_assignment.invididualtask.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import com.university_assignment.invididualtask.ui.theme.InvididualTaskTheme

import androidx.compose.ui.Modifier
import com.university_assignment.invididualtask.app.AppNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            InvididualTaskTheme {
                Surface {
                    Scaffold { padding ->
                        // set the paddings so the view take the space between system UI blocks
                        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
                            AppNavigator()
                        }
                    }
                }
            }
        }
    }
}