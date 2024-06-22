package com.courses.passwordmanagerapp.home.action

import com.courses.passwordmanagerapp.home.model.PasswordEntity
import com.courses.passwordmanagerapp.home.utils.Resource

data class HomeState (
    val accountName:String = "",
    val username:String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val modelSheetDialog: Boolean= false,
    val passwordDetailsDialog: Boolean = false,
    val editPasswordDialog: Boolean = false,
    val addUpdatePasswordState: Resource<PasswordEntity> = Resource.Idle(),
    val passwordList: Resource<List<PasswordEntity>> = Resource.Idle(),
    val deletePassword: Resource<PasswordEntity> = Resource.Idle(),
    val selectedAccount: PasswordEntity? = null        //for showing and edit the details.
)