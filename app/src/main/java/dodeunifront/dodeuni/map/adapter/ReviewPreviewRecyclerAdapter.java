package dodeunifront.dodeuni.map.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.map.dto.response.ResponseReviewDTO;

public class ReviewPreviewRecyclerAdapter extends RecyclerView.Adapter<ReviewPreviewRecyclerAdapter.ViewHolder> {
    private List<ResponseReviewDTO> reviewResult;
    private int length;
    public interface OnItemClickListener {
        void onItemClicked(ResponseReviewDTO reviewData);
    }
    static private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_preview_review, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.onBind(reviewResult.get(position));
    }

    public void setReviewResult(List<ResponseReviewDTO> result, int length) {
        this.reviewResult = result;
        this.length = length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle, tvContent, tvNickname, tvTime;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            tvTitle = view.findViewById(R.id.tv_preview_review_title);
            tvContent = view.findViewById(R.id.tv_preview_review_content);
            tvNickname = view.findViewById(R.id.tv_preview_review_nickname);
            tvTime = view.findViewById(R.id.tv_preview_review_time);
        }

        void onBind(ResponseReviewDTO review){
            int idx = review.getCreatedDateTime().indexOf('T');
            String date = review.getCreatedDateTime().substring(0, idx);
            String time = review.getCreatedDateTime().substring(idx+1, idx+6);
            view.setOnClickListener(v -> mItemClickListener.onItemClicked(review));
            tvTitle.setText(review.getTitle());
            tvContent.setText(review.getContent());
            tvNickname.setText(review.getNickname());
            tvTime.setText(date);
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.length;
    }
}
