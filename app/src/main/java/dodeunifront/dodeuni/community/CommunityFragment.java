package dodeunifront.dodeuni.community;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import dodeunifront.dodeuni.R;

public class CommunityFragment extends Fragment {
    TabLayout tabRoot;
    Bigtab_info fragment1;
    Bigtab_store fragment2;
    Bigtab_meeting fragment3;
    Boolean isAllFabsVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_community, container, false);
        fragment1 = new Bigtab_info();
        fragment2 = new Bigtab_store();
        fragment3 = new Bigtab_meeting();


        tabRoot = view.findViewById(R.id.tabRoot);


        tabRoot.removeAllTabs();
        tabRoot.addTab(tabRoot.newTab().setText("정보"));
        tabRoot.addTab(tabRoot.newTab().setText("장터"));
        tabRoot.addTab(tabRoot.newTab().setText("모임"));

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.tab_container, fragment1).commit();

        ExtendedFloatingActionButton fab = view.findViewById(R.id.fab_main);

        FloatingActionButton fab_info = view.findViewById(R.id.fab_info);
        FloatingActionButton fab_store = view.findViewById(R.id.fab_store);
        FloatingActionButton fab_meeting = view.findViewById(R.id.fab_meeting);

        TextView fab_text_info = view.findViewById(R.id.tv_fab_info);
        TextView fab_text_store = view.findViewById(R.id.tv_fab_store);
        TextView fab_text_meeting = view.findViewById(R.id.tv_fab_meeting);

        fab_info.setVisibility(View.GONE);
        fab_store.setVisibility(View.GONE);
        fab_meeting.setVisibility(View.GONE);

        fab_text_info.setVisibility(View.GONE);
        fab_text_store.setVisibility(View.GONE);
        fab_text_meeting.setVisibility(View.GONE);
        isAllFabsVisible = false;
        fab.shrink();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "글쓰기", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (!isAllFabsVisible){
                    fab_info.show();
                    fab_store.show();
                    fab_meeting.show();

                    fab_text_info.setVisibility(View.VISIBLE);
                    fab_text_store.setVisibility(View.VISIBLE);
                    fab_text_meeting.setVisibility(View.VISIBLE);


                    fab.extend();
                    isAllFabsVisible = true;
                } else {
                    fab_info.hide();
                    fab_store.hide();
                    fab_meeting.hide();

                    fab_text_info.setVisibility(View.GONE);
                    fab_text_store.setVisibility(View.GONE);
                    fab_text_meeting.setVisibility(View.GONE);

                    fab.shrink();
                    isAllFabsVisible = false;
                }
            }
        });
        fab_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info_intent = new Intent(getActivity(),PostCommunityActivity.class);
                startActivity(info_intent);
            }
        });
        fab_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fab_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        tabRoot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition())
                {
                    case 0:
                        fragmentManager.beginTransaction().replace(R.id.tab_container, fragment1).commit();
                        break;
                    case 1:
                        fragmentManager.beginTransaction().replace(R.id.tab_container, fragment2).commit();
                        break;
                    case 2:
                        fragmentManager.beginTransaction().replace(R.id.tab_container, fragment3).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}