package com.courses.passwordmanagerapp.home.repository

import android.annotation.SuppressLint
import android.util.Base64
import com.courses.passwordmanagerapp.home.db.PasswordDao
import com.courses.passwordmanagerapp.home.model.PasswordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class PasswordManagerRepository @Inject constructor(
    private val passwordDao: PasswordDao
) {

    fun getAllPassword(): Flow<List<PasswordEntity>> =
        passwordDao.getAllPassword().flowOn(Dispatchers.IO).conflate()


    suspend fun addPassword(passwordEntity: PasswordEntity) {
       return passwordDao.addPassword(passwordEntity)
    }

    suspend fun updatePassword(passwordEntity: PasswordEntity) {
        passwordDao.updatePassword(
            password = passwordEntity.password,
            accountName = passwordEntity.accountName,
            username = passwordEntity.username,
            id = passwordEntity.id
        )
    }

    suspend fun deletePassword(passwordEntity: PasswordEntity) = passwordDao.deletePassword(passwordEntity)


    private var key : String="mysecretkey12345"
    private var secretKeySpec = SecretKeySpec(key.toByteArray(),"AES")

    @SuppressLint("GetInstance")
    fun encrypt(string: String) : String{
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding") //Specifying which mode of AES is to be used
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec)// Specifying the mode wither encrypt or decrypt
        val encryptBytes =cipher.doFinal(string.toByteArray(Charsets.UTF_8))//Converting the string that will be encrypted to byte array
        return Base64.encodeToString(encryptBytes, Base64.DEFAULT) // returning the encrypted string
    }


    @SuppressLint("GetInstance")
    fun decrypt(string : String) : String{
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec)
        val decryptedBytes = cipher.doFinal(Base64.decode(string, Base64.DEFAULT)) // decoding the entered string
        return String(decryptedBytes,Charsets.UTF_8) // returning the decrypted string
    }

}