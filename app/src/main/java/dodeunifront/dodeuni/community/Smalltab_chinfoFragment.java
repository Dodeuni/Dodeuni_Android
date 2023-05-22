package dodeunifront.dodeuni.community;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.Adapter.RegAdapter;
import dodeunifront.dodeuni.community.DTO.CommunityListResponseDTO;
import dodeunifront.dodeuni.community.DTO.PostCommunityDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Smalltab_chinfoFragment extends Fragment {
    RecyclerView recyclerView;
    RegAdapter regAdapter;
    PostCommunityDTO regData;
    private ArrayList<CommunityListResponseDTO> regarrayList;
    SwipeRefreshLayout swipeRefreshLayout;
    Long userId;
    String nickname;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(PostcommunityAPI.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    Boolean pans;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_smalltab, container, false);
        Log.e("크레이에티뷰","*******************************");
        pans=true;

        userId = getArguments().getLong("userId",-1);
        nickname = getArguments().getString("nickname");


        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_community);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);
        regarrayList = new ArrayList<>();
        regAdapter = new RegAdapter(regarrayList,userId,nickname);
        recyclerView.setAdapter(regAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);


        PostcommunityAPI _postcommunityAPI = retrofit.create(PostcommunityAPI.class);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                _postcommunityAPI.getDatalist("정보","정보교환").enqueue(new Callback<List<CommunityListResponseDTO>>() {
                    @Override
                    public void onResponse(Call<List<CommunityListResponseDTO>> call, Response<List<CommunityListResponseDTO>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                ArrayList<CommunityListResponseDTO> new_regarrayList = new ArrayList<>();;
                                List<CommunityListResponseDTO> datas = response.body();
                                if (datas != null) {
                                    for (int i = 0; i < datas.size(); i++) {
                                        CommunityListResponseDTO dict_0 = new CommunityListResponseDTO(response.body().get(i).getId(),
                                                response.body().get(i).getCreatedDateTime(),
                                                response.body().get(i).getNickname(), response.body().get(i).getTitle(),
                                                response.body().get(i).getContent(),response.body().get(i).getThumbnailUrl(),
                                                response.body().get(i).getUserId());
                                        Log.e("내용",response.body().get(i).getContent());
                                        Log.e("제목",response.body().get(i).getTitle());
                                        new_regarrayList.add(0,dict_0);
                                    }
                                    regarrayList.clear();
                                    regarrayList.addAll(new_regarrayList);
                                    regAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<CommunityListResponseDTO>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        _postcommunityAPI.getDatalist("정보","정보교환").enqueue(new Callback<List<CommunityListResponseDTO>>() {
            @Override
            public void onResponse(Call<List<CommunityListResponseDTO>> call, Response<List<CommunityListResponseDTO>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<CommunityListResponseDTO> datas = response.body();
                        if (datas != null) {
                            regarrayList.clear();
                            for (int i = 0; i < datas.size(); i++) {
                                CommunityListResponseDTO dict_0 = new CommunityListResponseDTO(response.body().get(i).getId(),
                                        response.body().get(i).getCreatedDateTime(),
                                        response.body().get(i).getNickname(), response.body().get(i).getTitle(),
                                        response.body().get(i).getContent(),response.body().get(i).getThumbnailUrl(),
                                        response.body().get(i).getUserId());
                                Log.e("내용",response.body().get(i).getContent());
                                Log.e("제목",response.body().get(i).getTitle());

                                regarrayList.add(0,dict_0);
                                regAdapter.notifyItemInserted(0);
                            }
                            Log.e("getdatalist end", "======================================");
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<CommunityListResponseDTO>> call, Throwable t) {

            }
        });

        regAdapter.setOnItemClickListener(new RegAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent detail = new Intent(getContext(), DetailCommunityActivity.class);

                detail.putExtra("id",regarrayList.get(position).getId());
                detail.putExtra("userid",regarrayList.get(position).getUserId());
                detail.putExtra("login_userId",userId);
                detail.putExtra("nickname",nickname);
                detail.putExtra("position",position);

                detailLauncher.launch(detail);
            }
        });
        detailLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()== 3000){
                    Log.e("삭제완fy : ","삭제되었다");

                    regarrayList.remove(result.getData().getIntExtra("position",-99));
                    regAdapter.notifyItemRemoved(result.getData().getIntExtra("position",-99));
                    regAdapter.notifyDataSetChanged();
                }
            }
        });

        return rootView;
    }



    private ActivityResultLauncher<Intent> detailLauncher;    //TODO 디테일액티비티로 넘어가는 콜백

    @Override
    public void onPause() {
        super.onPause();
        pans=false;
        Log.e("onpause","++++++++++++++++++++++++++++++++++++++++++++");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("rearrayaㅅ아ㅣ크",regarrayList.size()+"");

//        if(pans==false){
//            Log.e("재실행합니다","++++++++++++++++++++++++++++++++++++++++");
//            PostcommunityAPI _postcommunityAPI = retrofit.create(PostcommunityAPI.class);
//            _postcommunityAPI.getDatalist("정보","정보교환").enqueue(new Callback<List<CommunityListResponseDTO>>() {
//                @Override
//                public void onResponse(Call<List<CommunityListResponseDTO>> call, Response<List<CommunityListResponseDTO>> response) {
//                    if (response.isSuccessful()) {
//                        if (response.body() != null) {
//                            ArrayList<CommunityListResponseDTO> new_regarrayList = new ArrayList<>();;
//                            List<CommunityListResponseDTO> datas = response.body();
//                            if (datas != null) {
//                                for (int i = 0; i < datas.size(); i++) {
//                                    CommunityListResponseDTO dict_0 = new CommunityListResponseDTO(response.body().get(i).getId(),
//                                            response.body().get(i).getCreatedDateTime(),
//                                            response.body().get(i).getNickname(), response.body().get(i).getTitle(),
//                                            response.body().get(i).getContent(),response.body().get(i).getThumbnailUrl(),
//                                            response.body().get(i).getUserId());
//                                    Log.e("내용",response.body().get(i).getContent());
//                                    Log.e("제목",response.body().get(i).getTitle());
//                                    new_regarrayList.add(0,dict_0);
//                                }
//                                regarrayList.clear();
//                                regarrayList.addAll(new_regarrayList);
//                                regAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                }
//                @Override
//                public void onFailure(Call<List<CommunityListResponseDTO>> call, Throwable t) {
//
//                }
//            });
//
//        }
    }
}
