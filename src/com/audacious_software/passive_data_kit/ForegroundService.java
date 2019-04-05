package com.audacious_software.passive_data_kit;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.audacious_software.pdk.passivedatakit.R;

import androidx.core.app.NotificationCompat;

public class ForegroundService extends Service {
    public static final String ACTION_START_SERVICE = "com.audacious_software.passive_data_kit.ForegroundService.ACTION_START_SERVICE";

    public static final int NOTIFICATION_ID = 778642;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onCreate();

        Notification note = ForegroundService.getForegroundNotification(this, intent);

        if (note != null) {
            this.startForeground(ForegroundService.NOTIFICATION_ID, note);
        }

        return Service.START_STICKY;
    }

    public static int getNotificationId() {
        return ForegroundService.NOTIFICATION_ID;
    }

    public static Notification getForegroundNotification(Context context, Intent intent) {
        return ForegroundService.getForegroundNotification(context, intent, R.drawable.ic_foreground_service);
    }

    public static Notification getForegroundNotification(Context context, Intent intent, int iconResource) {
        if (intent == null) {
            intent = context.getPackageManager().getLaunchIntentForPackage(context.getApplicationContext().getPackageName());
        }

        PassiveDataKit.getInstance(context).annotateForegroundIntent(intent);

        String channelId = intent.getStringExtra(PassiveDataKit.NOTIFICATION_CHANNEL_ID);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setContentTitle(context.getString(R.string.foreground_service_title));
        builder.setContentText(context.getString(R.string.foreground_service_message));
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MIN);
        builder.setSound(null);

        builder.setSmallIcon(intent.getIntExtra(PassiveDataKit.NOTIFICATION_ICON_ID, iconResource));

        if (intent.hasExtra(PassiveDataKit.NOTIFICATION_COLOR)) {
            builder.setColor(intent.getIntExtra(PassiveDataKit.NOTIFICATION_COLOR, 0));
        }

        PendingIntent pendingIntent = PassiveDataKit.getInstance(context).getForegroundPendingIntent();

        if (pendingIntent == null) {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getApplicationContext().getPackageName());

            pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
        }

        builder.setContentIntent(pendingIntent);

        return builder.build();
    }
}
