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
}