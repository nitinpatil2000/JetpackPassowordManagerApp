package com.courses.passwordmanagerapp.home.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.courses.passwordmanagerapp.home.action.HomeAction
import com.courses.passwordmanagerapp.home.action.HomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(inputText: String, text: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = inputText,
        onValueChange = { onChange(it) },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        label = { Text(text = text) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Blue,
            containerColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.DarkGray,
        ),

        shape = RoundedCornerShape(15.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    inputText: String
) {

    val passwordStrength = getPasswordStrength(state.password)
    val passwordStrengthColor = getRandomColor(passwordStrength)

    OutlinedTextField(
        value = inputText,
        onValueChange = {
            onAction(HomeAction.OnPasswordChange(it))
        },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 0.dp)
            .fillMaxWidth(),
        label = { Text(text = "password") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Blue,
            containerColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.DarkGray,
        ),
        shape = RoundedCornerShape(15.dp),
        visualTransformation = if (state.passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            IconButton(onClick = {
                onAction(HomeAction.OnPasswordToggleClick)
            }) {
                Icon(
                    imageVector = if (state.passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "visibility"
                )
            }
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 0.dp, 15.dp, 15.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Text(
            text = "Password Strength: $passwordStrength",
            color = passwordStrengthColor,
            fontSize = 13.sp,
            textAlign = TextAlign.Start
        )

        Text(
            text = "Generate Password",
            color = Color.Blue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier.clickable {
                onAction(HomeAction.OnGeneratePasswordClick)
            }
        )
    }


}


