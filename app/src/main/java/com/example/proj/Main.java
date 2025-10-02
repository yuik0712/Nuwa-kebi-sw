package com.example.proj;
// 메인 클래스 (ListView 항목 클릭 이벤트 처리 인터페이스 구현)
public class Main extends AppComponentFactory implements AdapterView.OnItemClickListener {
    private static final String TAG = "Main";

    // Activity 기본 생명주기 호출 및 레이아웃 설정
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // main.xml 레이아웃 화면 설정
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