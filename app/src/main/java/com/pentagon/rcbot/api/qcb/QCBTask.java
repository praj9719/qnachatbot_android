package com.pentagon.rcbot.api.qcb;

import android.os.AsyncTask;
import android.util.Log;

import com.pentagon.rcbot.api.APIClient;
import com.pentagon.rcbot.api.APIInterface;

import retrofit2.Response;

public class QCBTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "QCBTask";
    private final String bot_name;
    private final String query;
    private final String out_lang;
    private final QCBResultListener listener;

    public QCBTask(String bot_name, String query, String out_lang, QCBResultListener listener) {
        this.bot_name = bot_name;
        this.query = query;
        this.out_lang = out_lang;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onTaskStart();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            APIInterface apiInterface = APIClient.getApiInterface();
            RequestBody requestBody = new RequestBody();
            requestBody.setQuery(query);
            requestBody.setOut_lang(out_lang);
            Response<QCBResult> response = apiInterface.chat(bot_name, requestBody).execute();
            listener.onTaskComplete(response);
        }catch (Exception e){
            Log.d(TAG, "doInBackground: Exception: " + e.getMessage());
            listener.onTaskFailed(e);
        }
        return null;
    }
}
