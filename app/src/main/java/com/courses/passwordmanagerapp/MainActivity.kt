package com.courses.passwordmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.courses.passwordmanagerapp.home.HomeScreen
import com.courses.passwordmanagerapp.home.viewmodel.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<PasswordViewModel>()
            val state = viewModel.state.collectAsState()

           HomeScreen(state = state.value, onAction = viewModel::onAction)
        }
    }
}
