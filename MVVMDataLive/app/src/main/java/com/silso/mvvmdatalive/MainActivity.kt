package com.silso.mvvmdatalive

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.silso.mvvmdatalive.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var model: UserViewModel
    lateinit var db: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Get ViewModel.
        model = ViewModelProviders.of(this).get(UserViewModel::class.java)
        db.viewModel = model
        db.lifecycleOwner = this

        button.setOnClickListener {
            Toast.makeText(this, model.userMutableLiveData.value.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
