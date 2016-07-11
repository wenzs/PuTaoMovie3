package com.wenzs.putaomovie;

/**
 * Created by Administrator on 2016/7/6.
 */
public class MovieDetial {

    private String ret_code;
    private String msg;
    private MovieData data;

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


    public MovieData getData() {
        return data;
    }

    public void setData(MovieData data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "MovieDetial{" +
                "ret_code='" + ret_code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
