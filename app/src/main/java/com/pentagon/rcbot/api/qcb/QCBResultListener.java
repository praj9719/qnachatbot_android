package com.pentagon.rcbot.api.qcb;

import com.pentagon.rcbot.api.reverse.ReverseResult;

import retrofit2.Response;

public interface QCBResultListener {
    void onTaskStart();
    void onTaskComplete(Response<QCBResult> response);
    void onTaskFailed(Exception e);
}
