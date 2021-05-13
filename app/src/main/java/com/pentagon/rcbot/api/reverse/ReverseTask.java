package com.pentagon.rcbot.api.reverse;

import android.os.AsyncTask;
import android.util.Log;

import com.pentagon.rcbot.api.APIClient;
import com.pentagon.rcbot.api.APIInterface;

import retrofit2.Response;

public class ReverseTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "ReverseTask";
    private final String message;
    private final ReverseResultListener listener;

    public ReverseTask(String message, ReverseResultListener listener) {
        this.message = message;
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
            requestBody.setMessage(message);
            Response<ReverseResult> response =  apiInterface.reverseString(requestBody).execute();
            listener.onTaskComplete(response);
        }catch (Exception e){
            Log.d(TAG, "doInBackground: Exception: " + e.getMessage());
            listener.onTaskFailed(e);
        }
        return null;
    }

}
