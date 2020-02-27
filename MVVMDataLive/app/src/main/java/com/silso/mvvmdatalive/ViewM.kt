package com.silso.mvvmdatalive

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var userMutableLiveData = MutableLiveData<User>()
    private var mUser: User

    fun changeUserName() {
        // Both updates LiveData but does not update UI
        mUser.Name = "Name is Changed"

        // userMutableLiveData.getValue().setName("Updated Name");
        // This one Updates UI
        //  userMutableLiveData.setValue(userMutableLiveData.getValue());
    }

    fun changeUser() {
        mUser = User("New User Name", "myemail@mail.com")
        // Without setting new value UI is not updated and observe is not called
        userMutableLiveData.value = mUser
    }

    init {
        mUser = User("User", "asd@abc.com")
        userMutableLiveData.value = mUser
    }
}