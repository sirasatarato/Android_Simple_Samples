package com.silso.mvvmbasic.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.silso.mvvmbasic.R
import com.silso.mvvmbasic.databinding.ActivityMainBinding
import com.silso.mvvmbasic.room.AppDatabase
import com.silso.mvvmbasic.vm_rep.BasicViewModel
import com.silso.mvvmbasic.vm_rep.Factory
import com.silso.mvvmbasic.vm_rep.Reposistory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: BasicViewModel
    lateinit var db: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, Factory(Reposistory.getInstance(AppDatabase.getInstance(this)?.dao()))).get(BasicViewModel::class.java)
        db = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        db.apply {
            vm = viewModel
            lifecycleOwner = this@MainActivity
        }

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.inputMsgs?.observe(this, Observer {
            if (it == null || it.isEmpty()) return@Observer

            var a = ""
            for(data in it)
                a += data.str

            db.textView.text = a
        })

        db.button.setOnClickListener {
            viewModel.insertMsg()
            db.editText.setText("")
        }
    }
}
