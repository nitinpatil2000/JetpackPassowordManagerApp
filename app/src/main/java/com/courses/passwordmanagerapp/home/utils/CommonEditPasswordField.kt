package com.courses.passwordmanagerapp.home.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CommonInfoBox(title:String, content:String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.Gray
            )

            Text(
                text = content,
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun PasswordInfoBox(title:String, content: String) {
    var showPassword by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
        ) {
        Column(modifier = Modifier.padding(12.dp).weight(1f)){
            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.Gray
            )

            if(showPassword){
                Text(
                    text = content,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }else{
                val textLength = "*".repeat(content.length)
                Text(
                    text = textLength,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if(title == "Password"){
            IconButton(onClick = {
                showPassword = !showPassword
            }, modifier = Modifier.padding(end = 15.dp)) {
                if(showPassword){
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "show",
                        tint = Color.DarkGray
                    )
                }else{
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "show",
                        tint = Color.DarkGray
                    )
                }
            }
        }
    }
}