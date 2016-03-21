package com.yukunlin.ykl.network;

/**
 * Created by yukunlin on 16/3/17.
 */
public class RetData {
    private String from;
    private String to;
    private Trans_result trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Trans_result getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(Trans_result trans_result) {
        this.trans_result = trans_result;
    }

    @Override
    public String toString() {
        return "RetData{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", trans_result=" + trans_result +
                '}';
    }
}
