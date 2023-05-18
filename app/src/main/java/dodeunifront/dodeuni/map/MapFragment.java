package dodeunifront.dodeuni.map;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import net.daum.mf.map.api.MapView;

import dodeunifront.dodeuni.R;

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

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_map, container, false);
        initMapView();

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(
                v.findViewById(R.id.drawer_location_recommend)
        );
        behavior.setPeekHeight(350);

        TextView tv_new = v.findViewById(R.id.tv_location_new);
        tv_new.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LocationFindActivity.class);
            startActivity(intent);
        });

        // Inflate the layout for this fragment
        return v;
    }

    private void initMapView(){
        mapView = new MapView(getContext());
        mapViewContainer = v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
    }
}