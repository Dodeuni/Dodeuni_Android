package dodeunifront.dodeuni.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dodeunifront.dodeuni.LocationDetailView;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.map.api.LocationAPI;
import dodeunifront.dodeuni.map.api.ReviewAPI;
import dodeunifront.dodeuni.map.dto.request.RequestEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.request.RequestEnrollReviewDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseReviewDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationPostActivity extends AppCompatActivity {
    RequestEnrollLocationDTO LocationData = new RequestEnrollLocationDTO();
    RequestEnrollReviewDTO reviewData = new RequestEnrollReviewDTO();
    ResponseEnrollLocationDTO locationResultData;
    ResponseReviewDTO reviewResultData;
    LocationDetailView locationDetailView;
    EditText editTitle, editContent;
    CardView enrollBtn;
    String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_location);
        locationDetailView = findViewById(R.id.ldview_post_location);
        editTitle = findViewById(R.id.edit_post_location_title);
        editContent = findViewById(R.id.edit_post_location_content);
        enrollBtn = findViewById(R.id.cv_location_enroll);

        initLocationData();
        initTopView();

        enrollBtn.setOnClickListener(view -> {
            postLocation();
        });
    }

    private void initLocationData(){
        Intent intent = getIntent();
        System.out.println(intent.getStringExtra("name"));
        LocationData.setPlaceName(intent.getStringExtra("name"));
        LocationData.setCategory(intent.getStringExtra("category"));
        LocationData.setAddress(intent.getStringExtra("address"));
        LocationData.setPhone(intent.getStringExtra("phone"));
        LocationData.setX(intent.getStringExtra("x"));
        LocationData.setY(intent.getStringExtra("y"));
        LocationData.setUid(1);

        locationDetailView.setName(LocationData.getPlaceName());
        locationDetailView.setCategory(LocationData.getCategory());
        locationDetailView.setAddress(LocationData.getAddress());
        locationDetailView.setCategory(LocationData.getPhone());
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
                .baseUrl(LocationAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LocationAPI locationAPI = retrofit.create(LocationAPI.class);

        title = editTitle.getText().toString();
        content = editContent.getText().toString();

        if(title != ""  && content != "") {
            reviewData.setTitle(title);
            reviewData.setContent(content);
            locationAPI.postLocation(LocationData).enqueue(new Callback<ResponseEnrollLocationDTO>() {
                @Override
                public void onResponse(Call<ResponseEnrollLocationDTO> call, Response<ResponseEnrollLocationDTO> response) {
                    if (response.body()!=null) {
                        locationResultData = response.body();
                        reviewData.setPid(locationResultData.getId());
                        postReview();
                        finish();
                    } else {
                        Log.d("성공", "데이터없음");
                    }
                }

                @Override
                public void onFailure(Call<ResponseEnrollLocationDTO> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_LONG).show();
                    Log.d("실패", "통신 실패: " + t.getMessage());
                }
            });
        }
    }

    public void postReview(){
        reviewData.setUid(1);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ReviewAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ReviewAPI reviewAPI = retrofit.create(ReviewAPI.class);
        reviewAPI.postReview(reviewData).enqueue(new Callback<ResponseReviewDTO>() {
            @Override
            public void onResponse(Call<ResponseReviewDTO> call, Response<ResponseReviewDTO> response) {
                if (response.body() != null) {
                    reviewResultData = response.body();
                    Intent intent = new Intent(getApplicationContext(), LocationDetailActivity.class);
                    intent.putExtra("id", reviewResultData.getPid());
                    startActivity(intent);
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