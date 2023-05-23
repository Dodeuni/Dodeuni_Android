package dodeunifront.dodeuni.community;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import dodeunifront.dodeuni.R;

public class Bigtab_infoFragment extends Fragment {
    Smalltab_chinfoFragment fragment1_1;
    Smalltab_worryFragment fragment1_2;
    Smalltab_reviewFragment fragment1_3;

    Long userId;
    String nickname;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bigtab_info, container, false);

        userId = getArguments().getLong("userId",-1);
        nickname = getArguments().getString("nickname");

        fragment1_1 = new Smalltab_chinfoFragment();
        fragment1_2 = new Smalltab_worryFragment();
        fragment1_3 = new Smalltab_reviewFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("userId", userId);
        bundle.putString("nickname",nickname);
        FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
        fragment1_1.setArguments(bundle);
        transaction2.replace(R.id.ss_tab1_container, fragment1_1);
        transaction2.commit();

        TextView btn_info = rootView.findViewById(R.id.btn_changeinfo);
        TextView btn_worry = rootView.findViewById(R.id.btn_worry);
        TextView btn_review = rootView.findViewById(R.id.btn_review);


        btn_info.setBackgroundResource(R.drawable.btn_clicked_color);
        btn_info.setTextColor(Color.WHITE);

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_info.setBackgroundResource(R.drawable.btn_clicked_color);
                btn_info.setTextColor(Color.WHITE);
                btn_worry.setBackgroundResource(R.drawable.location_roundedtag);
                btn_worry.setTextColor(Color.BLACK);
                btn_review.setBackgroundResource(R.drawable.location_roundedtag);
                btn_review.setTextColor(Color.BLACK);

                Bundle bundle = new Bundle();
                bundle.putLong("userId", userId);
                bundle.putString("nickname",nickname);
                FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragment1_1.setArguments(bundle);
                transaction2.replace(R.id.ss_tab1_container, fragment1_1);
                transaction2.commit();

//                getChildFragmentManager().beginTransaction().replace(R.id.ss_tab1_container, fragment1_1).commit();
            }
        });
        btn_worry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_info.setBackgroundResource(R.drawable.location_roundedtag);
                btn_info.setTextColor(Color.BLACK);
                btn_worry.setBackgroundResource(R.drawable.btn_clicked_color);
                btn_worry.setTextColor(Color.WHITE);
                btn_review.setBackgroundResource(R.drawable.location_roundedtag);
                btn_review.setTextColor(Color.BLACK);

                Bundle bundle = new Bundle();
                bundle.putLong("userId", userId);
                bundle.putString("nickname",nickname);
                FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragment1_2.setArguments(bundle);
                transaction2.replace(R.id.ss_tab1_container, fragment1_2);
                transaction2.commit();

//                getChildFragmentManager().beginTransaction().replace(R.id.ss_tab1_container, fragment1_2).commit();


            }
        });

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_info.setBackgroundResource(R.drawable.location_roundedtag);
                btn_info.setTextColor(Color.BLACK);
                btn_worry.setBackgroundResource(R.drawable.location_roundedtag);
                btn_worry.setTextColor(Color.BLACK);
                btn_review.setBackgroundResource(R.drawable.btn_clicked_color);
                btn_review.setTextColor(Color.WHITE);

                Bundle bundle = new Bundle();
                bundle.putLong("userId", userId);
                bundle.putString("nickname",nickname);
                FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragment1_3.setArguments(bundle);
                transaction2.replace(R.id.ss_tab1_container, fragment1_3);
                transaction2.commit();

//                getChildFragmentManager().beginTransaction().replace(R.id.ss_tab1_container, fragment1_3).commit();
            }
        });

        return rootView;
    }
}
