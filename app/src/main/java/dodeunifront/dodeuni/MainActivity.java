package dodeunifront.dodeuni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;

import dodeunifront.dodeuni.alert.AlertActivity;
import dodeunifront.dodeuni.hue.HueAPI;
import dodeunifront.dodeuni.hue.HueActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dodeunifront.dodeuni.community.CommunityFragment;
import dodeunifront.dodeuni.map.CurrentLocation;
import dodeunifront.dodeuni.map.view.MapFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.util.maps.helper.Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dodeunifront.dodeuni.mypage.MypageFragment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    final String TAG = this.getClass().getSimpleName();
    FrameLayout home;
    BottomNavigationView bottomNavigationView;
    Long userId;
    String name;
    String email;
    String naverToken;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HueAPI.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLocationPermission();
        Log.i("HASH", "Key Hash: " + getKeyHashBase64(this));
        init(); //객체 정의
        SettingListener(); //리스너 등록
        Intent intent = getIntent();
        if(intent.getStringExtra("flag").equals("newuser")){
            userId = intent.getLongExtra("userId",-1);
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            naverToken = intent.getStringExtra("naverToken");
            Log.e("새로운회원","id:"+userId+"\ntoken:"+ naverToken +"\nname:"+ name+"\nemail:"+email);
        } else if(intent.getStringExtra("flag").equals("olduser")){
            Long local_uid = intent.getLongExtra("local_uid",-1);
            userId = intent.getLongExtra("userId",-1);
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            Log.e("새로운회원","id:"+userId+"\nname:"+ name+"\nemail:"+email);

//            LoginAPI loginAPI = retrofit.create(LoginAPI.class);
//            loginAPI.getDatalogin(local_uid).enqueue(new Callback<UserResponseDTO>() {
//                @Override
//                public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {
//                    if (response.isSuccessful()){
//                        if (response.body()!=null){
//                            userId = response.body().getId();
//                            name = response.body().getNickname();
//                            email = response.body().getEmail();
//                            Log.e("기존회원","id:"+userId+"\nname:"+ name+"\nemail:"+email);
//                        } else{ Log.e("성공하못함연결","");}
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UserResponseDTO> call, Throwable t) {
//                    Log.e("통신에러",t.toString());
//
//                }
//            });


        }
        init(); //객체 정의
        SettingListener(); //리스너 등록
        Toolbar toolbar = findViewById (R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
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
                    FragmentManager fragmentManager= getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    Bundle bundle = new Bundle();
                    bundle.putLong("userId",userId);
                    bundle.putString("nickname",name);
                    CommunityFragment communityFragment = new CommunityFragment();
                    communityFragment.setArguments(bundle);
                    transaction.replace(R.id.content_layout,communityFragment).commit();
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.content_layout, new CommunityFragment())
//                            .commit();
                    return true;
                }
                case R.id.menu_location: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_layout, new MapFragment())  //임시
                            .commit();
                    return true;
                }
                case R.id.menu_mypage: {
                    FragmentManager fragmentManager= getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    Bundle bundle = new Bundle();
                    bundle.putLong("userId",userId);
                    bundle.putString("nickname",name);
                    bundle.putString("email",email);
                    MypageFragment mypageFragment = new MypageFragment();
                    mypageFragment.setArguments(bundle);
                    transaction.replace(R.id.content_layout,mypageFragment).commit();

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
                Intent intent1 = new Intent(this,HueActivity.class);
                intent1.putExtra("userId",userId);
                startActivity (intent1);
                return true;
            case R.id.menu_alarm:
                Intent intent2 = new Intent(this, AlertActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public String getKeyHashBase64(Context context) {
        PackageInfo packageInfo = Utility.getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.DEFAULT);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setLocationPermission(){
        final LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        CurrentLocation currentLocation = new CurrentLocation(lm, this);
        currentLocation.setPermission();
    }
}