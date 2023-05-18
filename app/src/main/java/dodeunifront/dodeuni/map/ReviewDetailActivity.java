package dodeunifront.dodeuni.map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.map.api.LocationAPI;
import dodeunifront.dodeuni.map.api.ReviewAPI;
import dodeunifront.dodeuni.map.dto.response.ResponseDetailLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseReviewDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewDetailActivity extends AppCompatActivity {

    ResponseReviewDTO reviewData;
    long reviewId;
    TextView tvTitle, tvContent, tvTime, tvNickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_review);
        tvTitle = findViewById(R.id.tv_review_title);
        tvContent = findViewById(R.id.tv_review_content);
        tvTime = findViewById(R.id.tv_review_time);
        tvNickname = findViewById(R.id.tv_review_nickname);

        Intent intent = getIntent();
        reviewId = intent.getIntExtra("id", -1);

        initTopView();

        if(reviewId != -1) {
            getReviewData();
        } else {
            finish();
            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_LONG).show();
        }
    }

    public void initTopView(){
        TopView topView = findViewById(R.id.topview_review);
        topView.setOnButtonClickListener(() -> {
            finish();
        });
    }

    public void getReviewData(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ReviewAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ReviewAPI reviewAPI = retrofit.create(ReviewAPI.class);
        System.out.println(reviewId);
        reviewAPI.getReviewDetail(reviewId).enqueue(new Callback<ResponseReviewDTO>() {
            @Override
            public void onResponse(Call<ResponseReviewDTO> call, Response<ResponseReviewDTO> response) {
                if (response.body() != null) {
                    reviewData = response.body();
                    initTextView();
                } else {
                    Log.d("성공", "데이터 없음");
                }
            }

            @Override
            public void onFailure(Call<ResponseReviewDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_LONG).show();
                Log.d("실패", "통신 실패: " + t.getMessage());
            }
        });
    }

    public void initTextView(){
        tvTitle.setText(reviewData.getTitle());
        tvContent.setText(reviewData.getContent());
        tvTime.setText(reviewData.getCreatedDateTime());
        tvNickname.setText(reviewData.getNickname());
    }
}