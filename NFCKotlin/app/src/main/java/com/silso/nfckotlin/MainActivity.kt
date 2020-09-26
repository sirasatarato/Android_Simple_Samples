package com.silso.nfckotlin

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        turnOnNfcBeam()
    }

    private fun turnOnNfcBeam() {
        val mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (!mNfcAdapter.isEnabled) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show()
            return
        }

        mNfcAdapter.setNdefPushMessageCallback({
            createMessage()
        }, this)
    }

    private fun createMessage(): NdefMessage? {
        val text = """
            Hello there from another device!
            
            Beam Time: ${System.currentTimeMillis()}
            """.trimIndent()
        return NdefMessage(
            arrayOf(
                NdefRecord.createMime(
                    "application/com.bluefletch.nfcdemo.mimetype",
                    text.toByteArray()
                )
            )
        )
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                messages[0].records[0].payload

                val str: String = java.lang.String.format(
                    Locale.getDefault(),
                    "Message entries=%d. Base message is %s",
                    rawMessages.size,
                    String(messages[0].records[0].payload)
                )

                textView.text = str
            }
        }
    }
}