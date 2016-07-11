package com.wenzs.putaomovie;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/29.
 */
public class Cinema {
    private String ret_code;
    private String msg;
    private CinameData[] data;

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CinameData[] getData() {
        return data;
    }

    public void setData(CinameData[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "ret_code='" + ret_code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
