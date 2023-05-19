package dodeunifront.dodeuni.Hue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HueActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    HueAdapter hueAdapter;
    EditText et_send;
    Button btn_send;
    private ArrayList<HuePostDTO> huearrayList;
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
        hueAdapter = new HueAdapter(huearrayList);
        recyclerView.setAdapter(hueAdapter);



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
        HuePostDTO huePostDTO4 = new HuePostDTO(imsi0,timea,ViewType.LEFT_CHAT);
        HuePostDTO huePostDTO0 = new HuePostDTO(imsi,timea,ViewType.LEFT_CHAT);
        HuePostDTO huePostDTO1 = new HuePostDTO(imsi2,timea, ViewType.LEFT_CHAT);
        HuePostDTO huePostDTO2 = new HuePostDTO(imsi1,timea,ViewType.LEFT_CHAT);
        HuePostDTO huePostDTO3 = new HuePostDTO(imsi3,timea,ViewType.LEFT_CHAT);
        huearrayList.add(huePostDTO0);
        huearrayList.add(huePostDTO1);
        huearrayList.add(huePostDTO2);
        huearrayList.add(huePostDTO3);
        huearrayList.add(huePostDTO4);

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

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(HueAPI.URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    HueAPI _hueAPI = retrofit.create(HueAPI.class);
                    HuePostDTO huePostDTO5 = new HuePostDTO(text,Long.valueOf(1234));
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