package dodeunifront.dodeuni.community.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import dodeunifront.dodeuni.R;

public class MenuCommunityDialog extends Dialog implements View.OnClickListener {
    private CustomDialogInterface customDialogInterface;
    private Context context;
    private TextView mDlgdelete_tv, mDlgmodify_tv;
    public String mBtnName;

    public MenuCommunityDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_menu_community);

        mDlgdelete_tv = findViewById(R.id.tv_menu_community_delete);
        mDlgmodify_tv = findViewById(R.id.tv_menu_community_modify);

        mDlgdelete_tv.setOnClickListener(this);
        mDlgmodify_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_menu_community_delete:
                //mBtnName 변수에 "ok btn"이라는 string 데이터를 넣고, 메인 액티비티로 보냄
                mBtnName = "delete tv";
                customDialogInterface.okBtnClicked(mBtnName);
                dismiss();
                break;
            case R.id.tv_menu_community_modify:
                //mBtnName 변수에 "no btn"이라는 string 데이터를 넣고, 메인 액티비티로 보냄
                mBtnName = "moduty tv";
                customDialogInterface.noBtnClicked(mBtnName);
                dismiss();
                break;
        }
    }
    public void setDialogListener(CustomDialogInterface customDialogInterface){
        this.customDialogInterface = customDialogInterface;
    }
}
