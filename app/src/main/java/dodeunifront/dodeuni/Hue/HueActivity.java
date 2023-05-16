package dodeunifront.dodeuni.Hue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dodeunifront.dodeuni.MainActivity;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.API_Postcommunity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HueActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    HueAdapter adapter;
    ImsiAdapter imsiAdapter;
    EditText et_send;
    Button btn_send;
    private ArrayList<HueData> huearrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hue);

        Toolbar toolbar = findViewById(R.id.hue_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setTitle("");

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
        //adapter = new HueAdapter(huearrayList);
        imsiAdapter = new ImsiAdapter(huearrayList);
        recyclerView.setAdapter(imsiAdapter);



        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("hh:mm:ss");

        //임시 상대방 휴 메세지값 넣어보기
        String imsi = "아 놀고싶다";
        String imsi0 = "피곤쓰~~~~";
        String imsi1= "지금 나는 엽떡이 먹고싶다";
        String imsi2 = "잠이 너무 부족해서 머리가 아파 힘들어";
        String imsi3 = "여행가고싶다 떠나고싶어라";
        String timea = mFormat.format(mDate);
        HueData hueData4 = new HueData(imsi0,timea,ViewType.LEFT_CHAT);
        HueData hueData0 = new HueData(imsi,timea,ViewType.LEFT_CHAT);
        HueData hueData1 = new HueData(imsi2,timea, ViewType.LEFT_CHAT);
        HueData hueData2 = new HueData(imsi1,timea,ViewType.LEFT_CHAT);
        HueData hueData3 = new HueData(imsi3,timea,ViewType.LEFT_CHAT);
        huearrayList.add(hueData0);
        huearrayList.add(hueData1);
        huearrayList.add(hueData2);
        huearrayList.add(hueData3);
        huearrayList.add(hueData4);

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
                    HueData hueData = new HueData(text,time,ViewType.RIGHT_CHAT);
                    huearrayList.add(hueData);

                    imsiAdapter.notifyItemInserted(0);
                    imsiAdapter.notifyDataSetChanged();

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_Hyu.URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    API_Hyu api_hyu = retrofit.create(API_Hyu.class);
                    HueData hueData5 = new HueData(text,Long.valueOf(1234));
                    api_hyu.postDataActive(hueData5).enqueue(new Callback<List<HyuResponseDto>>() {
                        @Override
                        public void onResponse(Call<List<HyuResponseDto>> call, Response<List<HyuResponseDto>> response) {
                            if (response.isSuccessful()) {
                                List<HyuResponseDto> body = response.body();
                                if (body != null) {
                                }
                            } else {
                                //실패
                                Log.e("YMC", "stringToJson msg: 실패" + response.code());
                            }

                        }

                        @Override
                        public void onFailure(Call<List<HyuResponseDto>> call, Throwable t) {

                        }
                    });
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"입력해주세요",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
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

}