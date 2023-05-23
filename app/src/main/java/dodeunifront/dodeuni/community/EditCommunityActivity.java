package dodeunifront.dodeuni.community;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dodeunifront.dodeuni.ErrorModel;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.community.Adapter.EditImageAdapter;
import dodeunifront.dodeuni.community.Adapter.MultiImageAdapter;
import dodeunifront.dodeuni.community.DTO.CommentResponseDTO;
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

public class EditCommunityActivity extends AppCompatActivity {
    ArrayList<Uri> uriList = new ArrayList<>();     // 이미지의 uri를 담을 ArrayList 객체
    EditImageAdapter editImageAdapter;
    MultiImageAdapter multiImageAdapter;
    String main,sub,title,content;
    ArrayList<String> arrayList;
    List<String> photo_i = null;
    EditText et_title,et_content;
    RecyclerView rv_edit_photo;
    CardView btn_edit;
    TextView btn_sub;
    ImageView btn_addphoto;
    ArrayList<MultipartBody.Part> names = new ArrayList<>();
    Map<String, RequestBody> map = new HashMap<>();
    Dialog dilaog01;
    Long main_writer_id,login_userId;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_community);
        //Toolbar toolbar = findViewById(R.id.edit_community_toolbar);

        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setTitle("");*/

        TopView topView  = findViewById(R.id.topview_edit_community);
        topView.setOnButtonClickListener(() -> finish());

        intent = getIntent();
        main = intent.getStringExtra("main");
        sub = intent.getStringExtra("sub");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        main_writer_id = intent.getLongExtra("main_writer_id",-1);
        arrayList = intent.getStringArrayListExtra("arrayList");
        login_userId = intent.getLongExtra("login_userId",-1);

        photo_i = arrayList;
        for(int i=0;i<photo_i.size();i++){
            Uri uri = Uri.parse(photo_i.get(i));
            uriList.add(uri);
        }
        btn_edit = findViewById(R.id.btn_edit_editcommunity);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = et_title.getText().toString();
                content = et_content.getText().toString();
                Log.e("과연",title+content);

                dilaog01 = new Dialog(EditCommunityActivity.this);       // Dialog 초기화
                dilaog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dilaog01.setContentView(R.layout.dialog_editcommunity);
                showDialog01();
            }
        });
        et_content = (EditText) findViewById(R.id.et_edit_content);
        et_title = (EditText) findViewById(R.id.et_edit_title);
        btn_sub = findViewById(R.id.btn_edit_sub);
        btn_addphoto = (ImageView) findViewById(R.id.btn_edit_addpicture);
        btn_addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayFileChoose();
            }
        });
        rv_edit_photo = (RecyclerView)findViewById(R.id.rv_imageview_edit);
        init();
    }
    public void init(){
        et_content.setText(content);
        et_title.setText(title);
        btn_sub.setText(sub);
        if(photo_i!= null) {
            editImageAdapter = new EditImageAdapter(photo_i, getApplicationContext());
            rv_edit_photo.setAdapter(editImageAdapter);   // 리사이클러뷰에 어댑터 세팅
            rv_edit_photo.setLayoutManager(new LinearLayoutManager(EditCommunityActivity.this, LinearLayoutManager.HORIZONTAL, false));
        }
    }
    public void showDialog01(){
        dilaog01.show(); // 다이얼로그 띄우기
        Button noBtn = dilaog01.findViewById(R.id.btn_edit_cancel);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog01.dismiss();
            }
        });
        dilaog01.findViewById(R.id.btn_edit_ok).setOnClickListener(new View.OnClickListener() {
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
                Long id = (main_writer_id);
                Long userid = login_userId;

                List<Long> deletePhotoId=null;

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(PostcommunityAPI.URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();

                PostcommunityAPI _postcommunityAPI = retrofit.create(PostcommunityAPI.class);
                _postcommunityAPI.putDataCommunity(id,userid,map,names,deletePhotoId).enqueue(new Callback<CommentResponseDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<CommentResponseDTO> call, @NonNull Response<CommentResponseDTO> response) {
                        if(response.isSuccessful()){
                            if (response.body()!=null){
                                Log.e("!!!!!!!!!!!!!!!!!!!!!!","수정완료");
                                StyleableToast.makeText(getApplicationContext(),"수정이 완료되었습니다.",Toast.LENGTH_LONG,
                                        R.style.mytoast).show();
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
                                Toast.makeText(EditCommunityActivity.this, errorModel.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentResponseDTO> call, Throwable t) {
                        Log.e("통신에러","+"+t.toString());
                        Toast.makeText(getApplicationContext(), "통신에러", Toast.LENGTH_SHORT).show();

                    }
                });
                dilaog01.dismiss();
                intent.putExtra("main",main);
                intent.putExtra("sub",sub);
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                intent.putExtra("main_writer_id",main_writer_id);
                intent.putExtra("login_userId",login_userId);
                setResult(RESULT_OK,intent);
                finish();           // 앱 종료
            }
        });
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
                                multiImageAdapter = new MultiImageAdapter(uriList, getApplicationContext());
                                rv_edit_photo.setAdapter(multiImageAdapter);
                                rv_edit_photo.setLayoutManager(new LinearLayoutManager(EditCommunityActivity.this, LinearLayoutManager.HORIZONTAL, true));
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
                                        multiImageAdapter = new MultiImageAdapter(uriList, getApplicationContext());
                                        rv_edit_photo.setAdapter(multiImageAdapter);   // 리사이클러뷰에 어댑터 세팅
                                        rv_edit_photo.setLayoutManager(new LinearLayoutManager(EditCommunityActivity.this, LinearLayoutManager.HORIZONTAL, false));
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
}