package com.cinderellavip.bean.net.mine;

import com.cinderellavip.http.ListResult;

public class WithDrawHistoryItem {



    public String title;
    public String account;
    public String money;
    public int status;
    public String status_txt;
    public String create_at;

    public String getStatus() {
        if (status == 1)
        return "审核中";
        else if (status == 2)
            return "审核通过";
        else if (status == 3)
            return "已打款";
        else if (status == 4)
            return "已打款";

            return "审核中";
    }
}
