package com.example.proj;

import android.view.MenuItem;
import android.widget.Toolbar;

// 메인 클래스 (ListView 항목 클릭 이벤트 처리 인터페이스 구현)
public class Main extends AppComponentFactory implements AdapterView.OnItemClickListener {
    private static final String TAG = "Main";

    // Activity 기본 생명주기 호출 및 레이아웃 설정
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // main.xml 레이아웃 화면 설정

        // 툴바(Toolbar) 설정 및 메뉴 항목 처리 기능
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // getSupportActionBar()가 null이 아닐 때만 호출
        if (setSupportActionBar() != null) {
            getSupportActionBar.setDisplayHomeAsUpEnabled(true); // 툴바 뒤로가기 화살표(홈버튼)
        }

        // 런타임 요청 객체 초기화
        mPermissionRequest = new APIPermissionRequest();

        // 필수 권한(전화상태, 오디오 녹음, 외부 저장소 쓰기)이 부여되었는지 체크
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestMulti(); // 권한 요청 함수 호출
            Log.d(TAG, "request all needed permissions");
        }
    }

    // 툴바 메뉴 항목이 선택 되었을 때 호출
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // 툴바의 '홈'버튼 (뒤로가기) 클릭 시
                finish(); // 현재 Activity 종료하고 이전 화면으로 돌아감
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 기본 구조 설정 및 Activity 생명주기 메서드 오버라이드
    // 뒤로가기 버튼이 눌렸을 때 호출
    @Override
    public void onBackPressed() {
        finish(); // 현재 Activity 종료
        super.onBackPressed();
    }

    // Activity가 완전히 파괴될 때 호출
    @Override
    public void onDestory() {
        super.onDestory();
    }

    // 권한 요청을 수행하는 커스텀 함수
    public void requestMulti() {
        // AIPermissionRequest 객체를 사용하여 여러 권한을 동시 요청
        mPermissionRequest.requestMultiPermissions(this,mPermissionGrant);
    }

    // 권한 요청 결과(콜백)를 처리하는 리스너 객체
    private AIPermissionRequest.PermissionGrant mPermissionGrant = new AIPermissionRequest.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            // 권한 요청이 승인되었을 때 호출됨
            // 성공 토스트 메시지 출력 (디버깅용)
            String permissionName = "";
            switch (requestCode) {
                case AIPermissionRequest.CODE_READ_PHONE_STATE:
                    permissionName = "전화 상태"; break;
                case AIPermissionRequest.CODE_RECORD_AUDIO:
                    permissionName = "오디오 녹음"; break;
                case AIPermissionRequest.CODE_WRITE_EXTERNAL_STORAGE:
                    permissionName = "외부 저장소 쓰기"; break;
                default:
                    permissionName = "알 수 없는 권한"; break;
            }
            Toast.makeText(Main.this, "권한 승인 완료: " + permissionName, Toast.LENGTH_SHORT).show();
        }
    };
}