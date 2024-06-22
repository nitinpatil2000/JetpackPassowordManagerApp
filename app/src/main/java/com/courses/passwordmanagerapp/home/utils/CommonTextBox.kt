package com.courses.passwordmanagerapp.home.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.courses.passwordmanagerapp.home.model.PasswordEntity

@Composable
fun CommonTextBox(passwordEntity: PasswordEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClick()
            }
            .shadow(
                elevation = 8.dp,
                ambientColor = Color.Gray,
                shape = RoundedCornerShape(30),
                spotColor = Color.Blue
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp, 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(20.dp)
                    .weight(1f)
            ) {
                Text(
                    text = passwordEntity.accountName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.padding(8.dp))


                val textLength = buildString {
                    repeat(passwordEntity.password.length) {
                        append("*")
                    }
                }

                Text(
                    text = textLength,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    color = Color.Gray
                )
            }

            Box(contentAlignment = Alignment.Center) {
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "open",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }
    }

}