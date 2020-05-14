package com.cinderellavip.bean.net.mine;

public class MineBalanceItem {



    public String message;
    public String create_at;
    public String amount;
    public int type;

    public String getAmount() {
        if (type == 1){
            return "+"+amount;
        }else if (type == 0){
            return "-"+amount;
        }
        return amount;
    }
}
