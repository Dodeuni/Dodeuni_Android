package dodeunifront.dodeuni.Hue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import dodeunifront.dodeuni.ErrorModel;
import dodeunifront.dodeuni.MainActivity;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.DetailCommunityActivity;
import dodeunifront.dodeuni.community.PostcommunityAPI;
import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HueActivity extends AppCompatActivity {
    Long userId;
    RecyclerView recyclerView;
    HueAdapter hueAdapter;
    EditText et_send;
    Button btn_send;
    private ArrayList<HuePostDTO> huearrayList;
    Dialog dialogmusic;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HueAPI.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    Retrofit retrofitfeel = new Retrofit.Builder()
//            .client(okHttpClient)
            .baseUrl(HuefeelAPI.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    HueAPI _hueAPI = retrofit.create(HueAPI.class);
    HuefeelAPI mRetrofitAPI = retrofitfeel.create(HuefeelAPI.class);
    retrofit2.Call<String> mCallAIReply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hue);

        userId = getIntent().getLongExtra("userId",-1);
        Toolbar toolbar = findViewById(R.id.hue_toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setTitle("");

        dialogmusic = new Dialog(HueActivity.this);
        dialogmusic.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogmusic.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogmusic.setContentView(R.layout.dialog_musichue);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.rv_hue);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);

        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ( bottom <= oldBottom) {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(bottom);
                        }
                    }, 100);
                } }
        });

        huearrayList = new ArrayList<>();
        hueAdapter = new HueAdapter(huearrayList);
        recyclerView.setAdapter(hueAdapter);

        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("hh:mm:ss");

        _hueAPI.getDataHue().enqueue(new Callback<List<HyuResponseDTO>>() {
            @Override
            public void onResponse(Call<List<HyuResponseDTO>> call, Response<List<HyuResponseDTO>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        for (int i = 0; i < response.body().size(); i++) {
                            Log.e("userid",response.body().get(i).getUid()+"");
                            Log.e("content",response.body().get(i).getContent()+"");
                            String dd = response.body().get(i).getCreatedDateTime();
                            String ddp = dd.substring(dd.indexOf("T")+1,dd.indexOf("."));

                            int viewType = 0;
                            if(response.body().get(i).getUid() == userId){
                                viewType = ViewType.RIGHT_CHAT;
                            } else{
                                viewType = ViewType.LEFT_CHAT;
                            }
                            HuePostDTO huePostDTO = new HuePostDTO(response.body().get(i).getContent(),
                                    ddp,viewType);
                            huearrayList.add(huePostDTO);

                            hueAdapter.notifyItemInserted(0);
                            hueAdapter.notifyDataSetChanged();

                        }
                    } else{
                        Log.e("데이터가 비었음","");
                    }
                }else{
                    Log.e("response.isnot성공","");

                }
            }

            @Override
            public void onFailure(Call<List<HyuResponseDTO>> call, Throwable t) {
                Log.e("response :   ",t.toString());

            }
        });


        et_send = (EditText)findViewById(R.id.et_hue_send);
        btn_send = (Button) findViewById(R.id.btn_hue_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_send.getText().length() !=0)
                {
                    String text = et_send.getText().toString();
                    long mNow = System.currentTimeMillis();
                    Date mDate = new Date(mNow);
                    SimpleDateFormat mFormat = new SimpleDateFormat("hh:mm:ss");
                    String time = mFormat.format(mDate);
                    HuePostDTO huePostDTO = new HuePostDTO(text,time,ViewType.RIGHT_CHAT);
                    huearrayList.add(huePostDTO);

                    hueAdapter.notifyItemInserted(0);
                    hueAdapter.notifyDataSetChanged();

                    calltodo(text);
                    HuePostDTO huePostDTO5 = new HuePostDTO(text,userId);
                    _hueAPI.postDataActive(huePostDTO5).enqueue(new Callback<List<HyuResponseDTO>>() {
                        @Override
                        public void onResponse(Call<List<HyuResponseDTO>> call, Response<List<HyuResponseDTO>> response) {
                            if (response.isSuccessful()) {
                                List<HyuResponseDTO> body = response.body();
                                if (body != null) {
                                }
                            } else {
                                //실패
                                Log.e("YMC", "stringToJson msg: 실패" + response.code());
                            }

                        }

                        @Override
                        public void onFailure(Call<List<HyuResponseDTO>> call, Throwable t) {

                        }
                    });
                    HuefeelAPI huefeelAPI = retrofitfeel.create(HuefeelAPI.class);

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"입력해주세요",Toast.LENGTH_SHORT);
                    toast.show();
                }
            et_send.setText("");
            }
        });
    }

    retrofit2.Callback<String> mRtrofitCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            Log.d("testt",response.body());
            showdialogmusic(response.body());

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            t.printStackTrace();
            Log.e("error",t.toString());
        }
    };
    public void calltodo(String text){
        mCallAIReply = mRetrofitAPI.getfeeling(text);
        mCallAIReply.enqueue(mRtrofitCallback);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void showdialogmusic(String text){
        dialogmusic.show(); // 다이얼로그 띄우기
        String[] randomtxt = new String[]{};
        TextView recotv = dialogmusic.findViewById(R.id.tv_recommend_music);
        switch(text){
            case "행복"   :
                randomtxt = getResources().getStringArray(R.array.행복);
            case "공포"   :
                randomtxt = getResources().getStringArray(R.array.공포);
            case "슬픔"   :
                randomtxt = getResources().getStringArray(R.array.슬픔);
            case "혐오"   :
                randomtxt = getResources().getStringArray(R.array.혐오);
            case "놀람"   :
                randomtxt = getResources().getStringArray(R.array.놀람);
            case "분노"   :
                randomtxt = getResources().getStringArray(R.array.분노);
            case "중립"   :
                randomtxt = getResources().getStringArray(R.array.중립);
        }
        Random random = new Random();
        int n = random.nextInt(randomtxt.length-1);
        recotv.setText(randomtxt[n]);
        Button noBtn = dialogmusic.findViewById(R.id.btn_play_no);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogmusic.dismiss();
            }
        });
        dialogmusic.findViewById(R.id.btn_play_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                // 아마 유튜브링크 연결 예정임
                dialogmusic.dismiss();
                finish();           // 앱 종료
            }
        });
    }

}