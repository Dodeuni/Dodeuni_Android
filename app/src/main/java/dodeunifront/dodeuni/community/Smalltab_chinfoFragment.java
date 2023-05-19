package dodeunifront.dodeuni.community;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_smalltab, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_community);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);
        regarrayList = new ArrayList<>();
        regAdapter = new RegAdapter(regarrayList);
        recyclerView.setAdapter(regAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostcommunityAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

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


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostcommunityAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PostcommunityAPI _postcommunityAPI = retrofit.create(PostcommunityAPI.class);
//        _postcommunityAPI.getDatadetail(Long.valueOf(1)).enqueue(new Callback<CommunityListResponseDTO>() {
//            @Override
//            public void onResponse(Call<CommunityListResponseDTO> call, Response<CommunityListResponseDTO> response) {
//                if (response.body() != null) {
//                        CommunityListResponseDTO datas = response.body();
//                        if (datas != null) {
//                            regarrayList.clear();
//                                CommunityListResponseDTO dict_0 = new CommunityListResponseDTO(response.body().getCreatedDateTime(),
//                                        response.body().getNickname(), response.body().getTitle(),
//                                        response.body().getContent());
//                                Log.e("내용",response.body().getContent());
//                                Log.e("제목",response.body().getTitle());
//
//                                regarrayList.add(dict_0);
//                                regAdapter.notifyItemInserted(0);
//
//                            Log.e("getdatalist end", "======================================");
//                        }}
//            }
//
//            @Override
//            public void onFailure(Call<CommunityListResponseDTO> call, Throwable t) {
//
//            }
//        });


       // PostCommunityDTO regdatas = new PostCommunityDTO(titel,content);
        //regarrayList.add(regdatas);
        //regAdapter.notifyItemInserted(0);

       // Toast.makeText(getContext(), "불러오기 하였습니다..", Toast.LENGTH_SHORT).show();

//        if (getArguments() != null){
//            Bundle bundle = getArguments();
//            String titles = bundle.getString("title");
//            String contents = bundle.getString("content");
//            RegData regdatas = new RegData(titles,contents);
//            regarrayList.add(regdatas);
//            regAdapter.notifyItemInserted(0);
//        } else{
//            Log.e("keyekyeky con",":lescontents");
//        }
    }
}
