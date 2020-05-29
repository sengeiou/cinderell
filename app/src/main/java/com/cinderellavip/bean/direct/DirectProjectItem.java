package com.cinderellavip.bean.direct;

import com.cinderellavip.util.ArithmeticUtil;

import java.util.List;

public class DirectProjectItem {

    //项目
    public String attitude;
    public String efficacy;
    public String unsuitable;
    public String process;
    public String supplies;
    public int typesof;

    //套餐
    public List<String> contain;


    //共有
    public int id;
    public String title;
    public String thumb_nail;
    public double duration;
    public double price;
    public String city;

    public String getContent(){
        if (typesof == 1){
            return "服务时长："+duration+"分钟 服务姿势："+attitude
                    +" 原理功效："+efficacy
                    +" 不宜人群："+unsuitable
                    +" 服务流程："+process
                    +" 服务用品："+supplies
                    ;
        }else {
            String s = "包含项目：";
            for (int i = 0;i<contain.size();i++){
                String s1 = contain.get(i);
                if (i == contain.size() -1){
                    s += s1+"\n";
                }else {
                    s += s1+"、";
                }
            }
            s += "服务时长："+duration+"分钟";
            return s;
        }
    }


    public String getPrice() {
        return ArithmeticUtil.convert(price)+"元/次";
    }
}
