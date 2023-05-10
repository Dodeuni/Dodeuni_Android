package dodeunifront.dodeuni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dodeunifront.dodeuni.community.CommunityFragment;

public class MainActivity extends AppCompatActivity {
    final String TAG = this.getClass().getSimpleName();

    FrameLayout home;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); //객체 정의
        SettingListener(); //리스너 등록

        bottomNavigationView.setSelectedItemId(R.id.menu_community);
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
    }
}