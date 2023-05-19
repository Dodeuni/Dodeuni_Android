package dodeunifront.dodeuni.community.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.DTO.CommunityListResponseDTO;
import dodeunifront.dodeuni.community.DetailCommunityActivity;

public class RegAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<CommunityListResponseDTO> mdataList = null;
    public RegAdapter(ArrayList<CommunityListResponseDTO> dataList){
            mdataList = dataList;
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
        ((MyViewHolder) viewHolder).createdate.setText(mdataList.get(position).getCreatedDateTime());
        Glide.with(viewHolder.itemView)
            .load(mdataList.get(position).getThumbnailUrl())
            .into(((MyViewHolder) viewHolder).imageView);


    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = viewHolder.getBindingAdapterPosition();
                Context context = view.getContext();
                Intent detail = new Intent(context, DetailCommunityActivity.class);

                detail.putExtra("id",mdataList.get(mPosition).getId());
                detail.putExtra("userid",mdataList.get(mPosition).getUserId());
                context.startActivity(detail);
            }
        });
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

    }
}
}

