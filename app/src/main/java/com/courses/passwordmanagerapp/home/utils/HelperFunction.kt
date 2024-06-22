package com.courses.passwordmanagerapp.home.utils

import androidx.compose.ui.graphics.Color


fun getPasswordStrength(password: String): String {
    return when {
        password.length >= 12 && password.any { it.isDigit() } && password.any { it.isUpperCase() } -> "Strong"
        password.length >= 8 -> "Medium"
        else -> "Weak"
    }
}

fun generateStrongPassword(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..12)
        .map { chars.random() }
        .joinToString("")
}

fun getRandomColor(passwordStrength:String): Color {
    return when(passwordStrength){
        "Strong" -> Color.Black
        "Medium" -> Color.Blue
        else -> Color.Red
    }

}
