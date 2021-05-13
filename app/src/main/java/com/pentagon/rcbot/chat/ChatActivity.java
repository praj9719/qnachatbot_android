package com.pentagon.rcbot.chat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pentagon.rcbot.R;
import com.pentagon.rcbot.adapter.MessageAdapter;
import com.pentagon.rcbot.api.qcb.QCBResult;
import com.pentagon.rcbot.api.qcb.QCBResultListener;
import com.pentagon.rcbot.api.qcb.QCBTask;
import com.pentagon.rcbot.api.reverse.ReverseResult;
import com.pentagon.rcbot.api.reverse.ReverseResultListener;
import com.pentagon.rcbot.api.reverse.ReverseTask;
import com.pentagon.rcbot.object.Message;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    private String current_bot = null;
    private String current_out_lang = null;

    private EditText mEdit;
    private TextView mSend, mOptions, mBotName, mBotDetail;
    private ProgressBar mProgress;
    private RecyclerView mRecycler;
    private List<Message> mList;
    private MessageAdapter messageAdapter;
    private NestedScrollView mScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mEdit = findViewById(R.id.ac_edit);
        mSend = findViewById(R.id.ac_send);
        mBotName = findViewById(R.id.ac_bot_name);
        mBotDetail = findViewById(R.id.ac_bot_detail);
        mRecycler = findViewById(R.id.ac_recycler);
        mOptions = findViewById(R.id.ac_options);
        mProgress = findViewById(R.id.ac_progress);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setNestedScrollingEnabled(false);
        mScroll = findViewById(R.id.ac_scroll);
        mList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this);
        messageAdapter.updateMessages(mList);
        mRecycler.setAdapter(messageAdapter);
        mOptions.setOnClickListener(view -> editBot());
        mSend.setOnClickListener(view -> send());
        current_bot = getString(R.string.bot_ssc_science);
        current_out_lang = getString(R.string.lang_english);
        updateBotDetails();
    }

    private void updateBotDetails(){
        mBotName.setText(String.valueOf(current_bot));
        mBotDetail.setText("Output in " + String.valueOf(current_out_lang) + " language");
    }

    private String getBotName(){
        if (current_bot.equals(getString(R.string.bot_vishwakosh))) return "vishwakosh";
        else if(current_bot.equals(getString(R.string.bot_ssc_science))) return "ssc_science";
        else if(current_bot.equals(getString(R.string.bot_ssc_history))) return "ssc_history";
        return "ssc_science";
    }

    private String getOutputLang(){
        if (current_out_lang.equals(getString(R.string.lang_same))) return "same";
        else if (current_out_lang.equals(getString(R.string.lang_english))) return "en";
        else if (current_out_lang.equals(getString(R.string.lang_marathi))) return "mr";
        else if (current_out_lang.equals(getString(R.string.lang_hindi))) return "hi";
        else return "en";
    }

    private void editBot(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_edit_bot, null);
        RadioGroup mRadioBotGroup = view.findViewById(R.id.ac_rg_bot);
        RadioGroup mRadioLangGroup = view.findViewById(R.id.ac_rg_lang);

        int bId = R.id.ac_rb_bot_ssc_science;
        if (current_bot.equals(getString(R.string.bot_vishwakosh))) bId = R.id.ac_rb_bot_vishwakosh;
        else if(current_bot.equals(getString(R.string.bot_ssc_history))) bId = R.id.ac_rb_bot_ssc_history;

        int lId = R.id.ac_rb_lang_english;
        if (current_out_lang.equals(getString(R.string.lang_same))) lId = R.id.ac_rb_lang_same;
        else if (current_out_lang.equals(getString(R.string.lang_marathi))) lId = R.id.ac_rb_lang_marathi;
        else if (current_out_lang.equals(getString(R.string.lang_hindi))) lId = R.id.ac_rb_lang_hindi;

        RadioButton tBot = view.findViewById(bId);
        RadioButton tLang = view.findViewById(lId);
        tBot.setChecked(true);
        tLang.setChecked(true);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("Update", (dialogInterface, i) -> {
                    int selectedBotId = mRadioBotGroup.getCheckedRadioButtonId();
                    int selectedLangId = mRadioLangGroup.getCheckedRadioButtonId();
                    RadioButton rBot = view.findViewById(selectedBotId);
                    RadioButton rLang = view.findViewById(selectedLangId);
                    current_bot = rBot.getText().toString();
                    current_out_lang = rLang.getText().toString();
                    updateBotDetails();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {})
                .create();
        dialog.show();

    }

    private void send(){
        String query = mEdit.getText().toString().trim();
        mEdit.setText("");
        if (query.isEmpty()) return;
        mList.add(new Message(query, "", true));
        updateChat();
        new QCBTask(getBotName(), query, getOutputLang(), new QCBResultListener() {
            @Override
            public void onTaskStart() {
                runOnUiThread(() -> mProgress.setVisibility(View.VISIBLE));
            }

            @Override
            public void onTaskComplete(Response<QCBResult> response) {
                runOnUiThread(() -> mProgress.setVisibility(View.GONE));
                Log.d(TAG, "onTaskComplete: received");
                if (response.isSuccessful()){
                    QCBResult qcbResult = response.body();
                    if (qcbResult != null) {
                        Log.d(TAG, "onTaskComplete: status: " + qcbResult.getStatus());
//                        Log.d(TAG, "onTaskComplete: msg length: " + qcbResult.getData().getMessage().size());
                        List<com.pentagon.rcbot.api.qcb.Message> msgList = qcbResult.getData().getMessage();
                        for (com.pentagon.rcbot.api.qcb.Message msg : msgList){
//                            Log.d(TAG, "onTaskComplete: ans: " + msg.getShort());
                            mList.add(new Message(msg.getShort(), msg.getLong(), false));
                            updateChat();
                        }
                    }
                }
            }

            @Override
            public void onTaskFailed(Exception e) {
                runOnUiThread(() -> mProgress.setVisibility(View.GONE));
                Log.d(TAG, "onTaskFailed: " + e.getMessage());
            }
        }).execute();
    }


//    private void send(){
//        String message = mEdit.getText().toString().trim();
//        mEdit.setText("");
//        if (message.isEmpty()) return;
//        mList.add(new Message(message, true));
//        updateChat();
//        new ReverseTask(message, new ReverseResultListener() {
//            @Override
//            public void onTaskStart() {
//                Log.d(TAG, "onTaskStart: init");
//            }
//
//            @Override
//            public void onTaskComplete(Response<ReverseResult> response) {
//                if (response.isSuccessful()){
//                    ReverseResult result = response.body();
//                    assert result != null;
//                    assert result.getData() != null;
//                    String msg = result.getData().getMessage();
//                    mList.add(new Message(msg, false));
//                    updateChat();
//                }
//            }
//
//            @Override
//            public void onTaskFailed(Exception e) {
//                Log.d(TAG, "onTaskFailed: " + e.getMessage());
//            }
//        }).execute();
//    }

    private void updateChat(){
        runOnUiThread(() -> {
            messageAdapter.updateMessages(mList);
            mScroll.post(() -> {
                mScroll.fullScroll(View.FOCUS_DOWN);
                mEdit.requestFocus();
            });
        });
    }

}