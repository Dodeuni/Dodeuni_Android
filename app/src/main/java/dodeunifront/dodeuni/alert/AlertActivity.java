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

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.map.adapter.AlertRecyclerAdapter;
import dodeunifront.dodeuni.map.api.AlertAPI;
import dodeunifront.dodeuni.map.dto.response.ResponseAlertListDTO;
import dodeunifront.dodeuni.map.view.ReviewDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlertActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    AlertRecyclerAdapter mAlertRecyclerAdapter;
    ResponseAlertListDTO alertListResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

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
            Intent intent = new Intent(this, ReviewDetailActivity.class);
            intent.putExtra("id", alert.getId());
            startActivity(intent);
            Toast.makeText(this, "clicked: " + alert.getAlarm(), Toast.LENGTH_LONG).show();
        });
    }

    public void getAlertList(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AlertAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        AlertAPI alertAPI = retrofit.create(AlertAPI.class);
        alertAPI.getAlert(1).enqueue(new Callback<ResponseAlertListDTO>() {
            @Override
            public void onResponse(Call<ResponseAlertListDTO> call, Response<ResponseAlertListDTO> response) {
                if (response.body().getLength() != 0) {
                    alertListResult = response.body();
                    initRecyclerView();
                    //Log.d("성공", alertListResult.getAlertList().get(0).getAlarm());
                } else {
                    Log.d("ALERT: No Result", "검색 결과 없음");
                }
            }
            @Override
            public void onFailure(Call<ResponseAlertListDTO> call, Throwable t) {
                Log.d("실패","통신 실패: "+ t.getMessage());
            }
        });
    }
}