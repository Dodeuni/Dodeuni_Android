package dodeunifront.dodeuni.map.view;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;

import dodeunifront.dodeuni.LandingActivity;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.map.CurrentLocation;
import dodeunifront.dodeuni.map.adapter.FindLocationRecyclerAdapter;
import dodeunifront.dodeuni.map.adapter.RecommendLocationRecyclerAdapter;
import dodeunifront.dodeuni.map.api.KakaoMapAPI;
import dodeunifront.dodeuni.map.api.LocationAPI;
import dodeunifront.dodeuni.map.dto.request.RequestEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.request.RequestRecommendLocationDTO;
import dodeunifront.dodeuni.map.dto.KakaoLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseKakaoLocationListDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseKakaoXYListDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseRecommendLocationDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;
    private ViewGroup mapViewContainer;
    private MapView mapView;
    double resultLatitude, resultLongitude;
    String searchLatitude, searchLongitude;
    Gson gson;
    BottomSheetBehavior<View> bottomSheet;
    CurrentLocation.Geocoord currentGeocoord;
    ImageButton btnCurrentLocation, btnLocationSearch;
    RecyclerView mRecyclerView;
    EditText editSearch;
    ResponseKakaoLocationListDTO kakaoSearchResult;
    RecommendLocationRecyclerAdapter mRecommendRecyclerAdapter;
    FindLocationRecyclerAdapter mkeywordRecyclerAdapter;
    List<ResponseRecommendLocationDTO> recommendResult;
    RequestRecommendLocationDTO recommendRequest = new RequestRecommendLocationDTO();
    TextView tvRecommend, tvCenter, tvKinder, tvSchool, tvSpecialS;
    TextView tvTags[];
    String[] tags = {"", "아동발달", "PS3", "SC4", "특수학교"};

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mapViewContainer != null && mapViewContainer.indexOfChild(mapView) == -1){
            try {
                System.out.println("no view");
                initMapView();
            } catch (RuntimeException re){
                re.printStackTrace();
            }
        }

        System.out.println("view view");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_map, container, false);
        btnCurrentLocation = v.findViewById(R.id.imgBtn_location_current);
        btnLocationSearch = v.findViewById(R.id.imgBtn_location_search);
        mRecyclerView = v.findViewById(R.id.rv_location_recommend);
        editSearch = v.findViewById(R.id.edit_location_search);
        tvRecommend = v.findViewById(R.id.tv_recommend_tag);
        tvCenter = v.findViewById(R.id.tv_center_tag);
        tvKinder = v.findViewById(R.id.tv_kindergarden_tag);
        tvSchool = v.findViewById(R.id.tv_school_tag);
        tvSpecialS = v.findViewById(R.id.tv_special_school_tag);
        TextView tv_new = v.findViewById(R.id.tv_location_new);

        tvTags = new TextView[]{tvRecommend, tvCenter, tvKinder, tvSchool, tvSpecialS};


        initMapView();
        initBottomSheet();

        tv_new.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LocationFindActivity.class);
            startActivity(intent);
        });

        btnCurrentLocation.setOnClickListener(view -> {
            moveToCurrentLocation();
        });

        setSearchBtn();

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        getRecommendLocation("");
        setKeywords();
        searchLongitude = currentGeocoord.getLongitude() + "";
        searchLatitude = currentGeocoord.getLatitude() + "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getCurrentLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
    }

    public void getCurrentLocation(){
        final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        CurrentLocation currentLocation = new CurrentLocation(lm, getActivity());
        currentGeocoord = currentLocation.getCurrentLocation();
        Log.d("LOCATION",  "위도 : " + currentGeocoord.getLongitude() + "\n" +
                "경도 : " + currentGeocoord.getLatitude());
    }

    public void initBottomSheet(){
        bottomSheet = BottomSheetBehavior.from(
                v.findViewById(R.id.drawer_location_recommend)
        );
        bottomSheet.setPeekHeight(500);
        bottomSheet.setMaxHeight(2000);
    }

    private void initMapView(){
        mapView = new MapView(getContext());
        mapViewContainer = v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        moveToCurrentLocation();
    }

    public void initRecyclerViewWithRecommend(){
        mRecommendRecyclerAdapter = new RecommendLocationRecyclerAdapter();
        mRecyclerView.setAdapter(mRecommendRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRecommendRecyclerAdapter.setLocationResult(recommendResult);
        mRecommendRecyclerAdapter.setOnItemClickListener((locationData) -> {
            Intent intent = new Intent(getContext(), LocationDetailActivity.class);
            intent.putExtra("id", locationData.getId());
            startActivity(intent);
            Toast.makeText(getActivity(), "clicked: " + locationData.getName(), Toast.LENGTH_LONG).show();
        });
    }

    public void initRecyclerViewWithKeyword(){
        mkeywordRecyclerAdapter = new FindLocationRecyclerAdapter(mapView);
        mRecyclerView.setAdapter(mkeywordRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mkeywordRecyclerAdapter.setLocationResult(kakaoSearchResult);
        mkeywordRecyclerAdapter.setOnItemClickListener((locationData) -> {
            moveToKakaoLocationDetail(locationData);
            Toast.makeText(getActivity(), "clicked: " + locationData.getPlaceName(), Toast.LENGTH_LONG).show();
        });
    }

    public void setMarkers(){
        for(ResponseRecommendLocationDTO info: recommendResult){
            double lon = info.getY();
            double lat = info.getX();
            MapPOIItem marker = new MapPOIItem();
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(lon, lat);
            marker.setItemName(info.getName());
            marker.setMapPoint(mapPoint);
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            marker.setCustomImageResourceId(R.drawable.location_green_midium);
            mapView.addPOIItem(marker);
        }
    }

    public void setKeywordMarkers(){
        for(KakaoLocationDTO info: kakaoSearchResult.getDocuments()){
            double lon = Double.parseDouble(info.getY());
            double lat = Double.parseDouble(info.getX());
            MapPOIItem marker = new MapPOIItem();
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(lon, lat);
            marker.setItemName(info.getPlaceName());
            marker.setMapPoint(mapPoint);
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            marker.setCustomImageResourceId(R.drawable.location_green_midium);
            mapView.addPOIItem(marker);
        }
    }

    public void setSearchBtn(){
        btnLocationSearch.setOnClickListener(view -> {
            mapView.removeAllPOIItems();
            String keyword = editSearch.getText().toString();
            if(keyword.length() != 0){
                getSearchXY(keyword);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                getRecommendLocation(keyword);
                System.out.println(resultLongitude);
            } else {
                getRecommendLocation("");
                tagClicked(tvTags[0]);
                searchLatitude = currentGeocoord.getLatitude() + "";
                searchLongitude = currentGeocoord.getLongitude() + "";
            }
        });
    }

    public void moveToCurrentLocation(){
        if(currentGeocoord != null){
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(currentGeocoord.getLatitude(), currentGeocoord.getLongitude()), true);
        }
    }

    public void getRecommendLocation(String keyword){
        recommendRequest.setX(currentGeocoord.getLongitude());
        recommendRequest.setY(currentGeocoord.getLatitude());
        recommendRequest.setKeyword(keyword);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LocationAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LocationAPI locationAPI = retrofit.create(LocationAPI.class);
        locationAPI.postRecommendList(recommendRequest).enqueue(new Callback<List<ResponseRecommendLocationDTO>>() {
            @Override
            public void onResponse(Call<List<ResponseRecommendLocationDTO>> call, Response<List<ResponseRecommendLocationDTO>> response) {
                    recommendResult = response.body();
                if (response.body() != null) {
                    setMarkers();
                    Log.d("성공", recommendResult.get(0).getX() + " ");
                } else {
                    Toast.makeText(getContext(), "검색 결과 없음", Toast.LENGTH_SHORT).show();
                }
                    initRecyclerViewWithRecommend();
            }
            @Override
            public void onFailure(Call<List<ResponseRecommendLocationDTO>> call, Throwable t) {
                Log.d("실패","통신 실패: "+ t.getMessage());
            }
        });
    }

    public void getKeywordLocation(String keyword){
        if(searchLongitude != "200" && searchLatitude != "200") {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(KakaoMapAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            KakaoMapAPI kakaoMapAPI = retrofit.create(KakaoMapAPI.class);
            kakaoMapAPI.getKeywordLocationList(keyword, searchLongitude, searchLatitude, 15, "distance").enqueue(new Callback<ResponseKakaoLocationListDTO>() {
                @Override
                public void onResponse(Call<ResponseKakaoLocationListDTO> call, Response<ResponseKakaoLocationListDTO> response) {
                    if (response.body().getLength() != 0) {
                        mapView.removeAllPOIItems();
                        kakaoSearchResult = response.body();
                        double longitude = Double.parseDouble(kakaoSearchResult.getDocuments().get(0).getX());
                        double latitude = Double.parseDouble(kakaoSearchResult.getDocuments().get(0).getY()) - 0.002;
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
                        setKeywordMarkers();
                        initRecyclerViewWithKeyword();
                        Log.d("성공", kakaoSearchResult.getDocuments().get(0).getAddress());
                    } else {
                        Toast.makeText(getContext(), "검색 결과 없음", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseKakaoLocationListDTO> call, Throwable t) {
                    Log.d("실패", "통신 실패: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(getContext(), "검색 결과 없음", Toast.LENGTH_LONG).show();
        }
    }

    public void getCategoryLocation(String category){
        if(searchLongitude != "200" && searchLatitude != "200") {
            MapPoint centerPoint = mapView.getMapCenterPoint();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(KakaoMapAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            KakaoMapAPI kakaoMapAPI = retrofit.create(KakaoMapAPI.class);
            kakaoMapAPI.getCategoryLocationList(category, searchLongitude, searchLatitude, 15, "distance").enqueue(new Callback<ResponseKakaoLocationListDTO>() {
                @Override
                public void onResponse(Call<ResponseKakaoLocationListDTO> call, Response<ResponseKakaoLocationListDTO> response) {
                    if (response.body().getLength() != 0) {
                        kakaoSearchResult = response.body();
                        double longitude = Double.parseDouble(kakaoSearchResult.getDocuments().get(0).getX());
                        double latitude = Double.parseDouble(kakaoSearchResult.getDocuments().get(0).getY()) - 0.002;
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
                        setKeywordMarkers();
                        initRecyclerViewWithKeyword();
                        Log.d("성공", kakaoSearchResult.getDocuments().get(0).getAddress());
                    } else {
                        Toast.makeText(getContext(), "검색 결과 없음", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseKakaoLocationListDTO> call, Throwable t) {
                    Log.d("실패","통신 실패: "+ t.getMessage());
                }
            });
        } else {
            Toast.makeText(getContext(), "검색 결과 없음", Toast.LENGTH_LONG).show();
        }
    }

    public void getSearchXY(String address){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(KakaoMapAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        KakaoMapAPI kakaoMapAPI = retrofit.create(KakaoMapAPI.class);
        kakaoMapAPI.getAddressXY(address, 15).enqueue(new Callback<ResponseKakaoXYListDTO>() {
            @Override
            public void onResponse(Call<ResponseKakaoXYListDTO> call, Response<ResponseKakaoXYListDTO> response) {
                if (response.body().getDocuments().size() != 0) {
                    searchLongitude = response.body().getDocuments().get(0).getX();
                    searchLatitude = response.body().getDocuments().get(0).getY();
                    Log.d("성공", response.body().getDocuments().get(0).getAddress());
                } else {
                    searchLongitude = "200";
                    searchLatitude = "200";
                    Log.d("성공", "데이터 없음");
                }
            }
            @Override
            public void onFailure(Call<ResponseKakaoXYListDTO> call, Throwable t) {
                Log.d("실패","통신 실패: "+ t.getMessage());
            }
        });
    }

    public void moveToKakaoLocationDetail(KakaoLocationDTO location) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LocationAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LocationAPI locationAPI = retrofit.create(LocationAPI.class);

        RequestEnrollLocationDTO locationData = new RequestEnrollLocationDTO();
        locationData.setPlaceName(location.getPlaceName());
        locationData.setCategory(location.getCategory());
        locationData.setAddress(location.getAddress());
        locationData.setPhone(location.getPhone());
        locationData.setX(location.getX());
        locationData.setY(location.getY());
        locationData.setUid(LandingActivity.localUid);
        locationAPI.postLocation(locationData).enqueue(new Callback<ResponseEnrollLocationDTO>() {
            @Override
            public void onResponse(Call<ResponseEnrollLocationDTO> call, Response<ResponseEnrollLocationDTO> response) {
                if (response.body() != null) {
                    ResponseEnrollLocationDTO locationResultData = response.body();
                    Intent intent = new Intent(getActivity(), LocationDetailActivity.class);
                    intent.putExtra("id", locationResultData.getId());
                    startActivity(intent);
                    Log.d("LOCATION", "pid: " + locationResultData.getId());
                } else {
                    Log.d("성공", "데이터없음");
                }
            }

            @Override
            public void onFailure(Call<ResponseEnrollLocationDTO> call, Throwable t) {
                Toast.makeText(getContext(), "등록 실패", Toast.LENGTH_LONG).show();
                Log.d("실패", "통신 실패: " + t.getMessage());
            }
        });
    }

    public void setKeywords() {
        for(int i=0; i<tvTags.length; i++){
            TextView tv = tvTags[i];
            String tag = tags[i];
            int idx = i;
            tv.setOnClickListener(view -> {
                mapView.removeAllPOIItems();
                tagClicked(tv);
                switch (idx){
                    case 0: getRecommendLocation(tag);
                            break;
                    case 1:
                    case 4: getKeywordLocation(tag);
                            break;
                    case 2:
                    case 3: getCategoryLocation(tag);
                            break;
                }
            });
        }
    }

    public void tagClicked(TextView textView){
        for(TextView tv: tvTags){
            tv.setTextColor(getContext().getColor(R.color.black));
            tv.setBackground(getContext().getDrawable(R.drawable.location_roundedtag));
        }
        textView.setTextColor(getContext().getColor(R.color.white));
        textView.setBackground(getContext().getDrawable(R.drawable.location_roundedtag_green));
    }
}