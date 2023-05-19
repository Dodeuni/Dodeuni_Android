package dodeunifront.dodeuni.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.profile.NidProfileCallback;
import com.navercorp.nid.profile.data.NidProfileResponse;

import dodeunifront.dodeuni.Hue.HueAPI;
import dodeunifront.dodeuni.MainActivity;
import dodeunifront.dodeuni.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button naverlogin;

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

        setContentView(R.layout.activity_login);
        String naverClientId = getString(R.string.naver_client_id);
        String naverClientSecret = getString(R.string.naver_client_secret);
        String naverClientName = getString(R.string.naver_client_name);
        NaverIdLoginSDK.INSTANCE.initialize(this, naverClientId, naverClientSecret , naverClientName);

        naverlogin = (Button) findViewById(R.id.btn_naverlogin);
        naverlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNaverLogin();
            }
        });
        setLayoutState(false,null);

    }
    public void startNaverLogin(){
        final String[] naverToken = {""};
        String[] values ={"","","",""};

        NidProfileCallback<NidProfileResponse> profileCallback = new NidProfileCallback<NidProfileResponse>() {
            @Override
            public void onSuccess(NidProfileResponse nidProfileResponse) {
                String userId = nidProfileResponse.getProfile().getId();
                String name = nidProfileResponse.getProfile().getName().toString();
                String email = nidProfileResponse.getProfile().getEmail().toString();
                values[0] = userId;
                values[1] = name;
                values[2] = email;
                values[3] = naverToken[0];
                setLayoutState(true,values);
                Toast.makeText(getApplicationContext(), "네이버 아이디 로그인 성공!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, @NonNull String s) {
                String errorCode = NaverIdLoginSDK.INSTANCE.getLastErrorCode().getCode().toString();
                String errorDescription = NaverIdLoginSDK.INSTANCE.getLastErrorDescription().toString();
                Toast.makeText(getApplicationContext(), "errorCode: "+errorCode+"\n" +
                        "errorDescription: "+errorDescription, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int i, @NonNull String s) {
                onFailure(i, s);
            }
        };
        OAuthLoginCallback oAuthLoginCallback = new OAuthLoginCallback() {
            @Override
            public void onSuccess() {
                naverToken[0] = NaverIdLoginSDK.INSTANCE.getAccessToken();
                NidOAuthLogin nidOAuthLogin = new NidOAuthLogin();
                nidOAuthLogin.callProfileApi(profileCallback);
            }

            @Override
            public void onFailure(int i, @NonNull String s) {
                String errorCode = NaverIdLoginSDK.INSTANCE.getLastErrorCode().getCode().toString();
                String errorDescription = NaverIdLoginSDK.INSTANCE.getLastErrorDescription().toString();
                Toast.makeText(getApplicationContext(), "errorCode: "+errorCode+"\n" +
                        "errorDescription: "+errorDescription, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(int i, @NonNull String s) {
                onFailure(i, s);
            }
        };
        NaverIdLoginSDK.INSTANCE.authenticate(this, oAuthLoginCallback);
    }
    public void setLayoutState(boolean login,String[] strings){
        if(login){
            String userId;
            String name;
            String email;
            String naverToken;
            name = strings[1];
            email = strings[2];
            LoginAPI loginAPI = retrofit.create(LoginAPI.class);
            UserSaveRequestDTO userSaveRequestDTO = new UserSaveRequestDTO(email,name);
            loginAPI.postDatalogin(userSaveRequestDTO).enqueue(new Callback<TokenDTO>() {
                @Override
                public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                    if (response.isSuccessful()){
                        if (response.body()!=null){
                            Log.e("회원 id",response.body().getId()+"");
                            Log.e("회원 nickname",response.body().getNickname()+"");
                            Log.e("회원 email",response.body().getEmail()+"");
                            Log.e("회원 token",response.body().getToken()+"");

                            Long userId = response.body().getId();
                            String token = response.body().getToken();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("userId",userId);
                            intent.putExtra("name",name);
                            intent.putExtra("email",email);
                            intent.putExtra("naverToken",token);
                            startActivity(intent);
                        }
                    }
                    else{
                        Log.e("로그인 응답에 실패하였습니다","");
                    }
                }

                @Override
                public void onFailure(Call<TokenDTO> call, Throwable t) {
                    Log.e("통신실패",t.toString());

                }
            });
        }else{

        }
    }
}