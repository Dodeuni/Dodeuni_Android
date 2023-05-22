package dodeunifront.dodeuni.community;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dodeunifront.dodeuni.ErrorModel;
import dodeunifront.dodeuni.MainActivity;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.Adapter.MultiImageAdapter;
import dodeunifront.dodeuni.community.DTO.ResponseCommunityDTO;
import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PostCommunityMeetActivity extends AppCompatActivity {
    // 사용할 컴포넌트 선언
    EditText title_et, content_et;
    Long userId;
    Button btn_reg;
    final private String TAG = getClass().getSimpleName();
    Dialog dilaog01;
    String main = "모임";
    String sub="모임";
    String title,content;
    ImageView btn_addpic;

    ArrayList<Uri> uriList = new ArrayList<>();     // 이미지의 uri를 담을 ArrayList 객체

    RecyclerView recyclerView;  // 이미지를 보여줄 리사이클러뷰
    MultiImageAdapter adapter;  // 리사이클러뷰에 적용시킬 어댑터
    ArrayList<MultipartBody.Part> names = new ArrayList<>();
    Map<String, RequestBody> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_community_store);

        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", -1);

        TextView tvs = (TextView)findViewById(R.id.toolbar_title_store);
        tvs.setText("모임글쓰기");
        Toolbar toolbar = findViewById(R.id.write_community_toolbar_store);

        title_et = findViewById(R.id.et_post_title_store);
        content_et = findViewById(R.id.et_post_content_store);
        btn_reg = findViewById(R.id.btn_reg_community_store);

        btn_addpic = findViewById(R.id.btn_addpicture_store);
        recyclerView = findViewById(R.id.rv_imageview_post_store);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setTitle("");

        btn_addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()){
                    displayFileChoose();
                }else{
                    requestPermission();
                }
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = title_et.getText().toString();
                content = content_et.getText().toString();
                Log.e("과연",title+content);

                dilaog01 = new Dialog(PostCommunityMeetActivity.this);       // Dialog 초기화
                dilaog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dilaog01.setContentView(R.layout.dialog_postcommunity);
                showDialog01();



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
    public void showDialog01(){
        dilaog01.show(); // 다이얼로그 띄우기
        Button noBtn = dilaog01.findViewById(R.id.btn_post_cancel);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog01.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        dilaog01.findViewById(R.id.btn_post_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                RequestBody main_ = RequestBody.create(MediaType.parse("multipart/form-data"),main);
                RequestBody sub_ = RequestBody.create(MediaType.parse("multipart/form-data"),sub);
                RequestBody title_ = RequestBody.create(MediaType.parse("multipart/form-data"),title);
                RequestBody content_ = RequestBody.create(MediaType.parse("multipart/form-data"),content);

                map.put("main", main_);
                map.put("sub", sub_);
                map.put("title", title_);
                map.put("content", content_);


                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(PostcommunityAPI.URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();

                PostcommunityAPI _postcommunityAPI = retrofit.create(PostcommunityAPI.class);
                _postcommunityAPI.postData(userId,map,names).enqueue(new Callback<ResponseCommunityDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseCommunityDTO> call, @NonNull Response<ResponseCommunityDTO> response) {
                        if(response.isSuccessful()){
                            if (response.body()!=null){
                                Log.e("저장완료","+");
                                StyleableToast.makeText(getApplicationContext(),"글이 등록되었습니다, 새로고침을 해주세요!.",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Converter<ResponseBody, ErrorModel> converter = retrofit.responseBodyConverter(ErrorModel.class, new java.lang.annotation.Annotation[0]);
                            ErrorModel errorModel = null;
                            try {
                                errorModel = converter.convert(response.errorBody());
                                Log.e("error code???",""+errorModel.toString());
                                Log.e("error code???",""+response.body());
                                Log.e("error code???",""+response.message());
                                Log.e("YMC", "stringToJson msg: 실패" + response.code());
                                Toast.makeText(PostCommunityMeetActivity.this, errorModel.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseCommunityDTO> call, Throwable t) {
                        Log.e("통신에러","+"+t.toString());
                        Toast.makeText(getApplicationContext(), "통신에러", Toast.LENGTH_SHORT).show();

                    }
                });
                dilaog01.dismiss();
                finish();           // 앱 종료
            }
        });
    }
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(PostCommunityMeetActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_DENIED){
            return false;
        }else{
            return true;
        }
    }
    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(PostCommunityMeetActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(PostCommunityMeetActivity.this, "권한 수락이 필요합니다.",
                    Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(PostCommunityMeetActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 500);
        }
    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    private void displayFileChoose() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);  // 다중 이미지를 가져올 수 있도록 세팅
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultAlbum.launch(intent);
    }
    ActivityResultLauncher<Intent> activityResultAlbum = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if(result.getData() == null){   // 어떤 이미지도 선택하지 않은 경우
                            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                        }
                        else{   // 이미지를 하나라도 선택한 경우
                            if(result.getData().getClipData() == null){     // 이미지를 하나만 선택한 경우
                                Log.e("single choice: ", String.valueOf(result.getData().getData()));
                                Uri imageUri = result.getData().getData();
                                uriList.add(imageUri);
                                adapter = new MultiImageAdapter(uriList, getApplicationContext());
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(PostCommunityMeetActivity.this, LinearLayoutManager.HORIZONTAL, true));
                            }
                            else{      // 이미지를 여러장 선택한 경우
                                Uri uris = result.getData().getData();
                                ClipData clipData = result.getData().getClipData();
                                Log.e("clipData", String.valueOf(clipData.getItemCount()));
                                if(clipData.getItemCount() > 4){   // 선택한 이미지가 11장 이상인 경우
                                    Toast.makeText(getApplicationContext(), "사진은 4장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                                }
                                else{   // 선택한 이미지가 1장 이상 5장 이하인 경우
                                    Log.e(TAG, "multiple choice");
                                    if(clipData!=null)
                                    {
                                        for (int i = 0; i < clipData.getItemCount(); i++){
                                            Uri imageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.
                                            try {
                                                uriList.add(imageUri);  //uri를 list에 담는다.
                                                String juldapath = getRealPathFromURI(imageUri);
                                                Log.e("uris 리스트는 ",imageUri.toString()+"절대는"+juldapath);
                                                File file = new File(juldapath);
                                                if (!file.exists()) {       // 원하는 경로에 폴더가 있는지 확인
                                                    file.mkdirs();    // 하위폴더를 포함한 폴더를 전부 생성
                                                }
                                                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                                Log.e("과연과연과연과연",""+file.getName());

                                                MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("photo", file.getName() ,requestBody);
                                                names.add(uploadFile);
                                            } catch (Exception e) {
                                                Log.e(TAG, "File select error", e);
                                            }
                                        }
                                        adapter = new MultiImageAdapter(uriList, getApplicationContext());
                                        recyclerView.setAdapter(adapter);   // 리사이클러뷰에 어댑터 세팅
                                        recyclerView.setLayoutManager(new LinearLayoutManager(PostCommunityMeetActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                        // 리사이클러뷰 수평 스크롤 적용
                                    }
                                    else if(uris != null)
                                    {
                                        Toast.makeText(getApplicationContext(), "uris가 비었음.", Toast.LENGTH_LONG).show();

                                    }
                                }

                            }
                        }

                    }


                }
            }
    );
}
