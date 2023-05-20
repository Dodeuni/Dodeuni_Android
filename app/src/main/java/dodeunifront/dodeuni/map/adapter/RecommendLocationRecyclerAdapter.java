package dodeunifront.dodeuni.map.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapView;

import java.util.List;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.map.dto.response.ResponseRecommendLocationDTO;

public class RecommendLocationRecyclerAdapter extends RecyclerView.Adapter<RecommendLocationRecyclerAdapter.ViewHolder> {
    private List<ResponseRecommendLocationDTO> locationResult;
    private int length;
    MapView mapView;

    public interface OnItemClickListener {
        void onItemClicked(ResponseRecommendLocationDTO locationData);
    }
    static private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_location_recommend, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.onBind(locationResult.get(position));
    }

    public void setLocationResult(List<ResponseRecommendLocationDTO> result) {
        this.locationResult = result;
        length = result.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, category, distance;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            name = view.findViewById(R.id.tv_recommend_location_name);
            category = view.findViewById(R.id.tv_recommend_location_category);
            distance = view.findViewById(R.id.tv_recommend_location_distance);
        }

        void onBind(ResponseRecommendLocationDTO location){
            int dist = (int) (location.getDistance() * 1000);
            view.setOnClickListener(v -> mItemClickListener.onItemClicked(location));
            name.setText(location.getName());
            category.setText(location.getCategory());
            distance.setText("현재위치에서 " + dist + "m");
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.length;
    }
}
