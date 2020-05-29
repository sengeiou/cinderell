package com.cinderellavip.bean.direct;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DirectPersonInfo {
    public int id;
    public double distance;
    public String avatar;
    public String name;
    public String work;
    public String address;
    public String covenant;

    public List<String> qualification;
    public DirectPersonComment comment;

    public List<DirectProjectItem> project;

    @SerializedName("package")
    public List<DirectProjectItem> pack_age;

    public int reserve;

    public String getStatusText(){
        if (reserve == 0){
            return "今日不可约";

        }
        return "今日可约";
    }

    public String getRecentTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String format = df.format(new Date());
        if (covenant.contains(format)){
            return "今天 "+covenant.replaceAll(format,"");
        }else {
            return covenant;
        }
    }


}
