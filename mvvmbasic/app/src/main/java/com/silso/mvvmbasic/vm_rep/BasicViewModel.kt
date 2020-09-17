package com.silso.mvvmbasic.vm_rep

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.silso.mvvmbasic.room.Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BasicViewModel(private val reposistory: Reposistory): ViewModel(){
    var str = ObservableField<String>()
    var inputMsgs: LiveData<List<Entity>>? = reposistory.getAllMsgs()

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    fun insertMsg() {
        if (str.get() ==  null || (str.get()?:"").isEmpty()) return

        val a = str.get()!!
        viewModelScope.launch {
            reposistory.insert(Entity(str = a))
        }
    }
}
