package com.silso.koin

class MyPresenter(private val repository: Repository) {
    fun sayHello() = "${repository.getMyData()} from $this"
}