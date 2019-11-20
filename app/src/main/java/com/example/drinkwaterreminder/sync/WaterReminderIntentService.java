package com.example.drinkwaterreminder.sync;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class WaterReminderIntentService extends IntentService {
    public WaterReminderIntentService(){ super("WaterReminderIntentService");}

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTask( this, action );
    }
}
