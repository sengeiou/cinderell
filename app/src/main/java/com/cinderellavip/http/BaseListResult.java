package com.cinderellavip.http;


import java.util.List;

/**
 * Created by jumpbox on 16/8/23.
 */
public class BaseListResult<T>{
    public int code;
    public List<T> data;
    public String message;
}
