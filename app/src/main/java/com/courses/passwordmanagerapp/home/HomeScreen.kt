package com.courses.passwordmanagerapp.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.courses.passwordmanagerapp.home.action.HomeAction
import com.courses.passwordmanagerapp.home.action.HomeState
import com.courses.passwordmanagerapp.home.utils.CommonInfoBox
import com.courses.passwordmanagerapp.home.utils.CommonTextBox
import com.courses.passwordmanagerapp.home.utils.CommonTextField
import com.courses.passwordmanagerapp.home.utils.ErrorMessage
import com.courses.passwordmanagerapp.home.utils.LoadingIndicator
import com.courses.passwordmanagerapp.home.utils.PasswordInfoBox
import com.courses.passwordmanagerapp.home.utils.PasswordTextField
import com.courses.passwordmanagerapp.home.utils.Resource
import com.courses.passwordmanagerapp.home.utils.ReusableText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(state: HomeState, onAction: (HomeAction) -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 30.dp),
                containerColor = Color.Blue,
                onClick = {
                    onAction(HomeAction.OnFloatingActionClick)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add Password",
                    tint = Color.White
                )
            }
        },
        topBar = {
            TopAppBar(title = {
                Text(text = "Password Manager")
            })
        }
    ) { paddingValues ->
        when (state.passwordList) {
            is Resource.Error -> {
                val errorMessage = state.passwordList.message
                ErrorMessage(message = errorMessage ?: "An Error Occurred")
            }

            is Resource.Loading -> LoadingIndicator()
            is Resource.Idle -> Unit
            is Resource.Success -> {
                val allPassword = state.passwordList.data.orEmpty()
                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    items(allPassword) { passwordItem ->
                        CommonTextBox(passwordEntity = passwordItem) {
                            onAction(HomeAction.OnSelectedAccount(passwordItem))
                        }
                    }
                }
            }
        }
    }


    //TODO for add and delete password state.
    if (state.addUpdatePasswordState is Resource.Loading || state.deletePassword is Resource.Loading) {
        LoadingIndicator()
    }
    if (state.addUpdatePasswordState is Resource.Error || state.deletePassword is Resource.Error) {
        val errorMessage = state.addUpdatePasswordState.message ?: "An unknown error occurred"
        ErrorMessage(message = errorMessage)
    }






    //todo for password details.
    if (state.modelSheetDialog) {
        ModalBottomSheet(onDismissRequest = {
            onAction(HomeAction.OnDismissRequest)
        }) {

            CommonTextField(state.accountName, text = "Account Name") {
                onAction(HomeAction.OnAccountNameChange(it))
            }

            CommonTextField(state.username, text = "Username") {
                onAction(HomeAction.OnUserNameChange(it))
            }

            PasswordTextField(
                state = state,
                onAction = onAction,
                inputText = state.password
            )

            Button(modifier = Modifier
                .padding(20.dp, 0.dp, 10.dp, 35.dp)
                .fillMaxWidth()
                .height(50.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ), onClick = {
                    onAction(HomeAction.OnAddAccountClick)
                }) {
                Text(
                    text = "Add New Account",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
            }
        }
    }







    if (state.passwordDetailsDialog) {
        ModalBottomSheet(onDismissRequest = { onAction(HomeAction.OnPasswordDetailsDismissClick) }) {
            val selectedAccount = state.selectedAccount

            if (selectedAccount != null) {
                Column {
                    ReusableText(modifier = Modifier.padding(horizontal = 15.dp))
                    Spacer(modifier = Modifier.padding(10.dp))
                    CommonInfoBox("Account Type", selectedAccount.accountName)
                    CommonInfoBox("Username/Email", selectedAccount.username)
                    PasswordInfoBox("Password", selectedAccount.password)
                }

                Row {
                    Button(modifier = Modifier
                        .padding(20.dp, 0.dp, 10.dp, 35.dp)
                        .height(50.dp)
                        .width(150.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        ),
                        onClick = {
                            onAction(HomeAction.OnEditPasswordClickDialog)
                        }) {
                        Text(
                            text = "Edit",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Button(modifier = Modifier
                        .padding(20.dp, 0.dp, 10.dp, 35.dp)
                        .height(50.dp)
                        .width(150.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        onClick = {
                            onAction(HomeAction.OnDeletePasswordClick(selectedAccount))
                        }) {
                        Text(
                            text = "Delete",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }





    if (state.editPasswordDialog) {
        AlertDialog(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5),
            onDismissRequest = {
                onAction(HomeAction.OnEditDismissClickDialog)
            },
            text = {
                Column {
                    ReusableText()
                    CommonTextField(
                        inputText = state.accountName,
                        text = "Account Name",
                        onChange = {
                            onAction(HomeAction.OnAccountNameChange(it))
                        }
                    )

                    CommonTextField(
                        inputText = state.username,
                        text = "Username",
                        onChange = {
                            onAction(HomeAction.OnUserNameChange(it))
                        }
                    )

                    PasswordTextField(
                        inputText = state.password,
                        state = state,
                        onAction = onAction
                    )

                }
            }, confirmButton = {
                TextButton(onClick = {
                    onAction(HomeAction.OnEditDismissClickDialog)
                }) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = {
                    onAction(HomeAction.OnEditPasswordClick)
                }) {
                    Text(text = "Confirm")
                }
            }

        )

    }

}