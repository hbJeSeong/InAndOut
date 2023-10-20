package com.jshwangbo.inandout.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.jshwangbo.inandout.R;
import com.jshwangbo.inandout.util.INOConstants;
import com.kakao.sdk.common.util.Utility;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapType;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.MapViewInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MapActivity extends AppCompatActivity {

    static String TAG = INOConstants.TAG_MAPACTIVITY;
    public MapActivity mapActivity;
    MapView mapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Log.d(TAG, "call onCreate()");

        Log.d(TAG, "HASH : " + Utility.INSTANCE.getKeyHash(this));

        mapActivity = this;
        mapView = findViewById(R.id.map_view);
        mapView.start(new MapLifeCycleCallback() {
            @Override
            public void onMapDestroy() {
                Log.d(TAG, "call onMapDestroy()");
            }

            @Override
            public void onMapError(Exception error) {
                Toast.makeText(mapActivity.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "call onMapError() | " + error);
            }
        }, new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(KakaoMap kakaoMap) {
                Log.d(TAG, "call onMapReady()");
                // 인증 후 API 가 정상적으로 실행될 때 호출됨
            }

            @Override
            public LatLng getPosition() {
                // 지도 시작 시 위치 좌표를 설정
                return LatLng.from(37.406960, 127.115587);
            }

            @Override
            public int getZoomLevel() {
                // 지도 시작 시 확대/축소 줌 레벨 설정
                return 15;
            }

            @Override
            public MapViewInfo getMapViewInfo() {
                // 지도 시작 시 App 및 MapType 설정
                return MapViewInfo.from(String.valueOf(MapType.NORMAL));
            }

            @Override
            public String getViewName() {
                // KakaoMap 의 고유한 이름을 설정
                return "MyFirstMap";
            }

            @Override
            public boolean isVisible() {
                // 지도 시작 시 visible 여부를 설정
                return true;
            }

            @Override
            public String getTag() {
                // KakaoMap 의 tag 을 설정
                return "FirstMapTag";
            }
        });
    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e(TAG, "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA1");
                md.update(signature.toByteArray());
                Log.d(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e(TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}