package dodeunifront.dodeuni.map.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapView;

import java.util.List;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.map.dto.response.ResponseKakaoLocationListDTO;
import dodeunifront.dodeuni.map.dto.KakaoLocationDTO;

public class KakaoLocationRecyclerAdapter extends RecyclerView.Adapter<KakaoLocationRecyclerAdapter.ViewHolder> {
    private List<KakaoLocationDTO> locationResult;
    private int length;

    public KakaoLocationRecyclerAdapter(){
    }
    public interface OnItemClickListener {
        void onItemClicked(KakaoLocationDTO locationData);
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
        viewHolder.onBind(locationResult.get(position));
    }

    public void setLocationResult(ResponseKakaoLocationListDTO result) {
        this.locationResult = result.getDocuments();
        length = result.getLength();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, category, address;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            title = view.findViewById(R.id.tv_find_location_title);
            category = view.findViewById(R.id.tv_find_location_category);
            address = view.findViewById(R.id.tv_find_location_address);
        }

        void onBind(KakaoLocationDTO location){
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
