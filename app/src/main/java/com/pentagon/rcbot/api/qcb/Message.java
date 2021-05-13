
package com.pentagon.rcbot.api.qcb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("short")
    @Expose
    private String _short;

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getShort() {
        return _short;
    }

    public void setShort(String _short) {
        this._short = _short;
    }

}
