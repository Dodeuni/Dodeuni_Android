package dodeunifront.dodeuni.map.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;
import dodeunifront.dodeuni.map.CurrentLocation;
import dodeunifront.dodeuni.map.adapter.KakaoLocationRecyclerAdapter;
import dodeunifront.dodeuni.map.dto.KakaoLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseKakaoLocationListDTO;
import dodeunifront.dodeuni.retroifit.KakaoRetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFindActivity extends AppCompatActivity {

    MapView mapView;
    ImageButton btnSearch, btnCurrentLocation;
    EditText editSearch;
    ViewGroup mapViewContainer;
    RecyclerView mRecyclerView;
    KakaoLocationRecyclerAdapter mRecyclerAdapter;
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

        btnSearch.setOnClickListener(view -> clickedSearch());
        editSearch.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_ENTER) clickedSearch();
            return true;
        });

        btnCurrentLocation.setOnClickListener(view -> moveToCurrentLocation());
    }

    public void onBackPressed() {
        super.onBackPressed();
        bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void initTopView(){
        TopView topView = findViewById(R.id.topview_location_find);
        topView.setOnButtonClickListener(() -> {
            mapViewContainer.removeView(mapView);
            finish();
        });
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

    public void initRecyclerView(){
        mRecyclerAdapter = new KakaoLocationRecyclerAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerAdapter.setLocationResult(searchResult);
        mRecyclerAdapter.setOnItemClickListener((locationData) -> {
            moveToPostLocation(locationData);
        });
    }

    public void clickedSearch(){
        mapView.removeAllPOIItems();
        String keyword = editSearch.getText().toString();
        editSearch.setText("");
        if(keyword.length() != 0){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
            searchKeyword(keyword);
        }
    }

    public void setMarkers(){
        MapPOIItem marker = new MapPOIItem();
        for(KakaoLocationDTO info: searchResult.getDocuments()){
            double lon = Double.parseDouble(info.getY());
            double lat = Double.parseDouble(info.getX());
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(lon, lat);
            marker.setItemName(info.getPlaceName());
            marker.setMapPoint(mapPoint);
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            marker.setCustomImageResourceId(R.drawable.location_green_midium);
            mapView.addPOIItem(marker);
        }
    }

    public void getCurrentLocation(){
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        CurrentLocation currentLocation = new CurrentLocation(lm, this);
        currentGeocoord = currentLocation.getCurrentLocation();
    }

    public void moveToCurrentLocation(){
        if(currentGeocoord != null){
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(currentGeocoord.getLatitude(), currentGeocoord.getLongitude()), true);
        }
    }

    public void moveToPostLocation(KakaoLocationDTO locationData){
        Intent intent = new Intent(this, LocationPostActivity.class);
        intent.putExtra("name", locationData.getPlaceName());
        intent.putExtra("category", locationData.getCategory());
        intent.putExtra("address", locationData.getAddress());
        intent.putExtra("phone", locationData.getPhone());
        intent.putExtra("x", locationData.getX());
        intent.putExtra("y", locationData.getY());
        startActivity(intent);
        finish();
    }

    public void searchKeyword(String keyword){
        MapPoint centerPoint = mapView.getMapCenterPoint();
        KakaoRetrofitBuilder.getKakaoMapAPI().getKeywordLocationList(keyword, centerPoint.getMapPointGeoCoord().longitude+"", centerPoint.getMapPointGeoCoord().latitude+"", 15, "distance").enqueue(new Callback<ResponseKakaoLocationListDTO>() {
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
                    Toast.makeText(getApplicationContext(), "검색 결과 없음", Toast.LENGTH_SHORT).show();
                    editSearch.setText("");
                }
            }
            @Override
            public void onFailure(Call<ResponseKakaoLocationListDTO> call, Throwable t) {
                Log.d("실패","통신 실패: "+ t.getMessage());
            }
        });
    }
}