package com.cinderellavip;

import com.cinderellavip.http.SignUtil;
import com.cinderellavip.util.CouponsStringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String url = "https://api.huiguniangvip.com/api/store/search-products?keyword=%E7%B2%89%E5%BA%95%E9%81%AE%E7%91%95&limit=10&page=1&sort=0&sort_type=0";
        int separatorIndex = url.lastIndexOf("?");
        String substring = url.substring(separatorIndex + 1);

        String replaceAll = substring.replaceAll("&", "=");
        String[] split = replaceAll.split("=");
        TreeMap<String, String> keys = new TreeMap<>();
        for (int i = 0;i<split.length;){
            keys.put(split[i],split[i+1]);
            System.out.println("参数 key = "+split[i]+",值"+split[i+1]);
            i = i+2;
        }
        System.out.println(substring);
        System.out.println(replaceAll);
        System.out.println(SignUtil.getMd5(keys,"1589096227"));
    }

    @Test
    public void addition_isCorrect1() {
        String url = "keyword=日用品&limit=10&page=1&sort=0&sort_type=0&secret=241cd2aa2aae01cd2&timestamp=1589098120";

        //de9ffcda115fd2b7bada22d527860ceb
        //de9ffcda115fd2b7bada22d527860ceb
        System.out.println(SignUtil.getMd51(url));
//        System.out.println(MD5Utils.getMD5String(url));
//        System.out.println(MD5Utils1.signature(url));

//        String str = "123"; //默认环境，已是UTF-8编码
//        try {
//            String strGBK = URLEncoder.encode(str, "GBK");
//            System.out.println(strGBK);
//            String strUTF8 = URLDecoder.decode(str, "UTF-8");
//            System.out.println(strUTF8);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }



    }

    public int getDaysOfMonth(int year, int month) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(year + "-" + month + "-01"));
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            return 0;
        }

    }

    @Test
    public void textJson() {
        String url = "[\n" +
                "    \"Marcus\",\n" +
                "    \"Christian\",\n" +
                "    \"Norman\"\n" +
                "]";
        Gson gson = new Gson();
        Type founderSetType = new TypeToken<HashSet<String>>(){}.getType();
        HashSet<String> set = gson.fromJson(url, founderSetType);
        System.out.println(set.size());


    }

    @Test
    public void textCouponString() {
        String url = "25";
        String removeUrl = "25";
        System.out.println(CouponsStringUtil.getString(url,removeUrl,false));


    }


    @Test
    public void testSort() {
       List<String> list = new ArrayList<>();
       for (int i=0;i<12;i++){
           list.add(i+"");
       }
        for (int i=0;i<list.size();i++){
            System.out.print(list.get(i)+" ");
        }

       List<String> sortList = new ArrayList<>();
       //6
       int column = list.size()/2+list.size()%2;
       for (int i=0;i<column;i++){
               sortList.add(list.get(i));
               if (i+column<list.size())
               sortList.add(list.get(i+column));
       }
        System.out.println(" ");
        for (int i=0;i<sortList.size();i++){
            System.out.print(sortList.get(i)+" ");
        }

    }
}