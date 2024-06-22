package com.courses.passwordmanagerapp.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.courses.passwordmanagerapp.home.action.HomeAction
import com.courses.passwordmanagerapp.home.action.HomeState
import com.courses.passwordmanagerapp.home.model.PasswordEntity
import com.courses.passwordmanagerapp.home.repository.PasswordManagerRepository
import com.courses.passwordmanagerapp.home.utils.Resource
import com.courses.passwordmanagerapp.home.utils.generateStrongPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val repository: PasswordManagerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadPasswords()
    }

    private fun loadPasswords() {
        viewModelScope.launch {
            _state.update {
                it.copy(passwordList = Resource.Loading())
            }

            repository.getAllPassword().distinctUntilChanged()
                .collect { listOfPasswords ->
                    val decryptPasswords = listOfPasswords.map { passwordEntity ->
                        passwordEntity.copy(
                            accountName = repository.decrypt(passwordEntity.accountName),
                            username = repository.decrypt(passwordEntity.username),
                            password = repository.decrypt(passwordEntity.password),
                        )

                    }

                    _state.update {
                        if (listOfPasswords.isEmpty()) {
                            it.copy(passwordList = Resource.Error("Empty List"))
                        } else {
                            it.copy(passwordList = Resource.Success(decryptPasswords))
                        }
                    }
                }
        }
    }


    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnAccountNameChange -> {
                _state.update {
                    it.copy(accountName = action.accountName)
                }
            }

            is HomeAction.OnPasswordChange -> {
                _state.update {
                    it.copy(password = action.password)
                }
            }

            is HomeAction.OnUserNameChange -> {
                _state.update {
                    it.copy(username = action.username)
                }
            }

            HomeAction.OnAddAccountClick -> {
                isAdd(isAdd = true)
            }

            HomeAction.OnFloatingActionClick -> {
                _state.update {
                    it.copy(modelSheetDialog = true)
                }
            }

            HomeAction.OnDismissRequest -> {
                _state.update {
                    it.copy(modelSheetDialog = false)
                }
            }

            HomeAction.OnGeneratePasswordClick -> {
                val newPassword = generateStrongPassword()
                _state.update {
                    it.copy(password = newPassword)
                }
            }

            HomeAction.OnPasswordToggleClick -> {
                _state.update {
                    it.copy(passwordVisibility = !it.passwordVisibility)
                }

            }

            is HomeAction.OnSelectedAccount -> {
                _state.update {
                    it.copy(selectedAccount = action.passwordEntity, passwordDetailsDialog = true)
                }
            }

            HomeAction.OnPasswordDetailsDismissClick -> {
                _state.update {
                    it.copy(passwordDetailsDialog = false)
                }
            }


            HomeAction.OnEditPasswordClickDialog -> {
                val selectedAccount = _state.value.selectedAccount
                _state.update {
                    it.copy(
                        editPasswordDialog = true,
                        accountName = selectedAccount?.accountName ?: "",
                        username = selectedAccount?.username ?: "",
                        password = selectedAccount?.password ?: "",
                    )
                }

            }

            HomeAction.OnEditDismissClickDialog -> {
                _state.update {
                    it.copy(editPasswordDialog = false)
                }
            }

            HomeAction.OnEditPasswordClick -> {
                isAdd(isAdd = false)
            }

            is HomeAction.OnDeletePasswordClick -> {
                deletePassword(action.passwordEntity)
            }
        }
    }


    private fun isAdd(isAdd: Boolean) {
        val currentState = _state.value

        val passwordEntity = if (isAdd) {
            PasswordEntity(
                accountName = repository.encrypt(currentState.accountName),
                username = repository.encrypt(currentState.username),
                password = repository.encrypt(currentState.password)
            )
        } else {
            currentState.selectedAccount?.copy(
                accountName = repository.encrypt(currentState.accountName),
                username = repository.encrypt(currentState.username),
                password = repository.encrypt(currentState.password)
            )
        }

        if (currentState.username.isEmpty() || currentState.accountName.isEmpty() || currentState.password.isEmpty()) {
            _state.update { it.copy(addUpdatePasswordState = Resource.Error("Please fill all fields")) }

        } else {
            if (isAdd) {
                _state.update { it.copy(modelSheetDialog = false) }
            } else {
                _state.update { it.copy(editPasswordDialog = false, passwordDetailsDialog = false) }
            }

            viewModelScope.launch {
                _state.update { it.copy(addUpdatePasswordState = Resource.Loading()) }
                delay(2000)
                try {
                    if (isAdd) {
                        repository.addPassword(passwordEntity!!)
                    } else {
                        repository.updatePassword(passwordEntity!!)
                    }
                    _state.update { it.copy(addUpdatePasswordState = Resource.Success(passwordEntity)) }

                } catch (e: Exception) {
                    _state.update { it.copy(addUpdatePasswordState = Resource.Error(e.message.toString())) }
                }
            }
        }
    }

    private fun deletePassword(passwordEntity: PasswordEntity) {
        viewModelScope.launch {
            _state.update {
                it.copy(deletePassword = Resource.Loading())
            }
            delay(2000)
            try {
                repository.deletePassword(passwordEntity)
                _state.update {
                    it.copy(deletePassword = Resource.Success(passwordEntity))
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(deletePassword = Resource.Error(e.message.toString()))
                }
            }
        }
        _state.update {
            it.copy(passwordDetailsDialog = false)
        }
    }
}