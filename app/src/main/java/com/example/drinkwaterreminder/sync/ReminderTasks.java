package com.example.drinkwaterreminder.sync;

import android.content.Context;

import com.example.drinkwaterreminder.utils.NotificationUtils;
import com.example.drinkwaterreminder.utils.PreferenceUtils;

public class ReminderTasks {
    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    static final String ACTION_CHARGING_REMINDER = "charging-reminder";

//    executing task based on action request
    public static void executeTask(Context context, String action ){
        if ( ACTION_INCREMENT_WATER_COUNT.equals( action ) ){
            incrementWaterCount( context );
        }else if (ACTION_DISMISS_NOTIFICATION.equals( action )){
            NotificationUtils.clearAllNotifications( context );
        } else if (ACTION_CHARGING_REMINDER.equals( action )){
            chargingReminder( context );
        }}

//   ultimately updating water count
    private static void incrementWaterCount(Context context){
        PreferenceUtils.incrementWaterCount( context );
        NotificationUtils.clearAllNotifications( context );
    }
//    charging reminder
    private static void chargingReminder(Context context){
        PreferenceUtils.incrementChargingReminderCount( context );
        NotificationUtils.reminderUserCharging( context );
    }
}
