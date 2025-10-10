package com.example.proj.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.proj.R;
import com.example.proj.motion.base.BaseAppCompatActivity;
import com.example.proj.service.IClientId;
import com.example.proj.service.agent.NuwaRobotAPI;
import com.example.proj.service.agent.RobotEventListener;
import com.example.proj.service.agent.VoiceEventListener;
import com.example.proj.service.facecontrol.utils.ServiceConnectListener;

import com.nuwarobotics.app.nuwableinterface.BLEConstant
import com.nuwarobotics.app.nuwableinterface.INuwaBLEInterface;

public class StartBLEConnnection extends BaseAppCompatActivity {
    // UI 요소 선언
    ImageButton mCloseBtn;
    Button mBtnDemo;
    Context mContext;
    INuwaBLEInterface mNuwaBLEService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_measure);
        mContext = this;

        // 시작 버튼 클릭 리스너
        mBtnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public onClick(View view) {
                try {
                    if (mNuwaBLEService.getBLEStatus(BLEConstant.BLE_MODE_NUWA_THERMOMETER_VSS) != BLEConstant.STATE_CONNECTED) {
                        // 연결 상태가 아닐 경우 설정 (Activity 실향)
                        Intent intent = new Intent("com.nuwarobotics.health.action.ble_connect");
                        startActivityForResult(intent, 0);
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "Get Ble status error:" + e);
                }
            }
        });

        // 닫기 버튼 초기화 및 종료 로직
        mCloseBtn = findViewById(R.id.imgbtn_quit);
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // BaseAppCompatActivity의 추상 메소드 구현
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume ");
        hideSystemUi();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
    }

    @Override
    protected void onDestroy() {
        // 서비스 바인딩 해제
        unbindService(mServiceConnection);
        super.onDestroy();
        Log.d(TAG, "onDestroy ");
    }

    // BLE 서비스 연결 객체
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mNuwaBLEService = INuwaBLEInterface.Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected");
            try {
                if (mNuwaBLEService.isBluetoothReady()) {
                    // 서비스 연결 후 BLE 상태 확인
                    if (mNuwaBLEService.getBLEStatus(BLEConstant.BLE_MODE_NUWA_THERMOMETER_VSS) == BLEConstant.STATE_CONNECTED) {
                        // 블루투스가 연결되었습니다.
                    } else { // 그렇지 않으면,
                        // 블루투스 연결이 안되었습니다.
                    }
                }
            } catch (RemoteException e) {
                Log.e(TAG, "exception " + e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mNuwaBLEService = null;
            Log.e(TAG, "onServiceDisconnected");
        }

        // Activity 시작 시 BLE 서비스 바인딩 (HealthConstant는 외부에서 정의된 상수 클래스 가정)
        @Override
        protected void onStart() {
            super.onStart();
            bindService(new Intent(HealthConstant.NUWA_BLE_SERVICE_ACTION).setPackage(HealthConstant.NUWA_BLE_SERVICE_PACKAGE), mServiceConnection, BIND_AUTO_CREATE);
            Log.d("MyActivity", "onStart() called");
        }
    };

    // Fullscreen 및 System UI 숨김 기능
    protected void hideSystemUi() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}