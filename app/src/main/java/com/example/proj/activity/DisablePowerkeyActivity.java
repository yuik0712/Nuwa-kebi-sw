package com.example.proj.activity;

import android.view.View;

public class DisablePowerkeyActivity extends BaseAppCompatActivity {
    private final static String TAG = "DisablePowerkeyExampleActivity";

    // 기본 UI 및 Activity 생명주기
    ImageButton mCloseBtn; // UI 요소: 닫기 버튼

    // 로봇 API 연결 및 연결관리
    NuwaRobotAPI mRobotAPI; // 로봇 제어 API 객체
    IClientId mClientId; // 클라이언트 식별자

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Activity 기본 설정
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disablepowerkey);

        // 닫기 버튼 최소화
        mCloseBtn = findViewById(R.id.imgbtn_quit);

        // 닫기 버튼 클릭 시 전원 키 복구 로직 통합(추후 구현)
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 닫기 버튼 클릭 시 실행
                if(mRobotAPI != null) {
                    // 핵심 기능: 앱 종료 전, 비활성화 했던 전원 버튼 기능 재 활성화
                    mRobotAPI.enablePowerKey();
                }
                finish(); // Activity 종료
            }
        });

        // Nuwa 로봇 API 연결 및 연결 관리 로직
        mClientId = new IClientId(this.getPackageName()); // 로봇 API 클라이언트 식별자 초기화

        // 로봇 API 객체 초기화 (연결 시작)
        mRobotAPI = new NuwaRobotAPI(this, mClientId);
        Log.d(TAG, "register EventListener");

        mRobotAPI.registerRobotEvenListener(robotEventListener); // 로봇 이벤트 리스너 등록
    }

    // 기본 UI 및 Activity 생명주기 설정 (BaseAppCompatActivity 추상 메서드)
    @Override
    protected int getLayoutRes(){
        return R.layout.activity_disablepowerkey;
    }

    @Override
    protected int getTooolBarTitleRes() {
        return R.string.disablepowerkey_sdk_title;
    }

    // 액티비티 생명 주기 메서드 및 API 정리 로직
    @Override
    protected void onResume() {
        super.onResume();
        // hideSystemUi(); // 시스템 UI 숨김 기능 호출 주석 처리
    }

    // UI를 벗어날 때 로봇 API 사용을 위해 전원 키를 임시 활성화 (곧 제거할 기능...)
    protected void onPause() {
        super.onPause();
        if(mRobotAPI != null) {
            mRobotAPI.enablePowerKey();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 정리 작업이 필요하면 여기에 추가
    }

    // UI 숨김 기능
    /*
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
    */

    //
    RobotEventListener robotEventListener = new RobotEventListener() {
        @Override
        public void onWikiServiceStart() {
            // Nuwa Robot SDK가 준비되었을 때 호출됨 (API 호출 가능)
            Log.d(TAG,"onWikiServiceStart, robot ready to be control ") ;

            Log.d(TAG,"disablePowerKey");
            // 로봇의 전원 버튼 기능을 비활성화
            mRobotAPI.disablePowerKey();
        }

        @Override
        public void onWikiServiceStop() {
            // 로봇 SDK 서비스가 중지되었을 때 호출
        }

        @Override
        public void onWikiServiceCrash() {
            // 로봇 SDK 서비스가 충돌(Crash)되었을 때 호출
        }

        @Override
        public void onWikiServiceRecovery() {
            // 로봇 SDK 서비스가 충돌 후 복구되었을 때 호출
        }

        @Override
        public void onStartOfMotionPlay(String s) {
            // 모션 재생이 시작될 때 호출
        }

        @Override
        public void onPauseOfMotionPlay(String s) {
            // 모션 재생이 일시 정지될 때 호출
        }

        @Override
        public void onStopOfMotionPlay(String s) {
            // 모션 재생이 중지될 때 호출
        }

        @Override
        public void onCompleteOfMotionPlay(String s) {
            // 모션 재생이 완료되었을 때 호출
        }

        @Override
        public void onPlayBackOfMotionPlay(String s) {
            // 모션이 되감기 되었을 때 호출
        }

        @Override
        public void onErrorOfMotionPlay(int i) {
            // 모션 재생 중 오류가 발생했을 때 호출
        }

        @Override
        public void onPrepareMotion(boolean b, String s, float v) {
            // 모션을 준비 중일 때 호출
        }

        @Override
        public void onCameraOfMotionPlay(String s) {
            // 모션 중 카메라 이벤트가 발생했을 때 호출
        }

        @Override
        public void onGetCameraPose(float v, float v1, float v2, float v3, float v4, float v5, float v6, float v7, float v8, float v9, float v10, float v11) {
            // 카메라 포즈(위치/회전) 정보를 받았을 때 호출
        }

        @Override
        public void onTouchEvent(int i, int i1) {
            // 터치 이벤트가 발생했을 때 호출
        }

        @Override
        public void onPIREvent(int i) {
            // PIR 센서 이벤트(열 감지)가 발생했을 때 호출
        }

        @Override
        public void onTap(int i) {
            // 탭(Tap) 터치 이벤트가 발생했을 때 호출
        }

        @Override
        public void onLongPress(int i) {
            // 길게 누르는(Long Press) 이벤트가 발생했을 때 호출
        }

        @Override
        public void onWindowSurfaceReady() {
            // 렌더링을 위한 윈도우 서피스가 준비되었을 때 호출
        }

        @Override
        public void onWindowSurfaceDestroy() {
            // 렌더링을 위한 윈도우 서피스가 파괴되었을 때 호출
        }

        @Override
        public void onTouchEyes(int i, int i1) {
            // 로봇의 눈 영역에 터치 이벤트가 발생했을 때 호출
        }

        @Override
        public void onRawTouch(int i, int i1, int i2) {
            // 로우(Raw) 터치 이벤트가 발생했을 때 호출
        }

        @Override
        public void onFaceSpeaker(float v) {
            // 스피커 방향으로 얼굴이 향했을 때 호출
        }

        @Override
        public void onActionEvent(int i, int i1) {
            // 액션 이벤트가 발생했을 때 호출
        }

        @Override
        public void onDropSensorEvent(int i) {
            // 낙하 감지 센서 이벤트가 발생했을 때 호출
        }

        @Override
        public void onMotorErrorEvent(int i, int i1) {
            // 모터 오류 이벤트가 발생했을 때 호출
        }
    };
}