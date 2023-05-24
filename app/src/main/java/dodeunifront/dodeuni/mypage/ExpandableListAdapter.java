package dodeunifront.dodeuni.mypage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dodeunifront.dodeuni.R;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mcontext;
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public static final int ADRESS = 2;


    private List<Item> data;

    public ExpandableListAdapter(List<Item> data, Context mcontext) {
        this.data = data;
        this.mcontext =mcontext;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        int subItemPaddingLeft_ = (int) (18 * dp);
        int subItemPaddingTopAndBottom_ = (int) (2 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.itemlist_mypage, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                TextView itemTextView = new TextView(context);
                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
                itemTextView.setTextColor(0x88000000);
                itemTextView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return new RecyclerView.ViewHolder(itemTextView) {
                };
            case ADRESS:
                TextView itemTextView_ = new TextView(context);
                itemTextView_.setPadding(subItemPaddingLeft_, subItemPaddingTopAndBottom_, 0, subItemPaddingTopAndBottom_);
                itemTextView_.setTextColor(Color.parseColor("#008000"));
                itemTextView_.setTextSize(19);
                itemTextView_.setPaintFlags(itemTextView_.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                itemTextView_.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                itemTextView_.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                itemTextView_.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                return new RecyclerView.ViewHolder(itemTextView_) {
                };
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.text);
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.exitbutton);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.img_down_arrow);
                }
                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            while (data.size() > pos + 1 && data.get(pos + 1).type == ADRESS){
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.img_down_arrow);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.exitbutton);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                TextView itemTextView = (TextView) holder.itemView;
                itemTextView.setText(data.get(position).text);
                break;
            case ADRESS:
                TextView itemTextView_ = (TextView) holder.itemView;
//                itemTextView_.setText(data.get(position).uri);
                itemTextView_.setText("바로가기");
                itemTextView_.setTextSize(14);
                String ss = data.get(position).uri;
                itemTextView_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(ss));
                        mcontext.startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.tv_mypage_site);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.iv_mypagelist);
        }
    }

    public static class Item {
        public int type;
        public String text;
        String uri;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
        public Item(int type, String uri,int ints) {
            this.type = type;
            this.uri = uri;
        }
    }
}
