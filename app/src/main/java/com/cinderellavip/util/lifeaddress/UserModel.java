package com.cinderellavip.util.lifeaddress;

import android.content.Context;

import com.cinderellavip.util.address.CityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LWK
 * TODO
 * 2017/6/26
 */

public class UserModel
{
    public static List<User> getUserDatas(Context context)
    {
        List<User> list = new ArrayList<>();
        ArrayList<LifeAddress> lifeCity = CityUtil.getLifeCity(context);
        for (LifeAddress address:lifeCity){
            for (LifeAddressItem lifeAddressItem:address.list){
                list.add(new User(lifeAddressItem.name, address.initial));
            }
        }
        return list;
    }


}
