package com.example.android.background.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.example.android.background.MainActivity;
import com.example.android.background.R;

/**
 * Utility class for creating hydration notifications
 */
public class NotificationUtils {
  private static final int WATER_REMINDER_PENDING_INTENT_ID = 0;
  private static final String WATER_REMINDER_CHANNEL_ID = "WATER_REMINDER_CHANNEL_ID";

  public static void remindUserBecauseCharging(Context context) {
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    NotificationChannel mChannel = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      mChannel = new NotificationChannel(WATER_REMINDER_CHANNEL_ID,
          context.getString(R.string.main_notification_channel_name),
          NotificationManager.IMPORTANCE_HIGH);

      notificationManager.createNotificationChannel(mChannel);
    }

    NotificationCompat.Builder notificationBuilder =
        new NotificationCompat.Builder(context, WATER_REMINDER_CHANNEL_ID)
            .setColor(R.drawable.ic_drink_notification)
            .setLargeIcon(largeIcon(context))
            .setSmallIcon(R.drawable.ic_cancel_black_24px)
            .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
            .setContentText(context.getString(R.string.charging_reminder_notification_body))
            .setStyle(new NotificationCompat.BigTextStyle().bigText(
                context.getString(R.string.charging_reminder_notification_body)))
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setContentIntent(contentIntent(context))
            .setAutoCancel(true);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
        && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
    }

    notificationManager.notify(WATER_REMINDER_PENDING_INTENT_ID, notificationBuilder.build());
  }

  public static PendingIntent contentIntent(Context context) {
    Intent startActivityIntent = new Intent(context, MainActivity.class);
    return PendingIntent.getActivity(context, WATER_REMINDER_PENDING_INTENT_ID, startActivityIntent,
        PendingIntent.FLAG_UPDATE_CURRENT);
  }

  private static Bitmap largeIcon(Context context) {
    Resources res = context.getResources();

    Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
    return largeIcon;
  }
}
