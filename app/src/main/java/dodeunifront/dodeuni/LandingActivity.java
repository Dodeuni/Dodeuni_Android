package dodeunifront.dodeuni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dodeunifront.dodeuni.hue.HueAPI;
import dodeunifront.dodeuni.login.LoginActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingActivity extends AppCompatActivity {
    ImageView imageView;
    public static Long localUid;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HueAPI.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        imageView = (ImageView)findViewById(R.id.iv_landing_logo);
        Animation ani= AnimationUtils.loadAnimation(this,R.anim.appear);
        imageView.startAnimation(ani);
        Handler handler = new Handler();
        SharedPreferences sharedPreferencesLogin = this.getSharedPreferences("autoLogin",MODE_PRIVATE);
        localUid=sharedPreferencesLogin.getLong("MY_ID",-1);
        DodeuniFirebaseMessagingService.getToken(localUid);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(local_uid!=-1) {
//                    LoginAPI loginAPI = retrofit.create(LoginAPI.class);
//                    loginAPI.getDatalogin(local_uid).enqueue(new Callback<UserResponseDTO>() {
//                        @Override
//                        public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {
//                            if (response.isSuccessful()){
//                                if (response.body()!=null){
//
//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    intent.putExtra("userId",response.body().getId());
//                                    intent.putExtra("name",response.body().getNickname());
//                                    intent.putExtra("email",response.body().getEmail());
//
//                                    intent.putExtra("flag","olduser");
//                                    intent.putExtra("local_uid",local_uid);
//                                    startActivity(intent);
//                                    finish();
//                                    //Log.e("기존회원","id:"+userId+"\nname:"+ name+"\nemail:"+email);
//                                } else{ Log.e("성공하못함연결","");}
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<UserResponseDTO> call, Throwable t) {
//                            Log.e("통신에러",t.toString());
//
//                        }
//                    });
//
//                }
//                else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();}
//            }
        },1000); // 1초 있다 메인액티비티로
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}