package com.pentagon.rcbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.pentagon.rcbot.api.qcb.Message;
import com.pentagon.rcbot.api.qcb.QCBResult;
import com.pentagon.rcbot.api.qcb.QCBResultListener;
import com.pentagon.rcbot.api.qcb.QCBTask;
import com.pentagon.rcbot.chat.ChatActivity;

import java.lang.annotation.IncompleteAnnotationException;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, ChatActivity.class));
            finish();
        }, 1000);
    }


}