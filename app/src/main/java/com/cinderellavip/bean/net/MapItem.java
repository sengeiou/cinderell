package com.cinderellavip.bean.net;

import java.util.List;

public class MapItem {
    public String lng;//经度
    public String lat;//纬度
    public String food_map_name;
    public String logo;
    public int parent_id;
    public int food_map_id;

    public List<MapItem> childFoodMap;


}
