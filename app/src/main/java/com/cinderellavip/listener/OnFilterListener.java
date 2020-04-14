package com.cinderellavip.listener;

public interface OnFilterListener {

    void onComplex(); //综合的监听

    /**
     * @param isDown 从上到下
     */
    void onPrice(boolean isDown); //价格的监听
    void onCategray(boolean isDown); //筛选条件
    /**
     * @param isDown 从上到下
     */
    void onSaleVolume(boolean isDown); //效率的监听
}
