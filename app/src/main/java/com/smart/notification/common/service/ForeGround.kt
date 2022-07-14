package com.smart.notification.common.service

import android.R
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.smart.notification.mainModule.view.MainActivity


/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.service
 * Update by israel on Tuesday, 7/19/2022 10:17 AM
 * GitHub: https://github.com/FubbledIsrael
 */

/*class ForeGround: Service(){
    private val TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE"
    val ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE"
    val ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE"
    val ACTION_PAUSE = "ACTION_PAUSE"
    val ACTION_PLAY = "ACTION_PLAY"

    override fun onBind(p0: Intent?): IBinder? {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG_FOREGROUND_SERVICE, "My foreground service onCreate().")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.action
            when (action) {
                ACTION_START_FOREGROUND_SERVICE -> {
                    startForegroundService()
                    Toast.makeText(
                        applicationContext,
                        "Foreground service is started.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                ACTION_STOP_FOREGROUND_SERVICE -> {
                    stopForegroundService()
                    Toast.makeText(
                        applicationContext,
                        "Foreground service is stopped.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                ACTION_PLAY -> Toast.makeText(
                    applicationContext,
                    "You click Play button.",
                    Toast.LENGTH_LONG
                ).show()
                ACTION_PAUSE -> Toast.makeText(
                    applicationContext,
                    "You click Pause button.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /* Used to build and start foreground service. */
    private fun startForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.")

        // Create notification default intent.
        val intent = Intent(applicationContext, MainActivity::class.java)

//        Intent intent = new Intent();
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

//        NotificationCompat.Builder mBuilder =
//                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_launcher_background)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!");

        // Create notification builder.
        val builder =
            NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Music player implemented by foreground service.")
                .setContentText("Android foreground service is a android service which can run in foreground always, it can be controlled by user via notification.") as NotificationCompat.Builder

        // Make notification show big text.
//        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
//        bigTextStyle.setBigContentTitle("Music player implemented by foreground service.");
//        bigTextStyle.bigText("Android foreground service is a android service which can run in foreground always, it can be controlled by user via notification.");
//        // Set big text style.
//        builder.setStyle(bigTextStyle);
        builder.setWhen(System.currentTimeMillis())
        builder.setSmallIcon(R.mipmap.ic_launcher)
        val largeIconBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_menu_camera)
        builder.setLargeIcon(largeIconBitmap)
        // Make the notification max priority.
        builder.priority = Notification.PRIORITY_MAX
        // Make head-up notification.
        builder.setFullScreenIntent(pendingIntent, true)

        // Add Play button intent in notification.
        val playIntent = Intent(this, MyForeGroundService::class.java)
        playIntent.action = ACTION_PLAY
        val pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0)
        val playAction =
            NotificationCompat.Action(R.drawable.ic_media_play, "Play", pendingPlayIntent)
        builder.addAction(playAction)

        // Add Pause button intent in notification.
        val pauseIntent = Intent(this, MyForeGroundService::class.java)
        pauseIntent.action = ACTION_PAUSE
        val pendingPrevIntent = PendingIntent.getService(this, 0, pauseIntent, 0)
        val prevAction =
            NotificationCompat.Action(R.drawable.ic_media_pause, "Pause", pendingPrevIntent)
        builder.addAction(prevAction)

        // Build the notification.
        val notification: Notification = builder.build()

        // Start foreground service.
        startForeground(1, notification)
    }

    private fun stopForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.")

        // Stop foreground service and remove the notification.
        stopForeground(true)

        // Stop the foreground service.
        stopSelf()
    }
}*/