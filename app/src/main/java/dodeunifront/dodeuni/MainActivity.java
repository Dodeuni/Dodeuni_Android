package dodeunifront.dodeuni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import dodeunifront.dodeuni.Hue.HueActivity;

import androidx.appcompat.widget.Toolbar;



import com.google.android.material.bottomnavigation.BottomNavigationView;

import dodeunifront.dodeuni.community.CommunityFragment;

public class MainActivity extends AppCompatActivity {
    final String TAG = this.getClass().getSimpleName();

    FrameLayout home;
    BottomNavigationView bottomNavigationView;
    Long userId;
    String name;
    String email;
    String naverToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); //객체 정의
        SettingListener(); //리스너 등록
        Toolbar toolbar = findViewById (R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        bottomNavigationView.setSelectedItemId(R.id.menu_community);

        Intent intent = getIntent();
        userId = intent.getLongExtra("userId",-1);
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        naverToken = intent.getStringExtra("naverToken");
        Log.e("","id:"+userId+"\ntoken:"+ naverToken +"\nname:"+ name+"\nemail:"+email);

    }

    private void init() {
        home = findViewById(R.id.content_layout);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
    }
    private void SettingListener() {
        //선택 리스너 등록
        bottomNavigationView.setOnItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_community: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_layout, new CommunityFragment())
                            .commit();
                    return true;
                }
                case R.id.menu_location: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_layout, new CommunityFragment())  //임시
                            .commit();
                    return true;
                }
                case R.id.menu_mypage: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_layout, new CommunityFragment ()) //임시
                            .commit();
                    return true;
                }
            }
            return false;
        }
    }// 메뉴 리소스 XML의 내용을 앱바(App Bar)에 반영
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.top_nav, menu);

        return true;
    }

    //앱바(App Bar)에 표시된 액션 또는 오버플로우 메뉴가 선택되면
    //액티비티의 onOptionsItemSelected() 메서드가 호출
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.menu_hue:
                startActivity (new Intent (this, HueActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }


}