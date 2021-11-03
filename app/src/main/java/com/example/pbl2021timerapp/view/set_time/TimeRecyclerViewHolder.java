package com.example.pbl2021timerapp.view.set_time;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pbl2021timerapp.R;

public class TimeRecyclerViewHolder extends RecyclerView.ViewHolder {
    // 設定した時間を表示する TextView
    public TextView timeTextView;

    public TimeRecyclerViewHolder(View itemView) {
        super(itemView);
        timeTextView = itemView.findViewById(R.id.setTimeTextView);
    }
}
