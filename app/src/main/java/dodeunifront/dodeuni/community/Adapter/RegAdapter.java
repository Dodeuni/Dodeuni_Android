package dodeunifront.dodeuni.community.Adapter;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.DTO.CommunityListResponseDTO;
import dodeunifront.dodeuni.community.DetailCommunityActivity;

public class RegAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<CommunityListResponseDTO> mdataList = null;
    Long userId;
    String nickname;
    public interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }
    //리스너 객체 참조 변수
    private RegAdapter.OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(RegAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }
    public RegAdapter(ArrayList<CommunityListResponseDTO> dataList,Long userId,String nickname){
            mdataList = dataList;
            this.userId = userId;
            this.nickname = nickname;
    }

@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.itemlist_community, parent, false);
        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((MyViewHolder) viewHolder).title.setText(mdataList.get(position).getTitle());
        ((MyViewHolder) viewHolder).content.setText(mdataList.get(position).getContent());
        ((MyViewHolder) viewHolder).nickname.setText(mdataList.get(position).getNickname());
        String date_fi = mdataList.get(position).getCreatedDateTime();
        //String createDateTimeParse0 = date_fi.substring(0,date_fi.indexOf("T"));
        //String createDateTimeParse1 = date_fi.substring(date_fi.indexOf("T")+1,date_fi.indexOf("."));
        int idx = date_fi.indexOf('T');
        String date = date_fi.substring(0, idx);
        String time = date_fi.substring(idx+1, idx+6);
//        String createDateTimeParse0 = "날짜";
//        String createDateTimeParse1 = "시간";
//        String createDatTimeresult = createDateTimeParse0 +" "+createDateTimeParse1;
        ((MyViewHolder) viewHolder).createdate.setText(date + " " + time);
        Glide.with(viewHolder.itemView)
            .load(mdataList.get(position).getThumbnailUrl())
            .into(((MyViewHolder) viewHolder).imageView);


//    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int mPosition = viewHolder.getBindingAdapterPosition();
//                Context context = view.getContext();
//                Intent detail = new Intent(context, DetailCommunityActivity.class);
//
//                detail.putExtra("id",mdataList.get(mPosition).getId());
//                detail.putExtra("userid",mdataList.get(mPosition).getUserId());
//                detail.putExtra("login_userId",userId);
//                detail.putExtra("nickname",nickname);
//
//
//                context.startActivity(detail);
//            }
//        });
        }

@Override
public int getItemCount()
        {
        return mdataList.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    TextView content;
    TextView nickname;
    TextView createdate;
    ImageView imageView;

    MyViewHolder(View itemView)
    {
        super(itemView);
        title = itemView.findViewById(R.id.tv_community_title);
        content = itemView.findViewById(R.id.tv_community_content);
        nickname = itemView.findViewById(R.id.tv_community_writer);
        createdate = itemView.findViewById(R.id.tv_community_time);
        imageView = itemView.findViewById(R.id.iv_communitylist);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition ();
                if (position!=RecyclerView.NO_POSITION){
                    if (mListener!=null){
                        mListener.onItemClick (itemView,position);
                    }
                }
            }
        });

    }
}
}

