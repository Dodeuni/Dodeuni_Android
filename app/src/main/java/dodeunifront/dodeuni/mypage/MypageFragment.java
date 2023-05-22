package dodeunifront.dodeuni.mypage;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import dodeunifront.dodeuni.Hue.HueAPI;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.DetailCommunityActivity;
import dodeunifront.dodeuni.community.EditCommunityActivity;
import dodeunifront.dodeuni.community.PostcommunityAPI;
import dodeunifront.dodeuni.login.LoginAPI;
import dodeunifront.dodeuni.login.LoginActivity;
import dodeunifront.dodeuni.login.UserResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MypageFragment extends Fragment {

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HueAPI.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    RecyclerView rv_mypage;
    Dialog dilaog01,dialog_logout;
    Button logout;
    ImageView edit_name;
    TextView tv_nickname,tv_email;
    Long userId;
    String nickname,email;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        userId = getArguments().getLong("userId",-1);
        nickname = getArguments().getString("nickname");
        email = getArguments().getString("email");

        rv_mypage = (RecyclerView) view.findViewById(R.id.rv_mypage);
        logout = (Button) view.findViewById(R.id.btn_logout);
        edit_name =(ImageView) view.findViewById(R.id.btn_mypage_pencil);
        tv_nickname = (TextView) view.findViewById(R.id.tv_mypage_name);
        tv_nickname.setText(nickname);
        tv_email = (TextView) view.findViewById(R.id.tv_mypage_email);
        tv_email.setText(email);

        LoginAPI loginAPI = retrofit.create(LoginAPI.class);
        loginAPI.getDatalogin(userId).enqueue(new Callback<UserResponseDTO>() {
            @Override
            public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {
                if(response.isSuccessful()){
                    if (response.body()!=null){
                        tv_nickname.setText(response.body().getNickname());
                    }
                }else{
                    Log.e("연결에러",": 문제가 있습니다");
                }
            }

            @Override
            public void onFailure(Call<UserResponseDTO> call, Throwable t) {
                Log.e("통신실패",t.toString());
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_logout = new Dialog(view.getContext());       // Dialog 초기화
                dialog_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_logout.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_logout.setContentView(R.layout.dialog_logout);
                showDialoglogout();

            }
        });

        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editnicknamge(v);
            }
        });
        rv_mypage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item> data = new ArrayList<>();

        dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item isorimal = new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(
                dodeunifront.dodeuni.mypage.ExpandableListAdapter.HEADER, "아이소리몰");
        isorimal.invisibleChildren = new ArrayList<>();
        isorimal.invisibleChildren.add(new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(dodeunifront.dodeuni.mypage.ExpandableListAdapter.CHILD,
                "치료도구, 진단평가도구, 교육도구, 놀이교구, 온라인 검사 등을 판매하고 있으며 전문적인 놀이도구와 교구, 평가도구입니다."));
        isorimal.invisibleChildren.add(new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(ExpandableListAdapter.ADRESS,
                "https://www.isorimall.com/main/",0));
        data.add(isorimal);

        dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item jja = new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(
                dodeunifront.dodeuni.mypage.ExpandableListAdapter.HEADER, "중앙장애아동·발달장애인지원센터");
        jja.invisibleChildren = new ArrayList<>();
        jja.invisibleChildren.add(new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(dodeunifront.dodeuni.mypage.ExpandableListAdapter.CHILD,
                "개인별 지원계획, 주간활동 서비스, 부모교육 지원사업, 부모상담 지원사업, 방과 후 활동서비스, 장애아가족 양육지원 사업등을 운영하고 있으며" +
                        "필요한 서비스를 신청할 수 있습니다"));
        jja.invisibleChildren.add(new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(ExpandableListAdapter.ADRESS,
                "https://www.broso.or.kr/mainPage.do",0));
        data.add(jja);

        dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item places = new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(
                dodeunifront.dodeuni.mypage.ExpandableListAdapter.HEADER, "다모아");
        places.invisibleChildren = new ArrayList<>();
        places.invisibleChildren.add(new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(dodeunifront.dodeuni.mypage.ExpandableListAdapter.CHILD,
                "방송통신위원회와 시청자 미디어 재단이 만든 발달장애인 콘텐츠 전문 검색 홈페이지, 발달장애인을 위해 제작한 맞춤형 영상 콘텐츠부터" +
                        "뉴스기사,책,전문자료 등 지식정보를 얻을 수 있습니다."));
        places.invisibleChildren.add(new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(ExpandableListAdapter.ADRESS,
                "https://www.damoa.or.kr/main/inner.php?sMenu=main",0));
        data.add(places);

        dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item onmom = new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(
                dodeunifront.dodeuni.mypage.ExpandableListAdapter.HEADER, "온맘");
        onmom.invisibleChildren = new ArrayList<>();
        onmom.invisibleChildren.add(new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(dodeunifront.dodeuni.mypage.ExpandableListAdapter.CHILD,
                "장애 영유아부터 성인까지 건강, 교육 관련 서비스 등에 대한 부모르 위한 실제적이고 체계적인 양육정보를 제공하는 서비스입니다."));
        onmom.invisibleChildren.add(new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(ExpandableListAdapter.ADRESS,
                "https://www.nise.go.kr/onmam/front/index.do",0));
        data.add(onmom);

        dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item serviceP = new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(
                dodeunifront.dodeuni.mypage.ExpandableListAdapter.HEADER, "사회서비스 전자바우처");
        serviceP.invisibleChildren = new ArrayList<>();
        serviceP.invisibleChildren.add(new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(dodeunifront.dodeuni.mypage.ExpandableListAdapter.CHILD,
                "사회적 약자를 위한 사회 서비스/복지 제공기관을 쉽게 찾아볼 수 있는 사이트 입니다. 장애인 활동지우너 서비스부터 지역별 서비스 기관을 " +
                        "검색해볼 수 있습니다."));
        serviceP.invisibleChildren.add(new dodeunifront.dodeuni.mypage.ExpandableListAdapter.Item(ExpandableListAdapter.ADRESS,
                "https://www.socialservice.or.kr:444/",0));
        data.add(serviceP);
        rv_mypage.setAdapter(new ExpandableListAdapter(data,getContext()));
        return view;
    }

    public void Editnicknamge(View view){
        dilaog01 = new Dialog(view.getContext());       // Dialog 초기화
        dilaog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilaog01.setContentView(R.layout.dialog_nickname);
        showDialog01();
    }
    public void showDialog01(){
        dilaog01.show(); // 다이얼로그 띄우기
        Button noBtn = dilaog01.findViewById(R.id.btn_nickname_cancel);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog01.dismiss();
            }
        });
        dilaog01.findViewById(R.id.btn_nickname_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_nick = (EditText) dilaog01.findViewById(R.id.etet_newnickname);
                String chnick = et_nick.getText().toString();

                // 원하는 기능 구현
                LoginAPI loginAPI = retrofit.create(LoginAPI.class);
                loginAPI.updateNickname(userId,chnick).enqueue(new Callback<UserResponseDTO>() {
                    @Override
                    public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {
                        if (response.isSuccessful()){
                            if (response.body()!=null){
                                Log.e("변경된 닉네임 : ",response.body().getNickname());
                                tv_nickname.setText(response.body().getNickname());
                            }
                        } else {
                            Log.e("성공적이지 못한 연결","error");
                        }

                    }

                    @Override
                    public void onFailure(Call<UserResponseDTO> call, Throwable t) {
                        Log.e("변경된 닉네임 : ",t.toString());

                    }
                });
                dilaog01.dismiss();
//                finish();// 앱 종료
            }
        });

    }
    public void showDialoglogout(){
        dialog_logout.show(); // 다이얼로그 띄우기
        Button noBtn = dialog_logout.findViewById(R.id.btn_logout_cancel);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_logout.dismiss();
            }
        });
        dialog_logout.findViewById(R.id.btn_logout_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getContext().getSharedPreferences("autoLogin",MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                dialog_logout.dismiss();
                Intent i = new Intent(getContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }
}