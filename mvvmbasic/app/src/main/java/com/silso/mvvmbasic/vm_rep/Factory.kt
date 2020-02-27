package com.silso.mvvmbasic.vm_rep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class Factory(reposistory: Reposistory): ViewModelProvider.Factory {
    private var userRepository: Reposistory = reposistory

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BasicViewModel(userRepository) as T
    }
}