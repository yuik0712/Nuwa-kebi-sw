package com.example.proj.activity;

public class FaceControlActivity extends BaseAppCompatActivity {
    private final static String TAG = "FaceControlExampleActivity";

    // UI 요소 선언
    ImageButton mCloseBtn;
    Button mBtn_start ;

    // Activity 상수 정의
    private static final String TTS_SAMPLE = "Kebbi is speaking with face" ;
    private static final long FACE_MOUTH_SPEED = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facecontrol_show);

        // 닫기 버튼 초기화 및 종료 로직
        mCloseBtn = findViewById(R.id.imgbtn_quit);
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 시작 버튼 초기화 (로직은 나중에 추가됨)
        mBtn_start = findViewById(R.id.btn_start);

    }

    // BaseAppCompatActivity 추상 메서드 구현
    @Override
    protected int getLayoutRes(){
        return R.layout.activity_disablepowerkey;
    }

    @Override
    protected int getToolBarTitleRes(){
        return R.string.disablepowerkey_sdk_example_title;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 시스템 UI 숨김
        hideSystemUi();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

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