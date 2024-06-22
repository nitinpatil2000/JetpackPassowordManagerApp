package com.courses.passwordmanagerapp.home.action

import com.courses.passwordmanagerapp.home.model.PasswordEntity

sealed interface HomeAction {
    data class OnAccountNameChange(val accountName: String) : HomeAction
    data class OnUserNameChange(val username: String) : HomeAction
    data class OnPasswordChange(val password: String) : HomeAction
    data class OnSelectedAccount(val passwordEntity: PasswordEntity): HomeAction

    data object OnFloatingActionClick : HomeAction
    data object OnDismissRequest : HomeAction
    data object OnAddAccountClick : HomeAction
    data object OnPasswordToggleClick: HomeAction
    data object OnGeneratePasswordClick: HomeAction
    data object OnPasswordDetailsDismissClick: HomeAction
    data object OnEditPasswordClickDialog: HomeAction
    data object OnEditDismissClickDialog: HomeAction
    data object OnEditPasswordClick: HomeAction
    data class OnDeletePasswordClick(val passwordEntity: PasswordEntity): HomeAction
}



