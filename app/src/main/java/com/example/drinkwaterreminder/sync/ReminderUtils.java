package com.example.drinkwaterreminder.sync;

import android.content.Context;

import androidx.annotation.NonNull;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ReminderUtils {

    private static final int REMINER_INTERVAL_MINUTES = 15;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds( REMINER_INTERVAL_MINUTES ));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String REMINDER_JOG_TAG = "water-drink-reminder-tag";

    private static boolean sInitialized;

    synchronized public static void scheduleChargingReminder(@NonNull final Context context){
        if (sInitialized) return;

//        creates googlplaydriver
        Driver driver = new GooglePlayDriver( context );

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher( driver );

        Job constraintReminderJob = dispatcher.newJobBuilder()
                /* The Service that will be used to write to preferences */
                .setTag( REMINDER_JOG_TAG )
                .setConstraints( Constraint.DEVICE_CHARGING )
                .setLifetime( Lifetime.FOREVER )
                .setRecurring(true)
                .setTrigger( Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS+SYNC_FLEXTIME_SECONDS ) )
                .setReplaceCurrent(true)
                .setService( WaterReminderJobService.class )
                .build();


        dispatcher.schedule( constraintReminderJob );
        sInitialized = true;

    }

}
