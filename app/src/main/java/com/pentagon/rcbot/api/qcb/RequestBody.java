package com.pentagon.rcbot.api.qcb;

import com.google.gson.annotations.Expose;

public class RequestBody {
    @Expose
    private String query;

    @Expose
    private String out_lang;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getOut_lang() {
        return out_lang;
    }

    public void setOut_lang(String out_lang) {
        this.out_lang = out_lang;
    }
}
