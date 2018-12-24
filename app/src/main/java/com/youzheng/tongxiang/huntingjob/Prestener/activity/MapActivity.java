package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.tongxiang.huntingjob.Model.Hr.AddressJw;
import com.youzheng.tongxiang.huntingjob.Model.Hr.AddressListBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.AddressListData;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Widget.NoScrollListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by qiuweiyu on 2018/5/18.
 */

public class MapActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener, AMapLocationListener {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.map)
    MapView map;
    AMap aMap;

    PoiSearch.Query query;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    PoiSearch poiSearch;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.ls)
    NoScrollListView ls;
    @BindView(R.id.tv_text_position)
    TextView tvTextPosition;
    AddressListBean mbean ;
    private String position;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;

    private List<AddressListBean> data = new ArrayList<>();
    private CommonAdapter<AddressListBean> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        ButterKnife.bind(this);
        map.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textHeadTitle.setText("选择地址");
        mlocationClient = new AMapLocationClient(mContext);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mlocationClient.setLocationOption(mLocationOption);


        adapter = new CommonAdapter<AddressListBean>(mContext, data, R.layout.address_map_ls_item) {
            @Override
            public void convert(final ViewHolder helper, final AddressListBean item) {
                helper.setText(R.id.tv_text, item.getP() + item.getQ() + item.getR() + item.getH() + item.getI());
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("data", item.getG());
                        intent.putExtra("title", ((TextView) helper.getView(R.id.tv_text)).getText().toString());
                        setResult(1, intent);
                        finish();
                    }
                });
            }
        };
        ls.setAdapter(adapter);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RxPermissions permissions = new RxPermissions((MapActivity) this);
            permissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    if (aBoolean) {
                        mlocationClient.startLocation();
                    }
                }
            });
        } else {
            mlocationClient.startLocation();
        }

        if (aMap == null) {
            aMap = map.getMap();
        }
        setUpMap();
        initEvent();

        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvTextPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mbean!=null){
                    Intent intent = new Intent();
                    intent.putExtra("data", mbean.getG());
                    intent.putExtra("title", tvTextPosition.getText().toString());
                    setResult(1, intent);
                    finish();
                }
            }
        });

    }

    private void initEvent() {
        edtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mbean==null) {
                    query = new PoiSearch.Query(charSequence.toString(), "", "330400");
                }else {
                    query = new PoiSearch.Query(charSequence.toString(), "", mbean.getI());
                }
                query.setPageSize(100);// 设置每页最多返回多少条poiitem
                query.setPageNum(1);//设置查询页码
                poiSearch = new PoiSearch(mContext, query);
                poiSearch.setOnPoiSearchListener(MapActivity.this);
                poiSearch.searchPOIAsyn();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                Log.e("sssssssss",gson.toJson(cameraPosition));
                if (cameraPosition==null){
                    return;
                }
                double latitude ,longitude ;
                try {
                    JSONObject jsonObject = new JSONObject(gson.toJson(cameraPosition)).getJSONObject("target");
                    latitude = jsonObject.getDouble("latitude");
                    longitude = jsonObject.getDouble("longitude");
                    if (mbean==null) {
                        query = new PoiSearch.Query("", "", "330400");
                    }else {
                        query = new PoiSearch.Query("", "", mbean.getI());
                    }
                    query.setPageSize(100);// 设置每页最多返回多少条poiitem
                    query.setPageNum(1);//设置查询页码
                    poiSearch = new PoiSearch(mContext, query);
                    poiSearch.setOnPoiSearchListener(MapActivity.this);
                    poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude,longitude),10000000,true));
                    poiSearch.searchPOIAsyn();
                } catch (JSONException e) {
                    e.printStackTrace();
                    showToast("请给应用赋予定位权限并把GSP按钮打开");
                }
            }
        });

    }


    private void setUpMap() {
        position = getIntent().getStringExtra("position");
        if (position != null) {
            String[] strings = position.split(",");
            LatLng latLng = new LatLng(Double.parseDouble(strings[1]), Double.parseDouble(strings[0]));
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        mlocationClient.stopLocation();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            AddressListData listData = gson.fromJson(gson.toJson(poiResult), AddressListData.class);
            adapter.setData(listData.getB());
        } else {
            adapter.setData(new ArrayList<AddressListBean>());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.e("sssssss1ss",gson.toJson(aMapLocation));
        if (aMapLocation != null) {
            try {
                JSONObject object = new JSONObject(gson.toJson(aMapLocation));
                if (object.getString("q") != null) {
                    if (object.getString("q").equals("success")) {
                        mlocationClient.stopLocation();
                        mbean = new AddressListBean();
                        mbean.setP(object.getString("i"));
                        AddressJw addressJw = new AddressJw();
                        addressJw.setA(object.getDouble("t"));
                        addressJw.setB(object.getDouble("u"));
                        mbean.setI(object.getString("h"));
                        mbean.setG(addressJw);
                        tvTextPosition.setText("当前定位位置："+object.getString("i"));
                    }else {
                        showToast("请给应用赋予定位权限并把GSP按钮打开");
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
