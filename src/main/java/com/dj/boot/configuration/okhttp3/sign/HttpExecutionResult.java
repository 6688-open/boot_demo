package com.dj.boot.configuration.okhttp3.sign;

public class HttpExecutionResult {

    private byte[] rawResult;
    private String result;

    public HttpExecutionResult(byte[] rawResult) {
        this.rawResult = rawResult;
    }

    public byte[] getRawResult() {
        return rawResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
