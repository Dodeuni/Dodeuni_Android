package dodeunifront.dodeuni.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import net.daum.mf.map.api.MapView;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;

public class FindLocationMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location_map);

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(
                findViewById(R.id.drawer_location_find)
        );
        behavior.setPeekHeight(350);

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        TopView topView = findViewById(R.id.topview_location_find);
        topView.setOnButtonClickListener(() -> {
            mapViewContainer.removeView(mapView);
            finish();
        });
    }
}