package com.example.drinkwaterreminder.utils;

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

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Action;
import androidx.core.content.ContextCompat;

import com.example.drinkwaterreminder.MainActivity;
import com.example.drinkwaterreminder.R;
import com.example.drinkwaterreminder.sync.ReminderTasks;
import com.example.drinkwaterreminder.sync.WaterReminderIntentService;

import static com.example.drinkwaterreminder.R.string.ignore_reminder_action_title;

public class NotificationUtils {
    private static final int WATER_REMINDER_NOTIFICATION_ID  = 1987;
    private static final int WATER_REMINDER_PENDING_INTENT_ID = 8719;
    private static final String WATER_REMINDER_NOTIFICATION_CHANNEl_ID = "reminder-notification-channel";
    private static final int ACTION_DRINK_PENDING_INTENT_ID = 11;
    private static final int ACTION_IGNORE_PENDING_INTEN_ID = 141;
//    cancel all notifications
    public static void clearAllNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService( Context.NOTIFICATION_SERVICE );
        notificationManager.cancelAll();
    }

    public static void reminderUserCharging(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel mChannel = new NotificationChannel(
                    WATER_REMINDER_NOTIFICATION_CHANNEl_ID,
                    context.getString( R.string.main_notification_channel_name ),
                    NotificationManager.IMPORTANCE_HIGH );
            notificationManager.createNotificationChannel( mChannel );
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( context, WATER_REMINDER_NOTIFICATION_CHANNEl_ID )
                .setColor( ContextCompat.getColor( context, R.color.colorPrimary ) )
                .setSmallIcon( R.drawable.ic_drink_notification)
                .setLargeIcon( largeIcon( context ) )
                .setContentTitle( context.getString( R.string.charging_reminder_title ) )
                .setContentText( context.getText( R.string.charging_reminder_notification_body ) )
                .setStyle( new NotificationCompat.BigTextStyle(  ).bigText(
                        context.getString( R.string.charging_reminder_notification_body ) ))
                .setDefaults( Notification.DEFAULT_VIBRATE )
                .setContentIntent( contentIntent( context ) )
                .addAction( drinkWaterAction( context ) )
                .addAction( ignoreActionReminder( context ) )
                .setAutoCancel( true );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                 && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority( NotificationCompat.PRIORITY_HIGH );
        }
        notificationManager.notify( WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build() );
    }
//    ignoring reminder, user choice
    private static Action ignoreActionReminder(Context context){
        Intent ignoreReminderIntent = new Intent( context, WaterReminderIntentService.class );
        ignoreReminderIntent.setAction( ReminderTasks.ACTION_DISMISS_NOTIFICATION );
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTEN_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Action ignoreReminderAction = new Action( R.drawable.ic_cancel,
                R.string.ignore_reminder_action_title+"",
                ignoreReminderPendingIntent  );
        return ignoreReminderAction;
    }
//    when user choice i did it action
    private static Action drinkWaterAction(Context context){
        Intent incrementWaterCountIntent = new Intent( context, WaterReminderIntentService.class );
        incrementWaterCountIntent.setAction( ReminderTasks.ACTION_INCREMENT_WATER_COUNT );
        PendingIntent incrementWaterCountPendingIntent = PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_INTENT_ID,
                incrementWaterCountIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Action drinkWaterAction = new Action( R.drawable.notification_image,
                R.string.i_did_it+"",
                incrementWaterCountPendingIntent );
        return drinkWaterAction;

    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private static Bitmap largeIcon(Context context){
        Resources resources = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource( resources, R.drawable.notification_image );
        return largeIcon;
    }

}

