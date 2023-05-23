package dodeunifront.dodeuni.map.view;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;

import dodeunifront.dodeuni.LandingActivity;
import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.map.CurrentLocation;
import dodeunifront.dodeuni.map.MarkerEventListener;
import dodeunifront.dodeuni.retroifit.KakaoRetrofitBuilder;
import dodeunifront.dodeuni.map.adapter.KakaoLocationRecyclerAdapter;
import dodeunifront.dodeuni.map.adapter.RecommendLocationRecyclerAdapter;
import dodeunifront.dodeuni.map.dto.request.RequestEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.request.RequestRecommendLocationDTO;
import dodeunifront.dodeuni.map.dto.KakaoLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseKakaoLocationListDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseKakaoXYListDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseRecommendLocationDTO;
import dodeunifront.dodeuni.retroifit.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment {

    boolean isKakaoMarkers = false;
    private View v;
    private ViewGroup mapViewContainer;
    private MapView mapView;
    String searchLatitude, searchLongitude;
    String currentLatitude, currentLongitude;
    BottomSheetBehavior<View> bottomSheet;
    CurrentLocation.Geocoord currentGeocoord;
    ImageButton btnCurrentLocation, btnLocationSearch;
    RecyclerView mRecyclerView;
    EditText editSearch;
    ResponseKakaoLocationListDTO kakaoSearchResult;
    RecommendLocationRecyclerAdapter mRecommendRecyclerAdapter;
    KakaoLocationRecyclerAdapter mkeywordRecyclerAdapter;
    List<ResponseRecommendLocationDTO> recommendResult;
    RequestRecommendLocationDTO recommendRequest = new RequestRecommendLocationDTO();
    TextView tvRecommend, tvCenter, tvKinder, tvSchool, tvSpecialS;
    TextView tvTags[];
    String[] tags = {"", "아동발달", "PS3", "SC4", "어린이병원"};
    RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
    MarkerEventListener markerEventListener = new MarkerEventListener();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCurrentLocation();
        currentLongitude = currentGeocoord.getLongitude() + "";
        currentLatitude = currentGeocoord.getLatitude() + "";
        searchLongitude = currentLongitude;
        searchLatitude = currentLatitude;
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
        TextView tvLocationNew = v.findViewById(R.id.tv_location_new);
        tvTags = new TextView[]{tvRecommend, tvCenter, tvKinder, tvSchool, tvSpecialS};

        initMapView();
        initBottomSheet();

        tvLocationNew.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LocationFindActivity.class);
            startActivity(intent);
        });

        btnLocationSearch.setOnClickListener(view -> clickedSearch());

        editSearch.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_ENTER) clickedSearch();
            return true;
        });

        btnCurrentLocation.setOnClickListener(view -> moveToCurrentLocation());
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        getRecommendLocation();
        tagClicked(tvTags[0]);
        setKeywords();
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
        mapView.setPOIItemEventListener(markerEventListener);
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
        });
    }

    public void initRecyclerViewWithKeyword(){
        mkeywordRecyclerAdapter = new KakaoLocationRecyclerAdapter();
        mRecyclerView.setAdapter(mkeywordRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mkeywordRecyclerAdapter.setLocationResult(kakaoSearchResult);
        mkeywordRecyclerAdapter.setOnItemClickListener((locationData) -> {
            moveToKakaoLocationDetail(locationData);
        });
    }

    public void setRecommendMarkers(){
        for(int i=0; i<recommendResult.size(); i++){
            ResponseRecommendLocationDTO info = recommendResult.get(i);
            double lon = info.getY();
            double lat = info.getX();
            MapPOIItem marker = new MapPOIItem();
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(lon, lat);
            marker.setItemName(info.getName());
            marker.setMapPoint(mapPoint);
            marker.setTag(i);
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            marker.setCustomImageResourceId(R.drawable.location_green_midium);
            mapView.addPOIItem(marker);
        }
    }

    public void setKeywordMarkers(){
        for(int i=0; i<kakaoSearchResult.getDocuments().size(); i++){
            KakaoLocationDTO info = kakaoSearchResult.getDocuments().get(i);
            double lon = Double.parseDouble(info.getY());
            double lat = Double.parseDouble(info.getX());
            MapPOIItem marker = new MapPOIItem();
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(lon, lat);
            marker.setItemName(info.getPlaceName());
            marker.setMapPoint(mapPoint);
            marker.setTag(i);
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            marker.setCustomImageResourceId(R.drawable.location_green_midium);
            mapView.addPOIItem(marker);
        }
    }

    public void clickedSearch(){
        tagClicked(tvTags[0]);
        mapView.removeAllPOIItems();
        mRecommendRecyclerAdapter = new RecommendLocationRecyclerAdapter();
        mRecyclerView.setAdapter(mRecommendRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        String keyword = editSearch.getText().toString();
        tagClicked(tvTags[0]);
        if(keyword.length() != 0){
            tags[0] = keyword;
            getSearchXY(keyword);
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            tags[0] = "";
            searchLatitude = currentGeocoord.getLatitude() + "";
            searchLongitude = currentGeocoord.getLongitude() + "";
            getRecommendLocation();
        }
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
                    case 0: getRecommendLocation();
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

    public void moveToCurrentLocation(){
        if(currentGeocoord != null){
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(currentGeocoord.getLatitude(), currentGeocoord.getLongitude()), true);
        }
    }

    public void getRecommendLocation(){
        if(searchLongitude != "200" && searchLatitude != "200") {
            recommendRequest.setX(Double.parseDouble(searchLongitude));
            recommendRequest.setY(Double.parseDouble(searchLatitude));
            recommendRequest.setKeyword("");
            retrofitBuilder.getLocationAPI().postRecommendList(recommendRequest).enqueue(new Callback<List<ResponseRecommendLocationDTO>>() {
                @Override
                public void onResponse(Call<List<ResponseRecommendLocationDTO>> call, Response<List<ResponseRecommendLocationDTO>> response) {
                    if (response.body() != null && response.body().size() != 0) {
                        recommendResult = response.body();
                        double longitude = recommendResult.get(0).getX();
                        double latitude = recommendResult.get(0).getY() - 0.002;
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
                        initRecyclerViewWithRecommend();
                        setRecommendMarkers();
                        isKakaoMarkers = false;
                    } else {
                        Toast.makeText(getContext(), "검색 결과 없음", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<ResponseRecommendLocationDTO>> call, Throwable t) {
                    Log.d("실패","통신 실패: "+ t.getMessage());
                }
            });
        } else {
            Toast.makeText(getContext(), "검색 결과 없음", Toast.LENGTH_LONG).show();
        }
    }

    public void getKeywordLocation(String keyword){
        if(searchLongitude != "200" && searchLatitude != "200") {
            KakaoRetrofitBuilder.getKakaoMapAPI().getKeywordLocationList(keyword, searchLongitude, searchLatitude, 15, "distance").enqueue(new Callback<ResponseKakaoLocationListDTO>() {
                @Override
                public void onResponse(Call<ResponseKakaoLocationListDTO> call, Response<ResponseKakaoLocationListDTO> response) {
                    if (response.body().getLength() != 0) {
                        mapView.removeAllPOIItems();
                        kakaoSearchResult = response.body();
                        double longitude = Double.parseDouble(kakaoSearchResult.getDocuments().get(0).getX());
                        double latitude = Double.parseDouble(kakaoSearchResult.getDocuments().get(0).getY()) - 0.002;
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
                        setKeywordMarkers();
                        isKakaoMarkers = true;
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
            KakaoRetrofitBuilder.getKakaoMapAPI().getCategoryLocationList(category, searchLongitude, searchLatitude, 15, "distance").enqueue(new Callback<ResponseKakaoLocationListDTO>() {
                @Override
                public void onResponse(Call<ResponseKakaoLocationListDTO> call, Response<ResponseKakaoLocationListDTO> response) {
                    if (response.body().getLength() != 0) {
                        kakaoSearchResult = response.body();
                        double longitude = Double.parseDouble(kakaoSearchResult.getDocuments().get(0).getX());
                        double latitude = Double.parseDouble(kakaoSearchResult.getDocuments().get(0).getY()) - 0.002;
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
                        setKeywordMarkers();
                        isKakaoMarkers = true;
                        initRecyclerViewWithKeyword();
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
        KakaoRetrofitBuilder.getKakaoMapAPI().getAddressXY(address, 15).enqueue(new Callback<ResponseKakaoXYListDTO>() {
            @Override
            public void onResponse(Call<ResponseKakaoXYListDTO> call, Response<ResponseKakaoXYListDTO> response) {
                if (response.body().getDocuments().size() != 0) {
                    searchLongitude = response.body().getDocuments().get(0).getX();
                    searchLatitude = response.body().getDocuments().get(0).getY();
                    getRecommendLocation();
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

        RequestEnrollLocationDTO locationData = new RequestEnrollLocationDTO();
        locationData.setPlaceName(location.getPlaceName());
        locationData.setCategory(location.getCategory());
        locationData.setAddress(location.getAddress());
        locationData.setPhone(location.getPhone());
        locationData.setX(location.getX());
        locationData.setY(location.getY());
        locationData.setUid(LandingActivity.localUid);
        retrofitBuilder.getLocationAPI().postLocation(locationData).enqueue(new Callback<ResponseEnrollLocationDTO>() {
            @Override
            public void onResponse(Call<ResponseEnrollLocationDTO> call, Response<ResponseEnrollLocationDTO> response) {
                if (response.body() != null) {
                    ResponseEnrollLocationDTO locationResultData = response.body();
                    Intent intent = new Intent(getActivity(), LocationDetailActivity.class);
                    intent.putExtra("id", locationResultData.getId());
                    startActivity(intent);
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

    public class MarkerEventListener implements MapView.POIItemEventListener {
        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
            if(isKakaoMarkers) {
                moveToKakaoLocationDetail(kakaoSearchResult.getDocuments().get(mapPOIItem.getTag()));
            } else {
                Intent intent = new Intent(getContext(), LocationDetailActivity.class);
                intent.putExtra("id", recommendResult.get(mapPOIItem.getTag()).getId());
                startActivity(intent);
            }

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        }

        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        }
    }

}