package com.silso.music

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class BindingService: Service() {
    private val mBinder = MusicPlayerBinder()
    private var mPlayer: MediaPlayer? = null
    private val NOTIFICATION_CHANNEL_ID = "10001"

    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    fun isPlaying(): Boolean? {
        return (mPlayer?.isPlaying)
    }

    fun play(int: Int) {
        mPlayer = when (int) {
            1 -> MediaPlayer.create(this, R.raw.nonon)
            2 -> MediaPlayer.create(this, R.raw.crazy)
            3 -> MediaPlayer.create(this, R.raw.maps)
            else -> MediaPlayer.create(this, R.raw.nonon)
        }

        mPlayer!!.isLooping = true
        mPlayer!!.setVolume(100f, 100f)
        mPlayer!!.start()

        NotificationSomethings()
    }

    fun NotificationSomethings() {
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground)) //BitMap 이미지 요구
            .setContentTitle("Music")
            .setContentText("음악중")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            val channelName: CharSequence  = "노티페케이션 채널"
            val description = "오레오 이상을 위한 것임"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName , importance)
            channel.description = description

            // 노티피케이션 채널을 시스템에 등록
            notificationManager.createNotificationChannel(channel)

        }else builder.setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(1234, builder.build())
    }

    fun stop() {
        if (mPlayer != null && mPlayer!!.isPlaying) {
            mPlayer!!.stop()
            mPlayer!!.release()
            mPlayer = null
        }
    }

    inner class MusicPlayerBinder : Binder() {
        fun getService(): BindingService = this@BindingService
    }
}
