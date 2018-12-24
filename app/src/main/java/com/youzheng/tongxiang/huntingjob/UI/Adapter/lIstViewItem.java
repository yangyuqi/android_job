package com.youzheng.tongxiang.huntingjob.UI.Adapter;

import java.util.HashMap;

/**
 * Created by qiuweiyu on 2017/10/8.
 */

public class lIstViewItem {
    public int type;
    public HashMap<String,Object> map ;
    public lIstViewItem(int type, HashMap<String, Object> map){
        this.type = type;
        this.map = map;
    }
}
