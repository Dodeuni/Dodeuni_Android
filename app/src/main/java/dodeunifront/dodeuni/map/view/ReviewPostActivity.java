package dodeunifront.dodeuni.map.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dodeunifront.dodeuni.LandingActivity;
import dodeunifront.dodeuni.LocationDetailView;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.map.api.LocationAPI;
import dodeunifront.dodeuni.map.api.ReviewAPI;
import dodeunifront.dodeuni.map.dto.request.RequestEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.request.RequestEnrollReviewDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseDetailLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseReviewDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewPostActivity extends AppCompatActivity {

    ResponseDetailLocationDTO locationData;
    RequestEnrollReviewDTO reviewData = new RequestEnrollReviewDTO();
    ResponseReviewDTO reviewResultData;
    LocationDetailView locationDetailView;
    EditText editTitle, editContent;
    CardView enrollBtn;
    String title, content;
    int locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_location);
        locationDetailView = findViewById(R.id.ldview_post_location);
        editTitle = findViewById(R.id.edit_post_location_title);
        editContent = findViewById(R.id.edit_post_location_content);
        enrollBtn = findViewById(R.id.cv_location_enroll);

        Intent intent = getIntent();
        locationId = intent.getIntExtra("pid", -1);

        initTopView();

        if(locationId != -1) {
            getLocationData();
        } else {
            finish();
            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_LONG).show();
        }

        enrollBtn.setOnClickListener(view -> {
            postLocation();
        });
    }

    private void initLocationDetailView(){
        locationDetailView.setName(locationData.getName());
        locationDetailView.setCategory(locationData.getCategory());
        locationDetailView.setAddress(locationData.getAddress());
        locationDetailView.setContact(locationData.getContact());
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

    public void initTopView(){
        TopView topView = findViewById(R.id.topview_post_location);
        topView.setOnButtonClickListener(() -> finish());
    }

    public void postLocation(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ReviewAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        title = editTitle.getText().toString();
        content = editContent.getText().toString();

        if(title != ""  && content != "") {
            reviewData.setUid(LandingActivity.localUid);
            reviewData.setTitle(title);
            reviewData.setContent(content);
            reviewData.setPid(locationId);
            ReviewAPI reviewAPI = retrofit.create(ReviewAPI.class);
            reviewAPI.postReview(reviewData).enqueue(new Callback<ResponseReviewDTO>() {
                @Override
                public void onResponse(Call<ResponseReviewDTO> call, Response<ResponseReviewDTO> response) {
                    if (response.body() != null) {
                        reviewResultData = response.body();
                        finish();
                    } else {
                        Log.d("성공", "데이터없음");
                    }
                }

                @Override
                public void onFailure(Call<ResponseReviewDTO> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_LONG).show();
                    Log.d("실패", "통신 실패: " + t.getMessage());
                }
            });
        }
    }
}