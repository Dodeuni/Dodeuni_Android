package dodeunifront.dodeuni.alert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import dodeunifront.dodeuni.LandingActivity;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.community.DetailCommunityActivity;
import dodeunifront.dodeuni.map.dto.AlertDTO;
import dodeunifront.dodeuni.retroifit.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlertActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    AlertRecyclerAdapter mAlertRecyclerAdapter;
    List<AlertDTO> alertListResult;
    RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        mRecyclerView = findViewById(R.id.rv_alarm_list);

        initTopView();
        getAlertList();
    }

    public void initTopView(){
        TopView topView = findViewById(R.id.topview_alert);
        topView.setOnButtonClickListener(() -> finish());
    }

    public void initRecyclerView(){
        mAlertRecyclerAdapter = new AlertRecyclerAdapter();
        mRecyclerView.setAdapter(mAlertRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mAlertRecyclerAdapter.setAlertList(alertListResult);
        mAlertRecyclerAdapter.setOnItemClickListener((alert) -> {
            Intent intent = new Intent(this, DetailCommunityActivity.class);
            intent.putExtra("id", alert.getId());
            startActivity(intent);
        });
    }

    public void getAlertList(){
        retrofitBuilder.getAlertAPI().getAlert(LandingActivity.localUid).enqueue(new Callback<List<AlertDTO>>() {
            @Override
            public void onResponse(Call<List<AlertDTO>> call, Response<List<AlertDTO>> response) {
                if (response.body() != null) {
                    alertListResult = response.body();
                    initRecyclerView();
                } else {
                    Toast.makeText(getApplicationContext(), "알립 없음", Toast.LENGTH_LONG).show();
                    Log.d("ALERT: No Result", "검색 결과 없음");
                }
            }
            @Override
            public void onFailure(Call<List<AlertDTO>> call, Throwable t) {
                Log.d("실패","통신 실패: "+ t.getMessage());
            }
        });
    }
}