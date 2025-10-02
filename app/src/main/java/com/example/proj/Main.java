package com.example.proj;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.AppComponentFactory;

import com.example.util.AIPermissionRequest;
import com.example.util.ActivityBean;
import com.example.util.ActivityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        // ListView 데이터 로딩 및 어댑터 설정
        ListView listView = (ListView) findViewById(R.id.activity_list);
        ArrayList<HashMap<String, Object>> listItems = new ArrayList<HashMap<String, Object>>();

        // XML 파일("cfg_functions.xml")에서 실행할 Activity 목록을 불러옴
        ArrayList<ActivityBean> activityArrayList = ActivityUtils.pullXML(getApplicationContext(), "cfg_functions.xml");

        // 불러온 목록을 ListView에 표시할 데이터 구조로 변환
        for (ActivityBean activityBean : activityArrayList) {
            HashMap<String, Object> item = new HashMap<>(); // 반복문 안에서 초기화
            try {
                item.put("activity_name", activityBean.getLabel()); // 목록에 표시될 이름
                // Activity 클래스 이름을 Class 객체로 변환하여 저장 (Activity 실행 사용)
                item.put("activity_class", Class.forName(getPackageName() + "." + activityBean.getName()));
            } catch (ClassNotFoundException e) {
                // 해당 Activity 클래스를 찾을 수 없을 경우 에러 로그 출력
                Log.e(TAG, "Activity class not found: " + activityBean.getName(), e);
                continue; // 다음 항목으로 넘어감
            }
            listItems.add(item);
        }

        // ListView 어댑터 생성 (SimpleAdapter는 HashMap 데이터를 View 연결)
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"activity_name"}, new int[]{R.id.text_item});

        listView.setAdapter(adapter); // 목록 표시
        listView.setDividerHeight(2); // 목록 항목 간 구분선 높이 설정

        listView.setOnItemClickListener(this); // 목록 항목 클릭 이벤트 리스너를 현재 클래스로 지정
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

    // AdapterView 인터페이스 구현
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 클릭된 항목에 저장된 데이터를 가져옴
        Map<?, ?> map = (HashMap<?, ?>) parent.getAdapter().getItem(position);

        // 저장해 둔 다음 Activity의 Class 객체 추출
        Class<?> clazz = (Class<?>) map.get("activity_class");

        // Intent를 생성하여 추출된 Class (Activity) 목표로 설정하고 실행
        Intent it = new Intent(this, clazz);
        this.startActivity(it);
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