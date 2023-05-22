package dodeunifront.dodeuni.Hue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dodeunifront.dodeuni.R;

public class HueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HuePostDTO> mdataList = null;

    HueAdapter(ArrayList<HuePostDTO> dataList){
        mdataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == ViewType.LEFT_CHAT)
        {
            view = inflater.inflate(R.layout.itemlist_hue_other, parent, false);
            return new LeftViewHolder(view);
        }
        else
        {
            view = inflater.inflate(R.layout.itemlist_hue_me, parent, false);
            return new RightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof LeftViewHolder)
        {
            ((LeftViewHolder) viewHolder).content.setText(mdataList.get(position).getEv_huemessage());
            ((LeftViewHolder) viewHolder).time.setText(mdataList.get(position).getSendtime());
        }
        else
        {
            ((RightViewHolder) viewHolder).content.setText(mdataList.get(position).getEv_huemessage());
            ((RightViewHolder) viewHolder).time.setText(mdataList.get(position).getSendtime());
        }

    }
    @Override
    public int getItemCount()
    {
        return mdataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mdataList.get(position).getViewType();
    }
    public class LeftViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView time;

        LeftViewHolder(View itemView)
        {
            super(itemView);
            content = itemView.findViewById(R.id.tv_hue_other);
            time = itemView.findViewById(R.id.tv_hue_time_other);
        }
    }

    public class RightViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView time;

        RightViewHolder(View itemView)
        {
            super(itemView);
            content = itemView.findViewById(R.id.tv_hue_me);
            time = itemView.findViewById(R.id.tv_hue_time_me);
        }
    }

}
