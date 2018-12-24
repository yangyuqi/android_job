package com.youzheng.tongxiang.huntingjob.Model.entity.deliver;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/22.
 */

public class InvitationList {
    private int count ;
    private List<InvitationListBeam> invitationList ;
    private List<DeliverBean> deliveryList ;

    public List<DeliverBean> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<DeliverBean> deliveryList) {
        this.deliveryList = deliveryList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<InvitationListBeam> getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(List<InvitationListBeam> invitationList) {
        this.invitationList = invitationList;
    }
}
