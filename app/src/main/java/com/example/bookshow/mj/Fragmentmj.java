package com.example.bookshow.mj;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.bookshow.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Fragmentmj extends Fragment {
    private static final int WHAT_DATA_OK = 10;
    private MapView mMapView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mj, container);
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        //设定中心点坐标
        LatLng cenpt =  new LatLng(22.255354,113.537369);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(cenpt)
                //放大地图到20倍
                .zoom(20)
                .build();

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        BaiduMap mBaiduMap = mMapView.getMap();
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        mMapView.setLogoPosition(LogoPosition.logoPostionCenterTop);

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_launcher);



        Handler handler=new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==WHAT_DATA_OK)
                {
                    String content= msg.getData().getString("data");
                    if(null!=content) {
                        try {
                            JSONObject jsonObject = new JSONObject(content);
                            JSONArray shops=jsonObject.getJSONArray("shops");
                            for(int index=0;index<shops.length();index++)
                            {
                                JSONObject shop=shops.getJSONObject(index);

                                LatLng centerPoint = new LatLng(shop.getDouble("latitude"),shop.getDouble("longitude"));
                                MarkerOptions markerOption = new MarkerOptions().icon(bitmap).position(centerPoint);
                                Marker marker = (Marker) mMapView.getMap().addOverlay(markerOption);
                                OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(50)
                                        .fontColor(0xFFFF00FF).text(shop.getString("name")).rotate(0).position(centerPoint);
                                mMapView.getMap().addOverlay(textOption);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

            }
        };
        Runnable runnable= () -> {
            try {
                URL url = new URL("http://file.nidama.net/class/mobile_develop/data/bookstore.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setUseCaches(false);
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                    String line="";
                    StringBuffer stringBuffer=new StringBuffer();
                    while(null!=(line=bufferedReader.readLine()))
                        stringBuffer.append(line);
                    Message message=new Message();
                    message.what= WHAT_DATA_OK;
                    Bundle bundle=new Bundle();
                    bundle.putString("data",stringBuffer.toString());
                    message.setData(bundle);

                    handler.sendMessage(message);
                    Log.i("test", "onCreateView: "+stringBuffer.toString());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        new Thread(runnable).start();
        //构建MarkerOption，用于在地图上添加Marker，地图上的自定义的标签和
        OverlayOptions option = new MarkerOptions()
                .position(cenpt)
                .icon(bitmap);

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        OverlayOptions mTextOptions = new TextOptions()
                .text("百度地图SDK") //文字内容
                .bgColor(0xAAFFFF00) //背景色
                .fontSize(24) //字号
                .fontColor(0xFFFF00FF) //文字颜色
                .rotate(-30) //旋转角度
                .position(cenpt);

//在地图上显示文字覆盖物
        Overlay mText = mBaiduMap.addOverlay(mTextOptions);
        //对 marker 添加点击相应事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()

    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        mMapView.onDestroy();
    }
}