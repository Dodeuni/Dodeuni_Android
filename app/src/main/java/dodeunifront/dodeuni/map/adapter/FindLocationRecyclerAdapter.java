package dodeunifront.dodeuni.map.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapView;

import java.util.List;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.map.dto.response.ResponseLocationListDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseLocationDTO;

public class FindLocationRecyclerAdapter extends RecyclerView.Adapter<FindLocationRecyclerAdapter.ViewHolder> {
    private List<ResponseLocationDTO> locationResult;
    private int length;
    MapView mapView;

    public FindLocationRecyclerAdapter(MapView mapView){
        this.mapView = mapView;
    }
    public interface OnItemClickListener {
        void onItemClicked(ResponseLocationDTO locationData);
    }
    static private OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_find_location_recommend, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.onBind(locationResult.get(position), mapView);
    }

    public void setLocationResult(ResponseLocationListDTO result) {
        this.locationResult = result.getDocuments();
        length = result.getLength();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, category, address;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            title = (TextView) view.findViewById(R.id.tv_find_location_title);
            category = (TextView) view.findViewById(R.id.tv_find_location_category);
            address = (TextView) view.findViewById(R.id.tv_find_location_address);
        }

        void onBind(ResponseLocationDTO location, MapView mapView){
            /*double longitude = Double.parseDouble(location.getY());
            double latitude = Double.parseDouble(location.getX());

            MapPOIItem marker = new MapPOIItem();
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(longitude, latitude);
            marker.setItemName(location.getPlaceName());
            marker.setMapPoint(mapPoint);
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            marker.setCustomImageResourceId(R.drawable.location_green_midium);
            mapView.addPOIItem(marker);*/

            view.setOnClickListener(v -> mItemClickListener.onItemClicked(location));
            title.setText(location.getPlaceName());
            category.setText(location.getCategory());
            address.setText(location.getAddress());
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.length;
    }
}
