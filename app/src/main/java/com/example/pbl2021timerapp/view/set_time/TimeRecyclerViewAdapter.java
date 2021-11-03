package com.example.pbl2021timerapp.view.set_time;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pbl2021timerapp.R;
import com.example.pbl2021timerapp.db.time.Time;

import java.util.List;

public class TimeRecyclerViewAdapter extends RecyclerView.Adapter<TimeRecyclerViewHolder> {
    private List<Time> times;

    public TimeRecyclerViewAdapter(List<Time> times) {
        this.times = times;
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
    }

    @Override
    public int getItemCount() {
        return (times != null)
                ? times.size()
                : 0;
    }
}
