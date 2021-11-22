package com.example.pbl2021timerapp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.pbl2021timerapp.view.set_time.SetTimeActivity;

public class AlarmBroadcastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Received ", Toast.LENGTH_LONG).show();

        Intent i = new Intent();
        i.setClassName("com.example.pbl2021timerapp", "com.example.pbl2021timerapp.GoTimerActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
