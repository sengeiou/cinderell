package com.cinderellavip.bean.local;

public class ShareImageItem {





    public boolean isCheck;

    public ShareImageItem(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String path;

    public ShareImageItem(boolean isCheck, String path) {
        this.isCheck = isCheck;
        this.path = path;
    }
}
