package dodeunifront.dodeuni.map.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapView;

import java.util.List;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.map.dto.AlertDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseAlertListDTO;

public class AlertRecyclerAdapter extends RecyclerView.Adapter<AlertRecyclerAdapter.ViewHolder> {
    private List<AlertDTO> alertList;
    private int length;

    public AlertRecyclerAdapter(){ }
    public interface OnItemClickListener {
        void onItemClicked(AlertDTO alert);
    }
    static private OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_alert, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.onBind(alertList.get(position));
    }

    public void setAlertList(List<AlertDTO> result) {
        this.alertList = result;
        length = alertList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, content, time;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            title = view.findViewById(R.id.tv_alert_title);
            content = view.findViewById(R.id.tv_alert_content);
            time = view.findViewById(R.id.tv_alert_time);
        }

        void onBind(AlertDTO alert){
            view.setOnClickListener(v -> mItemClickListener.onItemClicked(alert));
            title.setText(alert.getAlarm());
            content.setText(alert.getMain() + " > " + alert.getSub());
            time.setText(alert.getCreatedDateTime());
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.length;
    }
}
