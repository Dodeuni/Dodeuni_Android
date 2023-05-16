package dodeunifront.dodeuni.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import dodeunifront.dodeuni.Hue.API_Hyu;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.Adapter.DatailImageAdapter;
import dodeunifront.dodeuni.community.DTO.CommentResponseDTO;
import dodeunifront.dodeuni.community.DTO.CommentSaveRequestDTO;
import dodeunifront.dodeuni.community.DTO.CommunityDeatilDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailCommunityActivity extends AppCompatActivity {
    TextView toolvartext, tv_title_community_detail,tv_time_community_detail,
            tv_content_community_detail,tv_community_detail_writer;
    ImageView btn_write_menu,btn_comment_menu;
    RecyclerView rv_detail_recyclerView;
    EditText comment_et;
    Button btn_comment;
    LinearLayout comment_layout;
    DatailImageAdapter datailImageAdapter;
    Long main_writer_id,main_writer_userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_community);

        toolvartext = (TextView)findViewById(R.id.tv_detail_toolbar_title);
        tv_title_community_detail = (TextView)findViewById(R.id.tv_title_community_detail);
        tv_time_community_detail = (TextView)findViewById(R.id.tv_time_community_detail);
        tv_content_community_detail = (TextView)findViewById(R.id.tv_content_community_detail);
        tv_community_detail_writer = (TextView)findViewById(R.id.tv_community_detail_writer);
        btn_write_menu = (ImageView)findViewById(R.id.btn_write_menu);
        rv_detail_recyclerView = (RecyclerView)findViewById(R.id.rv_detail_recyclerView);
        comment_et = (EditText)findViewById(R.id.comment_et);
        btn_comment = (Button)findViewById(R.id.btn_comment);
        comment_layout = (LinearLayout)findViewById(R.id.comment_layout);

        Intent detail = getIntent();
        Long id = detail.getLongExtra("id",100);
        main_writer_id = id;
        main_writer_userid = detail.getLongExtra("userid",100);
//        Log.e("id isisisis",id.toString());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Postcommunity.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API_Postcommunity api_postcommunity = retrofit.create(API_Postcommunity.class);
        api_postcommunity.getDatadetail(id).enqueue(new Callback<CommunityDeatilDTO>() {
            @Override
            public void onResponse(Call<CommunityDeatilDTO> call, Response<CommunityDeatilDTO> response) {
                if (response.body() != null) {
                    CommunityDeatilDTO datas = response.body();
                            toolvartext.setText(datas.getSub());
                            tv_title_community_detail.setText(datas.getTitle());
                            tv_content_community_detail.setText(datas.getContent());
                            tv_community_detail_writer.setText(datas.getNickname());
                            tv_time_community_detail.setText(datas.getCreatedDateTime());
                    datailImageAdapter = new DatailImageAdapter(datas.getPhotoUrl(), getApplicationContext());
                    rv_detail_recyclerView.setAdapter(datailImageAdapter);   // 리사이클러뷰에 어댑터 세팅
                    rv_detail_recyclerView.setLayoutManager(new LinearLayoutManager(DetailCommunityActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        }
                else {
                    Log.e("내용","???????????????????????????????????");
                }
            }

            @Override
            public void onFailure(Call<CommunityDeatilDTO> call, Throwable t) {

            }
        });
        api_postcommunity.getDataComment(main_writer_id).enqueue(new Callback<List<CommentResponseDTO>>() {
            @Override
            public void onResponse(Call<List<CommentResponseDTO>> call, Response<List<CommentResponseDTO>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        comment_layout.removeAllViews();
                        for (int i = 0; i < response.body().size(); i++) {
                            String text = response.body().get(i).getContent();
                            String nickname = response.body().get(i).getNickname();
                            String date = response.body().get(i).getCreatedDateTime();

                            LayoutInflater layoutInflater = LayoutInflater.from(DetailCommunityActivity.this);
                            View customView = layoutInflater.inflate(R.layout.itemlist_comment, null);
                            btn_comment_menu=  ((ImageView)customView.findViewById(R.id.btn_comment_menu));

                            if(main_writer_userid == response.body().get(i).getUid()){
                                btn_comment_menu.setVisibility(View.VISIBLE);
                            } else{
                                btn_comment_menu.setVisibility(View.INVISIBLE);
                            }

                            ((TextView)customView.findViewById(R.id.tv_community_comment_writer)).setText(nickname);
                            //((TextView)customView.findViewById(R.id.tv_comment_date)).setText(content);
                            ((TextView)customView.findViewById(R.id.tv_comment_content)).setText(text);
                            ((TextView)customView.findViewById(R.id.tv_comment_date)).setText(date);

                            comment_layout.addView(customView);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<CommentResponseDTO>> call, Throwable t) {

            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comment_et.getText().length() !=0)
                {
                    String text = comment_et.getText().toString();
                    String nickname = "홍쓰";
                    Long cid = main_writer_id;  //게시글 아이디
                    Long uid = Long.valueOf(1);  //댓글작성자 아이디

                    LayoutInflater layoutInflater = LayoutInflater.from(DetailCommunityActivity.this);
                    View customView = layoutInflater.inflate(R.layout.itemlist_comment, null);
                    btn_comment_menu=  ((ImageView)customView.findViewById(R.id.btn_comment_menu));

                    if(cid == uid){
                        btn_comment_menu.setVisibility(View.VISIBLE);
                    }

                    ((TextView)customView.findViewById(R.id.tv_community_comment_writer)).setText(nickname);
                    //((TextView)customView.findViewById(R.id.tv_comment_date)).setText(content);
                    ((TextView)customView.findViewById(R.id.tv_comment_content)).setText(text);

                    comment_layout.addView(customView);
                    comment_et.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(comment_et.getWindowToken(), 0);

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_Hyu.URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    API_Postcommunity api_postcommunity_comment = retrofit.create(API_Postcommunity.class);
                    CommentSaveRequestDTO commentSaveRequestDTO = new CommentSaveRequestDTO(text,Long.valueOf(0),null,cid,uid);
                    api_postcommunity_comment.postcomment(commentSaveRequestDTO).enqueue(new Callback<List<CommentResponseDTO>>() {
                        @Override
                        public void onResponse(Call<List<CommentResponseDTO>> call, Response<List<CommentResponseDTO>> response) {
                            if(response.isSuccessful()){
                                if (response.body() != null){
                                    Log.e("hey~~~",response.body().get(0).getNickname());
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
//        btn_comment_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
//                getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        if (menuItem.getItemId() == R.id.popup_delete){
//                            Toast.makeText(DetailCommunityActivity.this, "삭제하기", Toast.LENGTH_SHORT).show();
//                        }else if (menuItem.getItemId() == R.id.popup_modify){
//                            Toast.makeText(DetailCommunityActivity.this, "수정하기", Toast.LENGTH_SHORT).show();
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//            }
//        });

    }
    public void popupclicks(View view){
        final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.popup_delete){
                            Toast.makeText(DetailCommunityActivity.this, "삭제하기", Toast.LENGTH_SHORT).show();
                        }else if (menuItem.getItemId() == R.id.popup_modify){
                            Toast.makeText(DetailCommunityActivity.this, "수정하기", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
    }

}