package com.example.pbl2021timerapp.view.set_time;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pbl2021timerapp.view.go_timer.AlarmBroadcastReceiver;
import com.example.pbl2021timerapp.R;
import com.example.pbl2021timerapp.db.time.Time;

import java.util.List;

public class TimeRecyclerViewAdapter extends RecyclerView.Adapter<TimeRecyclerViewHolder> {
    private List<Time> times;
    private Context context;

    public TimeRecyclerViewAdapter(Context context, List<Time> times) {
        this.times = times;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        TimeRecyclerViewHolder holder = new TimeRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeRecyclerViewHolder holder, int position) {
        Time item = times.get(position);
        String timeStr = (String) item.getTimeStr();
        holder.timeTextView.setText(timeStr);

        holder.switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                String[] times = timeStr.split(":");
                int hour = Integer.parseInt(times[0]);
                int minute = Integer.parseInt(times[1]);


                Toast.makeText(holder.itemView.getContext(), String.format("%d時%d分にアラームをセットしました", hour, minute) , Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
//                        .putExtra(AlarmClock.EXTRA_MESSAGE, "alarm")
//                        .putExtra(AlarmClock.EXTRA_HOUR, 17)
//                        .putExtra(AlarmClock.EXTRA_MINUTES, 41);

                Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(holder.itemView.getContext(), 0, intent, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, 3000, alarmIntent);

//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.set(Calendar.HOUR_OF_DAY, 17);
//                calendar.set(Calendar.MINUTE, 17);
//
//                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

//                if (intent.resolveActivity(context.getPackageManager()) != null) {
//                    context.startActivity(intent);
//               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (times != null)
                ? times.size()
                : 0;
    }
}
