package dodeunifront.dodeuni.Hue;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dodeunifront.dodeuni.R;

public class HueAdapter extends RecyclerView.Adapter<HueAdapter.CustomViewHolder>{
    private ArrayList<HueData> hueData;
    private Context mcontext;

    public interface OnItemClickListener{
        void onItemClick(View v, int positoin);
    }
    private HueAdapter.OnItemClickListener mListener = null;
    public void setOnItemClickListener(HueAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public HueAdapter (ArrayList<HueData> list) {
        this.hueData = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.itemlist_hue_me, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder viewholder, int position) {
        String text = hueData.get(position).ev_huemessage;
        String time = hueData.get(position).sendtime;
        viewholder.tv_metext.setText(hueData.get(position).getEv_huemessage());
        viewholder.tv_metext_time.setText(hueData.get(position).getSendtime());
    }

    @Override
    public int getItemCount() {
        return (null != hueData ? hueData.size() : 0);
    }

    //이름,등록일,개수 만든거 구성
    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private TextView tv_metext;
        private TextView tv_metext_time;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_metext = (TextView)itemView.findViewById(R.id.tv_hue_me);
            tv_metext_time=(TextView)itemView.findViewById(R.id.tv_hue_time_me);
            itemView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다. ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.

        }
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        };
    }
}
