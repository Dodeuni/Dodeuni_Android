package dodeunifront.dodeuni.map.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dodeunifront.dodeuni.LocationDetailView;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.map.adapter.ReviewPreviewRecyclerAdapter;
import dodeunifront.dodeuni.map.api.LocationAPI;
import dodeunifront.dodeuni.map.dto.response.ResponseDetailLocationDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationDetailActivity extends AppCompatActivity {

    ResponseDetailLocationDTO locationData;
    LocationDetailView locationDetailView;
    ReviewPreviewRecyclerAdapter mRecyclerAdapter;
    RecyclerView mRecyclerView;
    TextView tvAddReview, tvCountReview;
    long locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_location);
        locationDetailView = findViewById(R.id.ldview_detail_map);
        mRecyclerView = findViewById(R.id.rv_detail_preview_list);
        tvAddReview = findViewById(R.id.tv_add_review);
        tvCountReview = findViewById(R.id.tv_title_review_list);

        Intent intent = getIntent();
        locationId = intent.getIntExtra("id", -1);

        initTopView();

        tvAddReview.setOnClickListener(view -> setAddReviewBtn());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(locationId != -1) {
            getLocationData();
        } else {
            finish();
            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void initTopView(){
        TopView topView = findViewById(R.id.topview_detail_map);
        topView.setOnButtonClickListener(() -> {
            finish();
        });
    }

    public void initLocationDetailView(){
        locationDetailView.setName(locationData.getName());
        locationDetailView.setCategory(locationData.getCategory());
        locationDetailView.setAddress(locationData.getAddress());
        locationDetailView.setContact(locationData.getContact());
        tvCountReview.setText("리뷰 (" + locationData.getReviewsLength() + ")");
    }

    public void getLocationData(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LocationAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LocationAPI locationAPI = retrofit.create(LocationAPI.class);
        System.out.println(locationId);
        locationAPI.getLocationDetail(locationId).enqueue(new Callback<ResponseDetailLocationDTO>() {
            @Override
            public void onResponse(Call<ResponseDetailLocationDTO> call, Response<ResponseDetailLocationDTO> response) {
                if (response.body() != null) {
                    locationData = response.body();
                    initLocationDetailView();
                    initRecyclerView();
                } else {
                    Log.d("성공", "데이터없음");
                }
            }
            @Override
            public void onFailure(Call<ResponseDetailLocationDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_LONG).show();
                Log.d("실패", "통신 실패: " + t.getMessage());
            }
        });
    }

    public void initRecyclerView(){
        mRecyclerAdapter = new ReviewPreviewRecyclerAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerAdapter.setReviewResult(locationData.getReviews(), locationData.getReviewsLength());
        mRecyclerAdapter.setOnItemClickListener((locationData) -> {
            Intent intent = new Intent(this, ReviewDetailActivity.class);
            intent.putExtra("id", locationData.getId());
            startActivity(intent);
        });
    }

    public void setAddReviewBtn(){
        Intent intent = new Intent(this, ReviewPostActivity.class);
        intent.putExtra("pid", locationData.getId());
        startActivity(intent);
    }
}