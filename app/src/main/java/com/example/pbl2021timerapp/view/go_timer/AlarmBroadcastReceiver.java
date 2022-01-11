package com.example.pbl2021timerapp.view.go_timer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmBroadcastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Received ", Toast.LENGTH_LONG).show();

        Intent _intent = new Intent();
        _intent.setClassName("com.example.pbl2021timerapp", "com.example.pbl2021timerapp.view.go_timer.GoTimerActivity");
        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(_intent);
    }
}
