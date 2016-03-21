package com.yukunlin.ykl.network;

/**
 * Created by yukunlin on 16/3/17.
 */
public class Translation {
    private String errNum;
    private String errMsg;
    private RetData retData;

    public String getErrNum() {
        return errNum;
    }

    public void setErrNum(String errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public RetData getRetData() {
        return retData;
    }

    public void setRetData(RetData retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "errNum='" + errNum + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", retData=" + retData +
                '}';
    }
}
