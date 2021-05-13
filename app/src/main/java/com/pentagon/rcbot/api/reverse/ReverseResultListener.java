package com.pentagon.rcbot.api.reverse;

import retrofit2.Response;

public interface ReverseResultListener {
    void onTaskStart();
    void onTaskComplete(Response<ReverseResult> response);
    void onTaskFailed(Exception e);
}
