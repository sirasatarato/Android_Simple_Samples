package com.silso.room.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.silso.room.InputMsgRepository

class MailViewModelFactory(repository: InputMsgRepository) : ViewModelProvider.Factory {
    private var userRepository: InputMsgRepository = repository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(userRepository) as T
    }
}
