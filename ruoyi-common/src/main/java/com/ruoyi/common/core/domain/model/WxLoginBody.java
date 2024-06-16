package com.ruoyi.common.core.domain.model;

public class WxLoginBody {
    private String code;
    private String encryptedIv;
    private String encryptedData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEncryptedIv() {
        return encryptedIv;
    }

    public void setEncryptedIv(String encryptedIv) {
        this.encryptedIv = encryptedIv;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    @Override
    public String toString() {
        return "WxLoginBody{" +
                "code='" + code + '\'' +
                ", encryptedIv='" + encryptedIv + '\'' +
                ", encryptedData='" + encryptedData + '\'' +
                '}';
    }
}
