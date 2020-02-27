package com.silso.koin

class RepositoryImpl: Repository {
    override fun getMyData(): String {
        return "Hello Koin"
    }
}