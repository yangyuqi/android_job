package com.youzheng.tongxiang.huntingjob.Model.entity.deliver;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/12.
 */

public class DeliverBeanList {
    private List<DeliverBean> deliveryList ;

    private int count ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public List<DeliverBean> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<DeliverBean> deliveryList) {
        this.deliveryList = deliveryList;
    }
}
