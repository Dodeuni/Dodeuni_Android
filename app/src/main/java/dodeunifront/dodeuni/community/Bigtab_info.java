package dodeunifront.dodeuni.community;

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

        getChildFragmentManager().beginTransaction().replace(R.id.tab1_container, fragment1_1).commit();

        Button btn = rootView.findViewById(R.id.btn_changeinfo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.tab1_container, fragment1_1).commit();
            }
        });

        btn = rootView.findViewById(R.id.btn_worry);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.tab1_container, fragment1_2).commit();
            }
        });

        btn = rootView.findViewById(R.id.btn_review);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.tab1_container, fragment1_3).commit();
            }
        });

        return rootView;
    }
}
