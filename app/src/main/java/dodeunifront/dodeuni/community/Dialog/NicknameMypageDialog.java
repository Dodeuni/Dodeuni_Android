package dodeunifront.dodeuni.community.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import dodeunifront.dodeuni.R;

public class NicknameMypageDialog  extends Dialog implements View.OnClickListener{ private CustomDialogInterface customDialogInterface;
    private Context context;
    private Button mDlgOkBtn, mDlgNoBtn;
    public String mBtnName;

    public NicknameMypageDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_nickname);

        mDlgOkBtn = findViewById(R.id.btn_nickname_ok);
        mDlgNoBtn = findViewById(R.id.btn_nickname_cancel);

        mDlgOkBtn.setOnClickListener(this);
        mDlgNoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_nickname_ok:
                //mBtnName 변수에 "ok btn"이라는 string 데이터를 넣고, 메인 액티비티로 보냄
                mBtnName = "ok btn";
                customDialogInterface.okBtnClicked(mBtnName);
                dismiss();
                break;
            case R.id.btn_edit_cancel:
                //mBtnName 변수에 "no btn"이라는 string 데이터를 넣고, 메인 액티비티로 보냄
                mBtnName = "no btn";
                customDialogInterface.noBtnClicked(mBtnName);
                dismiss();
                break;
        }
    }
    public void setDialogListener(CustomDialogInterface customDialogInterface){
        this.customDialogInterface = customDialogInterface;
    }
}
