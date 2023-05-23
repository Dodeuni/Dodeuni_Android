package dodeunifront.dodeuni.community;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.hue.HueAPI;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.Adapter.CommentAdapter;
import dodeunifront.dodeuni.community.Adapter.DatailImageAdapter;
import dodeunifront.dodeuni.community.DTO.CommentResponseDTO;
import dodeunifront.dodeuni.community.DTO.CommentSaveRequestDTO;
import dodeunifront.dodeuni.community.DTO.CommunityDeatilDTO;
import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailCommunityActivity extends AppCompatActivity {
    private ArrayList<CommentResponseDTO> commentResponseDTOArrayList;
    CommentAdapter commentAdapter;
    Dialog dilaog01;
    TextView toolvartext, tv_title_community_detail,tv_time_community_detail,
            tv_content_community_detail,tv_community_detail_writer;
    ImageView btn_write_menu,btn_comment_menu;
    RecyclerView rv_detail_recyclerView;
    EditText comment_et;
    TextView btn_comment;
    RecyclerView comment_layout;
    DatailImageAdapter datailImageAdapter;
    Long main_writer_id,main_writer_userid,login_userId;
    String main,sub,title,content;
    List<String> photo_i;
    String nickname;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HueAPI.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_community);
        commentResponseDTOArrayList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentResponseDTOArrayList,getApplicationContext());

        //toolvartext = (TextView)findViewById(R.id.tv_detail_toolbar_title);
        tv_title_community_detail = (TextView)findViewById(R.id.tv_title_community_detail);
        tv_time_community_detail = (TextView)findViewById(R.id.tv_time_community_detail);
        tv_content_community_detail = (TextView)findViewById(R.id.tv_content_community_detail);
        tv_community_detail_writer = (TextView)findViewById(R.id.tv_community_detail_writer);
        btn_write_menu = (ImageView)findViewById(R.id.btn_write_menu);
        rv_detail_recyclerView = (RecyclerView)findViewById(R.id.rv_detail_recyclerView);
        comment_et = (EditText)findViewById(R.id.comment_et);
        btn_comment = findViewById(R.id.btn_comment);
        comment_layout = (RecyclerView) findViewById(R.id.comment_layout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        comment_layout.setLayoutManager(linearLayoutManager);
        comment_layout.setAdapter(commentAdapter);

        TopView topView = findViewById(R.id.topview_detail_community);
        topView.setOnButtonClickListener(() -> finish());


        Intent detail = getIntent();
        Long id = detail.getLongExtra("id",100);
        main_writer_id = id; //현재 게시글의 순서 id
        main_writer_userid = detail.getLongExtra("userid",100); //지금보고 있는 게시글의 아이디
        login_userId = detail.getLongExtra("login_userId",-1);
        nickname =detail.getStringExtra("nickname");
        position = detail.getIntExtra("position",-99);

        btn_write_menu.setVisibility(View.INVISIBLE);
        if(main_writer_userid==login_userId){
            btn_write_menu.setVisibility(View.VISIBLE);
        }

        PostcommunityAPI _postcommunityAPI = retrofit.create(PostcommunityAPI.class);
        _postcommunityAPI.getDatadetail(id).enqueue(new Callback<CommunityDeatilDTO>() {
            @Override
            public void onResponse(Call<CommunityDeatilDTO> call, Response<CommunityDeatilDTO> response) {
                if (response.body() != null) {
                    CommunityDeatilDTO datas = response.body();
                    main = datas.getMain();
                    sub = datas.getSub();
                    photo_i = datas.getPhotoUrl();
                    title = datas.getTitle();
                    content = datas.getContent();

                    topView.setTitle(datas.getSub());
                    tv_title_community_detail.setText(datas.getTitle());
                    tv_content_community_detail.setText(datas.getContent());
                    tv_community_detail_writer.setText(datas.getNickname());
                    String date_fi = datas.getCreatedDateTime();
                    //String createDateTimeParse0 = date_fi.substring(0,date_fi.indexOf("T"));
                    //String createDateTimeParse1 = date_fi.substring(date_fi.indexOf("T")+1,date_fi.indexOf("."));
                    String createDateTimeParse0 = "날짜";
                    String createDateTimeParse1 = "시간";
                    String createDatTimeresult = createDateTimeParse0 +" "+createDateTimeParse1;
                    tv_time_community_detail.setText(createDatTimeresult);
                    if(photo_i!= null)
                    {
                        datailImageAdapter = new DatailImageAdapter(datas.getPhotoUrl(), getApplicationContext());
                        rv_detail_recyclerView.setAdapter(datailImageAdapter);   // 리사이클러뷰에 어댑터 세팅
                        rv_detail_recyclerView.setLayoutManager(new LinearLayoutManager(DetailCommunityActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        }
                } else {
                }
            }

            @Override
            public void onFailure(Call<CommunityDeatilDTO> call, Throwable t) {

            }
        });
        _postcommunityAPI.getDataComment(main_writer_id).enqueue(new Callback<List<CommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<CommentResponseDTO>> call, Response<List<CommentResponseDTO>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        comment_layout.removeAllViews();
                        commentResponseDTOArrayList.clear();
                        for (int i = 0; i < response.body().size(); i++) {
                            CommentResponseDTO datas = response.body().get(i);
                            String text = response.body().get(i).getContent();
                            String nickname = response.body().get(i).getNickname();

                            String date_fi = datas.getCreatedDateTime();
                            //String createDateTimeParse0 = date_fi.substring(0,date_fi.indexOf("T"));
                            //String createDateTimeParse1 = date_fi.substring(date_fi.indexOf("T")+1,date_fi.indexOf("."));
                            String createDateTimeParse0 = "날짜";
                            String createDateTimeParse1 = "시간";
                            String createDatTimeresult = createDateTimeParse0 +" "+createDateTimeParse1;

                            CommentResponseDTO dict_0 = new CommentResponseDTO(datas.getId(),datas.getContent(),datas.getStep(),datas.getPid(),
                                    createDatTimeresult,datas.getCreatedDateTime(),datas.getCid(),datas.getUid(),datas.getNickname());
                            commentResponseDTOArrayList.add(dict_0);
                            commentAdapter.notifyItemInserted(0);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<CommentResponseDTO>> call, Throwable t) {
                Log.e("통신실패",t.toString());
            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comment_et.getText().length() !=0)
                {
                    String text = comment_et.getText().toString();
                    comment_et.setText("");
                    Long cid = main_writer_id;  //게시글 아이디
                    Long uid = login_userId;  //댓글작성자 아이디
                    Log.e("게시글 아이디        ",cid+"     댓글작성자 아이디"+uid);

                    LayoutInflater layoutInflater = LayoutInflater.from(DetailCommunityActivity.this);
                    View customView = layoutInflater.inflate(R.layout.itemlist_comment, null);
                    btn_comment_menu=  ((ImageView)customView.findViewById(R.id.btn_comment_menu));

                    if(cid == uid){
                        btn_comment_menu.setVisibility(View.VISIBLE);
                    }

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(comment_et.getWindowToken(), 0);


                    PostcommunityAPI _postcommunity_API_comment = retrofit.create(PostcommunityAPI.class);
                    CommentSaveRequestDTO commentSaveRequestDTO = new CommentSaveRequestDTO(text,Long.valueOf(0),null,cid,uid);
                    _postcommunity_API_comment.postcomment(commentSaveRequestDTO).enqueue(new Callback<List<CommentResponseDTO>>() {
                        @Override
                        public void onResponse(Call<List<CommentResponseDTO>> call, Response<List<CommentResponseDTO>> response) {
                            if(response.isSuccessful()){
                                if (response.body() != null){
                                    Log.e("hey~~~",response.body().get(0).getNickname());
                                    CommentResponseDTO dict_0 = new CommentResponseDTO(text,
                                            response.body().get(0).getCreatedDateTime(),
                                            nickname);
                                    commentResponseDTOArrayList.add(dict_0);
                                    commentAdapter.notifyDataSetChanged();
                                }
                                else {Log.e("hey~~~","het~~~~~~~~~");

                                }
                            }else{
                                Log.e("YMC", "stringToJson msg: 실패" + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<CommentResponseDTO>> call, Throwable t) {

                        }
                    });

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"입력해주세요",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
    public void popupclicks(View view){
        final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.popup_delete){
                            dilaog01 = new Dialog(DetailCommunityActivity.this);       // Dialog 초기화
                            dilaog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dilaog01.setContentView(R.layout.dialog_deletecommunity);
                            showDialog01();
                        }else if (menuItem.getItemId() == R.id.popup_modify){
                            Intent intent = new Intent(getApplicationContext(),EditCommunityActivity.class);
                            intent.putExtra("main",main);
                            intent.putExtra("sub",sub);
                            intent.putExtra("title",title);
                            intent.putExtra("content",content);
                            intent.putExtra("main_writer_id",main_writer_id);
                            intent.putExtra("login_userId",login_userId);

                            ArrayList<String> arrayList = new ArrayList<String>();
                            if (photo_i!=null){
                            arrayList.addAll(photo_i);}

                            intent.putStringArrayListExtra("arrayList",arrayList);
                            mStartForResult_edit.launch(intent);
//                            startActivity(intent);
                        }
                        return false;
                    }
                });
                popupMenu.show();
    }
    ActivityResultLauncher<Intent> mStartForResult_edit = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    String title_new = intent.getStringExtra("title");
                    String content_new = intent.getStringExtra("content");
                    tv_title_community_detail.setText(title_new);
                    tv_content_community_detail.setText(content_new);
                }
            }
    );
    public void showDialog01(){
        dilaog01.show(); // 다이얼로그 띄우기
        Button noBtn = dilaog01.findViewById(R.id.btn_delete_cancel);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog01.dismiss();
            }
        });
        dilaog01.findViewById(R.id.btn_delete_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                PostcommunityAPI _postcommunity_API_comment = retrofit.create(PostcommunityAPI.class);
                _postcommunity_API_comment.deletecommunity(main_writer_userid,main_writer_id).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.e("삭제성공",response.body()+"");
                        StyleableToast.makeText(DetailCommunityActivity.this, "삭제가 완료되었습니다,새로고침을 해주세요!",
                                Toast.LENGTH_LONG,R.style.mytoast).show();
                    }
                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e("onfailure",t.toString());
                    }
                });
                dilaog01.dismiss();
                Intent intent = new Intent();
                intent.putExtra("position",position);
                setResult(3000,intent);
                finish();           // 앱 종료
            }
        });
    }

}