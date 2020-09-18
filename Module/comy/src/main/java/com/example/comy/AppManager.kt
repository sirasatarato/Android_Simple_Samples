package com.example.comy

class AppManager {
    companion object {
        var appType: AppType? = null
        var themeColor = 0
    }

    enum class AppType {
        RedApp, BlueApp
    }
}