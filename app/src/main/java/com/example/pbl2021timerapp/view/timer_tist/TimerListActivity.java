package com.example.pbl2021timerapp.view.timer_tist;

import static android.Manifest.permission.RECORD_AUDIO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Button;

import com.example.pbl2021timerapp.R;
import com.example.pbl2021timerapp.RecordingCheckActivity;
import com.example.pbl2021timerapp.data_manager.timer_list.TimeDataManagerCallback;
import com.example.pbl2021timerapp.data_manager.timer_list.TimeDataManager;
import com.example.pbl2021timerapp.db.time.Time;
import com.example.pbl2021timerapp.view.set_time.SetTimeActivity;
import com.example.pbl2021timerapp.view.set_time.TimeRecyclerViewAdapter;

import java.util.List;

public class TimerListActivity extends AppCompatActivity {
    // DB 関連の動作を包括するクラス
    private TimeDataManager timeDataManager;

    // DB 関連の動作で使用するコールバック
    private TimeDataManagerCallback callback;

    // RecyclerView
    private RecyclerView _rv;

    private Context context;

    static final int REQUEST_CODE = 1;

    public TimerListActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int granted = ContextCompat.checkSelfPermission(this, RECORD_AUDIO);
        if (PackageManager.PERMISSION_GRANTED != granted) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CODE);

        }

        // FAB をクリックしたときの処理を以下のコールバックで受け取る
        findViewById(R.id.openSetTimeActivityButton).setOnClickListener(view -> {
            // クリック時の処理
            this.onButtonClick();
        });

        findViewById(R.id.recordingOpenButton).setOnClickListener(view -> {
            Intent intent = new Intent(this, RecordingCheckActivity.class);
            startActivity(intent);
        });

        // RecyclerView を取得する
        _rv = (RecyclerView) findViewById(R.id.timerListRecyclerView);

        // Room を使用し、データを取得する
        // 取得後は RecyclerView を更新するコールバックが実行される
        timeDataManager = new TimeDataManager(this);
        callback = new TimeDataManagerCallback(this);
        timeDataManager.setCallback(callback);
        timeDataManager.read();

        this.context = getApplicationContext();
    }

    public Boolean checkRecordable(){
        if(!SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.RECORD_AUDIO
                        },
                        REQUEST_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permission, int[] grantResults
    ){
        Log.d("MainActivity","onRequestPermissionsResult");

        if (grantResults.length <= 0) { return; }
//        switch(requestCode){
//            case REQUEST_CODE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mText.setText("");
//                } else {
//
//                }
//                break;
//        }
    }

    private void onButtonClick() {
        Intent intent = new Intent(this, SetTimeActivity.class);
        startActivity(intent);
    }

    /**
     * DB 読み込み完了後、RecyclerView を更新する
     *
     * @param times DB から取得した SetTime
     */
    public void updateRecyclerView(List<Time> times) {
        TimeRecyclerViewAdapter adapter = new TimeRecyclerViewAdapter(context, times);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        _rv.setLayoutManager(layout);
        _rv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeDataManager.read();
    }

    @Override
    protected void onDestroy() {
        // Activity 破棄のタイミングで DB を閉じる
        timeDataManager.closeDatabase();
        super.onDestroy();
    }
}