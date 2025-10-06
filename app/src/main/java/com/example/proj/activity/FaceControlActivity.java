package com.example.proj.activity;

import android.view.View;

public class FaceControlActivity extends BaseAppCompatActivity {
    private final static String TAG = "FaceControlActivity";

    // UI 요소 선언
    ImageButton mCloseBtn;
    Button mBtn_start;

    NuwaRobotAPI mRobotAPI;
    IClientId mClientId;
    Context mContext ;

    // Activity 상수 정의
    private static final String TTS = "kebbi is speaking with face";
    private static final long FACE_MOUTH_SPEED = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facecontrol_show);
        mContext = this; // Context 초기화

        // 닫기 버튼 초기화 및 종료 로직
        mCloseBtn = findViewById(R.id.imgbtn_quit);
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 시작 버튼 초기화
        mBtn_start = findViewById(R.id.btn_start);
        // 닫기 버튼 로직 수정 (mRobotAPI 사용 추가)
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 종료 전 powerKey 활성화 로직 추가
                if(mRobotAPI!=null){
                    mRobotAPI.enablePowerKey();
                }
                finish();
            }
        });

        // 초기 Nuwa API 객체
        mClientId = new IClientId(this.getPackageName());
        mRobotAPI = new NuwaRobotAPI(this,mClientId);
    }

    // Face Control 서비스 연결 리스너
    ServiceConnectListener FaceControlConnect = new ServiceConnectListener() {
        @Override
        public void onConnectChanged(ComponentName componentName, boolean isConnected) {
            // 서비스 연결 상태를 로그로 기록
            Log.d(TAG, "faceService 연결 상태: " + isConnected);

            if (isConnected) {
                // 연결되면 얼굴 인식 콜백 등록
                mRobotAPI.UnityFaceManager().registerCallback(mUnityFaceCallback);
            } else {
                // 연결 끊기면 콜백 해제
                mRobotAPI.UnityFaceManager().unregisterCallback(mUnityFaceCallback);
            }
        }
    };

    // BaseAppCompatActivity 추상 메서드 구현
    @Override
    protected int getLayoutRes () {
        return R.layout.activity_disablepowerkey;
    }

    @Override
    protected int getToolBarTitleRes () {
        return R.string.disablepowerkey_sdk_example_title;
    }

//    @Override
//    protected void onResume () {
//        super.onResume();

//        Log.d(TAG, "register EventListener");

//        mRobotAPI.registerRobotEventListener(robotEventListener);//listen callback of robot service event
//        // 시스템 UI 숨김
//        hideSystemUi();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause ") ;

        // onPause 자원 해제 로직 수정 (FaceManager release 추가)
        if(mRobotAPI!=null){
            mRobotAPI.UnityFaceManager().release(); // FaceManager release 추가
            mRobotAPI.release();
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
    }

    // Fullscreen 및 System UI 숨김 기능
//    protected void hideSystemUi () {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE);
//    }

    // 로봇의 서비스 이벤트를 수신하는 리스너
    RobotEventListener robotEventListener = new RobotEventListener() {
        @Override
        public void onWikiServiceStart() {
            // Nuwa 로봇 SDK 서비스 시작 호출
            Log.d(TAG,"onWikiServiceStart, 로봇 제어 준비 완료");

            // 음성 이벤트 콜백 등록 (로봇의 음성 관련 이벤트를 수신하기 위해 리스너 등록)
            mRobotAPI.registerVoiceEventListener(voiceEventListener);

            // 얼굴 제어 기능 초기화 (얼굴 인식 및 제어 서비스 시작)
            Log.d(TAG,"얼굴 제어 초기화");
            mRobotAPI.initFaceControl(mContext, mContext.getClass().getName(), FaceControlConnect);
        }

        @Override
        public void onWikiServiceStop() {

        }

        @Override
        public void onWikiServiceCrash() {

        }

        @Override
        public void onWikiServiceRecovery() {

        }

        @Override
        public void onStartOfMotionPlay(String s) {

        }

        @Override
        public void onPauseOfMotionPlay(String s) {

        }

        @Override
        public void onStopOfMotionPlay(String s) {

        }

        @Override
        public void onCompleteOfMotionPlay(String s) {

        }

        @Override
        public void onPlayBackOfMotionPlay(String s) {

        }

        @Override
        public void onErrorOfMotionPlay(int i) {

        }

        @Override
        public void onPrepareMotion(boolean b, String s, float v) {

        }

        @Override
        public void onCameraOfMotionPlay(String s) {

        }

        @Override
        public void onGetCameraPose(float v, float v1, float v2, float v3, float v4, float v5, float v6, float v7, float v8, float v9, float v10, float v11) {

        }

        @Override
        public void onTouchEvent(int i, int i1) {

        }

        @Override
        public void onPIREvent(int i) {

        }

        @Override
        public void onTap(int i) {

        }

        @Override
        public void onLongPress(int i) {

        }

        @Override
        public void onWindowSurfaceReady() {

        }

        @Override
        public void onWindowSurfaceDestroy() {

        }

        @Override
        public void onTouchEyes(int i, int i1) {

        }

        @Override
        public void onRawTouch(int i, int i1, int i2) {

        }

        @Override
        public void onFaceSpeaker(float v) {

        }

        @Override
        public void onActionEvent(int i, int i1) {

        }

        @Override
        public void onDropSensorEvent(int i) {

        }

        @Override
        public void onMotorErrorEvent(int i, int i1) {

        }
    };

    UnityFaceCallback mUnityFaceCallback = new UnityFaceCallback(){
        @Override
        public void on_touch_left_eye() {
            Log.d("FaceControl", "on_touch_left_eye()");
        }

        @Override
        public void on_touch_right_eye() {
            Log.d("FaceControl", "on_touch_right_eye()");
        }

        @Override
        public void on_touch_nose() {
            Log.d("FaceControl", "on_touch_nose()");
        }

        @Override
        public void on_touch_mouth() {
            Log.d("FaceControl", "on_touch_mouth()");
        }

        @Override
        public void on_touch_head() {
            Log.d("FaceControl", "on_touch_head()");
        }

        @Override
        public void on_touch_left_edge() {
            Log.d("FaceControl", "on_touch_left_edge()");
        }

        @Override
        public void on_touch_right_edge() {
            Log.d("FaceControl", "on_touch_right_edge()");
        }

        @Override
        public void on_touch_bottom() {
            Log.d("FaceControl", "on_touch_bottom()");
        }

    };
}