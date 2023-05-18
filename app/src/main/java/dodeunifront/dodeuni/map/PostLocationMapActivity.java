package dodeunifront.dodeuni.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.map.api.KakaoMapAPI;
import dodeunifront.dodeuni.map.api.LocationAPI;
import dodeunifront.dodeuni.map.dto.request.RequestEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseEnrollLocationDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostLocationMapActivity extends AppCompatActivity {
    RequestEnrollLocationDTO postLocationData = new RequestEnrollLocationDTO();
    ResponseEnrollLocationDTO locationEnrollResult;
    TextView tvName, tvCate, tvAddress, tvPhone;
    EditText editTitle, editContent;
    CardView enrollBtn;
    String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_location_map);
        tvName = findViewById(R.id.tv_post_location_name);
        tvCate = findViewById(R.id.tv_post_location_category);
        tvAddress = findViewById(R.id.tv_post_location_address);
        tvPhone = findViewById(R.id.tv_post_location_phone);
        editTitle = findViewById(R.id.edit_post_location_title);
        editContent = findViewById(R.id.edit_post_location_content);
        enrollBtn = findViewById(R.id.cv_location_enroll);

        initLocationData();
        initTopView();

        setEnrollBtn();
    }

    private void initLocationData(){
        Intent intent = getIntent();
        System.out.println(intent.getStringExtra("name"));
        postLocationData.setPlaceName(intent.getStringExtra("name"));
        postLocationData.setCategory(intent.getStringExtra("category"));
        postLocationData.setAddress(intent.getStringExtra("address"));
        postLocationData.setPhone(intent.getStringExtra("phone"));
        postLocationData.setX(intent.getStringExtra("x"));
        postLocationData.setY(intent.getStringExtra("y"));
        postLocationData.setUid(1);

        tvName.setText(postLocationData.getPlaceName());
        tvCate.setText(postLocationData.getCategory());
        tvAddress.setText(postLocationData.getAddress());
        tvPhone.setText(postLocationData.getPhone());
    }

    public void initTopView(){
        TopView topView = findViewById(R.id.topview_post_location);
        topView.setOnButtonClickListener(() -> finish());
    }

    public void setEnrollBtn(){
        enrollBtn.setOnClickListener(view -> {
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
                locationAPI.postLocation(postLocationData).enqueue(new Callback<ResponseEnrollLocationDTO>() {
                    @Override
                    public void onResponse(Call<ResponseEnrollLocationDTO> call, Response<ResponseEnrollLocationDTO> response) {
                        if (response.body()!=null) {
                            locationEnrollResult = response.body();
                            Intent intent = new Intent(getApplicationContext(), ReviewMapActivity.class);
                            startActivity(intent);
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
        });
    }
}