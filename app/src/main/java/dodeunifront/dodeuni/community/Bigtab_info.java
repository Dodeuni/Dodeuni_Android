package dodeunifront.dodeuni.community;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dodeunifront.dodeuni.R;

public class Bigtab_info extends Fragment {
    Smalltab_chinfo fragment1_1;
    Smalltab_worry fragment1_2;
    Smalltab_review fragment1_3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bigtab_info, container, false);

        fragment1_1 = new Smalltab_chinfo();
        fragment1_2 = new Smalltab_worry();
        fragment1_3 = new Smalltab_review();

        getChildFragmentManager().beginTransaction().replace(R.id.ss_tab1_container, fragment1_1).commit();

        Button btn_info = rootView.findViewById(R.id.btn_changeinfo);
        Button btn_worry = rootView.findViewById(R.id.btn_worry);
        Button btn_review = rootView.findViewById(R.id.btn_review);


        btn_info.setBackgroundResource(R.drawable.btn_clicked_color);
        btn_info.setTextColor(Color.WHITE);

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_info.setBackgroundResource(R.drawable.btn_clicked_color);
                btn_info.setTextColor(Color.WHITE);
                btn_worry.setBackgroundResource(R.drawable.custombtn_smallcommunity);
                btn_worry.setTextColor(Color.BLACK);
                btn_review.setBackgroundResource(R.drawable.custombtn_smallcommunity);
                btn_review.setTextColor(Color.BLACK);
                getChildFragmentManager().beginTransaction().replace(R.id.ss_tab1_container, fragment1_1).commit();
            }
        });
        btn_worry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_info.setBackgroundResource(R.drawable.custombtn_smallcommunity);
                btn_info.setTextColor(Color.BLACK);
                btn_worry.setBackgroundResource(R.drawable.btn_clicked_color);
                btn_worry.setTextColor(Color.WHITE);
                btn_review.setBackgroundResource(R.drawable.custombtn_smallcommunity);
                btn_review.setTextColor(Color.BLACK);
                getChildFragmentManager().beginTransaction().replace(R.id.ss_tab1_container, fragment1_2).commit();


            }
        });

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_info.setBackgroundResource(R.drawable.custombtn_smallcommunity);
                btn_info.setTextColor(Color.BLACK);
                btn_worry.setBackgroundResource(R.drawable.custombtn_smallcommunity);
                btn_worry.setTextColor(Color.BLACK);
                btn_review.setBackgroundResource(R.drawable.btn_clicked_color);
                btn_review.setTextColor(Color.WHITE);
                getChildFragmentManager().beginTransaction().replace(R.id.ss_tab1_container, fragment1_3).commit();
            }
        });

        return rootView;
    }
}
