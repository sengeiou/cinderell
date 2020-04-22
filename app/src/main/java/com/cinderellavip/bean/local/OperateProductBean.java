package com.cinderellavip.bean.local;

import java.util.List;

public class OperateProductBean {
    public String name;
    public List<OperateProductSecondBean> list;

    public OperateProductBean(String name, List<OperateProductSecondBean> list) {
        this.name = name;
        this.list = list;
    }
}
