package dodeunifront.dodeuni;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class TopView extends FrameLayout {
    TextView tv_title;
    ImageButton btn_back;
    String title;

    public TopView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_top, this);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TopView,
                0, 0);

        tv_title = findViewById(R.id.tv_title);
        btn_back = findViewById(R.id.imgbtn_back);
        try {
            title = a.getString(R.styleable.TopView_title);
        } finally {
            a.recycle();
        }
        setTitle(title);

        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mButtonClickListener.onButtonClicked();
            }
        });
    }

    public interface OnButtonClickListener {
        void onButtonClicked();
    }

    private OnButtonClickListener mButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener a_listener){
        mButtonClickListener = a_listener;
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }
}
