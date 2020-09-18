package com.example.firs

import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.comy.AppManager
import com.example.comy.BaseMainActivity

class MainActivity : BaseMainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppManager.appType = AppManager.AppType.RedApp
        AppManager.themeColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val button: Button = findViewById(R.id.button)
        button.text = "fds"
    }
}