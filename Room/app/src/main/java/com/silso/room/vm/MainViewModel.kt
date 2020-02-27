package com.silso.room.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.silso.room.InputMsgRepository
import com.silso.room.room.InputMsg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val inputMsgRepository: InputMsgRepository): ViewModel(){
    var inputMsgs: LiveData<List<InputMsg>> = inputMsgRepository.getAllMsgs()

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    fun insertMsg(inputMsg: InputMsg) {
        viewModelScope.launch { inputMsgRepository.insert(inputMsg) }
    }
}