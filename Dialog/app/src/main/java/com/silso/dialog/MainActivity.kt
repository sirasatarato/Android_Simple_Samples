package com.silso.dialog

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_hell_btn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)

            builder.setTitle("버튼 추가 예제").setMessage("선택하세요.")

            builder.setPositiveButton("OK"
            ) { _, _ ->
                toast("OK Click")
            }

            builder.setNegativeButton("Cancel"
            ) { _, _ ->
                toast("Cancel Click")
            }

            builder.setNeutralButton("Neutral"
            ) { _, _ ->
                toast("Neutral Click")
            }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }

        main_anko_btn.setOnClickListener {
            alert(title = "확인해주세요", message = "팝업이 생성되었습니다.") {
                positiveButton("확인") {
                    toast("OK Click")
                }
                negativeButton("취소") {
                    toast("Cancel Click")
                }
                neutralPressed("중립") {
                    toast("Neutral Click")
                }
            }.show()
        }
    }
}
