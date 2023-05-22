package dodeunifront.dodeuni.map.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.map.CurrentLocation;
import dodeunifront.dodeuni.map.adapter.FindLocationRecyclerAdapter;
import dodeunifront.dodeuni.map.api.KakaoMapAPI;
import dodeunifront.dodeuni.map.dto.KakaoLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseKakaoLocationListDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationFindActivity extends AppCompatActivity {

    MapView mapView;
    ImageButton btnSearch, btnCurrentLocation;
    EditText editSearch;
    ViewGroup mapViewContainer;
    RecyclerView mRecyclerView;
    FindLocationRecyclerAdapter mRecyclerAdapter;
    LinearLayout bottomSheetLayout;
    ResponseKakaoLocationListDTO searchResult;
    BottomSheetBehavior<View> bottomSheet;
    CurrentLocation.Geocoord currentGeocoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);

        btnSearch = findViewById(R.id.imgBtn_location_search);
        editSearch = findViewById(R.id.edit_location);
        mRecyclerView = findViewById(R.id.rv_location_find_recommend);
        btnCurrentLocation = findViewById(R.id.imgBtn_location_find_current);
        bottomSheetLayout = findViewById(R.id.drawer_location_find);

        getCurrentLocation();

        initBottomSheet();
        initMapView();
        initTopView();

        setSearchBtn();

        btnCurrentLocation.setOnClickListener(view -> {
            moveToCurrentLocation();
        });
    }

    public void getCurrentLocation(){
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        CurrentLocation currentLocation = new CurrentLocation(lm, this);
        currentGeocoord = currentLocation.getCurrentLocation();
    }

    public void initBottomSheet(){
        bottomSheet = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheet.setPeekHeight(500);
        bottomSheet.setMaxHeight(2000);
    }

    public void initMapView(){
        mapView = new MapView(this);
        mapViewContainer = findViewById(R.id.map_location_view);
        mapViewContainer.addView(mapView);

        moveToCurrentLocation();
    }

    public void initTopView(){
        TopView topView = findViewById(R.id.topview_location_find);
        topView.setOnButtonClickListener(() -> {
            mapViewContainer.removeView(mapView);
            finish();
        });
    }

    public void setSearchBtn(){
        btnSearch.setOnClickListener(view -> {
            mapView.removeAllPOIItems();
            String keyword = editSearch.getText().toString();
            if(keyword.length() != 0){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                searchKeyword(keyword);
            }
        });
    }

    public void moveToCurrentLocation(){
        if(currentGeocoord != null){
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(currentGeocoord.getLatitude(), currentGeocoord.getLongitude()), true);
        }
    }

    public void searchKeyword(String keyword){
        MapPoint centerPoint = mapView.getMapCenterPoint();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(KakaoMapAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        KakaoMapAPI kakaoMapAPI = retrofit.create(KakaoMapAPI.class);
        kakaoMapAPI.getKeywordLocationList(keyword, centerPoint.getMapPointGeoCoord().longitude+"", centerPoint.getMapPointGeoCoord().latitude+"", 15, "distance").enqueue(new Callback<ResponseKakaoLocationListDTO>() {
            @Override
            public void onResponse(Call<ResponseKakaoLocationListDTO> call, Response<ResponseKakaoLocationListDTO> response) {
                if (response.body().getLength() != 0) {
                    searchResult = response.body();
                    double longitude = Double.parseDouble(searchResult.getDocuments().get(0).getX());
                    double latitude = Double.parseDouble(searchResult.getDocuments().get(0).getY()) - 0.002;
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
                    setMarkers();
                    initRecyclerView();
                    Log.d("성공", searchResult.getDocuments().get(0).getAddress());
                } else {

                }
            }
            @Override
            public void onFailure(Call<ResponseKakaoLocationListDTO> call, Throwable t) {
                Log.d("실패","통신 실패: "+ t.getMessage());
            }
        });
    }

    public void setMarkers(){
        for(int i=0; i<searchResult.getDocuments().size(); i++){
            KakaoLocationDTO info = searchResult.getDocuments().get(i);
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

    public void initRecyclerView(){
        mRecyclerAdapter = new FindLocationRecyclerAdapter(mapView);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerAdapter.setLocationResult(searchResult);
        mRecyclerAdapter.setOnItemClickListener((locationData) -> {
            Intent intent = new Intent(this, LocationPostActivity.class);
            intent.putExtra("name", locationData.getPlaceName());
            intent.putExtra("category", locationData.getCategory());
            intent.putExtra("address", locationData.getAddress());
            intent.putExtra("phone", locationData.getPhone());
            intent.putExtra("x", locationData.getX());
            intent.putExtra("y", locationData.getY());
            startActivity(intent);
            finish();
            Toast.makeText(this, "clicked: " + locationData.getPlaceName(), Toast.LENGTH_LONG).show();
        });
    }
}