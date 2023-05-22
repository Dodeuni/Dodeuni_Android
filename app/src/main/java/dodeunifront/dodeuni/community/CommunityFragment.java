package dodeunifront.dodeuni.community;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.DTO.CommunityListResponseDTO;
import io.github.muddz.styleabletoast.StyleableToast;

public class CommunityFragment extends Fragment {


    TabLayout tabRoot;
    Bigtab_infoFragment fragment1;
    Bigtab_storeFragment fragment2;
    Bigtab_meetingFragment fragment3;
    Boolean isAllFabsVisible;
    Long userId;
    String nickname;
    FrameLayout uaua;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_community, container, false);
        fragment1 = new Bigtab_infoFragment();
        fragment2 = new Bigtab_storeFragment();
        fragment3 = new Bigtab_meetingFragment();
        uaua = view.findViewById(R.id.tab_container);

        tabRoot = view.findViewById(R.id.tabRoot);


        tabRoot.removeAllTabs();
        tabRoot.addTab(tabRoot.newTab().setText("정보"));
        tabRoot.addTab(tabRoot.newTab().setText("장터"));
        tabRoot.addTab(tabRoot.newTab().setText("모임"));

        userId = this.getArguments().getLong("userId");
        nickname = this.getArguments().getString("nickname");

        Bundle bundle = new Bundle();
        bundle.putLong("userId", userId);
        bundle.putString("nickname",nickname);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragment1.setArguments(bundle);
        transaction.replace(R.id.tab_container, fragment1);
        transaction.commit();

        ExtendedFloatingActionButton fab = view.findViewById(R.id.fab_main);

        ExtendedFloatingActionButton fab_info = view.findViewById(R.id.fab_info);
        ExtendedFloatingActionButton fab_store = view.findViewById(R.id.fab_store);
        ExtendedFloatingActionButton fab_meeting = view.findViewById(R.id.fab_meeting);

        fab_info.setVisibility(View.GONE);
        fab_store.setVisibility(View.GONE);
        fab_meeting.setVisibility(View.GONE);



        isAllFabsVisible = false;
        fab.shrink();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAllFabsVisible){
                    //Snackbar.make(view, "글쓰기", Snackbar.LENGTH_LONG)
                      //      .setAction("Action", null).show();
                    fab_info.show();
                    fab_store.show();
                    fab_meeting.show();


                    fab.setIconResource(R.drawable.exitbutton);


                    fab.extend();
                    isAllFabsVisible = true;
                } else {
                    fab_info.hide();
                    fab_store.hide();
                    fab_meeting.hide();

                    fab.setIconResource(R.drawable.writemenu);


                    fab.shrink();
                    isAllFabsVisible = false;
                }
            }
        });

        fab_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info_intent = new Intent(getActivity(),PostCommunityActivity.class);
                info_intent.putExtra("userId",userId);
                info_intent.putExtra("nickname",nickname);

                startActivity(info_intent);
            }
        });

        fab_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent meet_intent = new Intent(getActivity(),PostCommunityMeetActivity.class);
                meet_intent.putExtra("userId",userId);
                meet_intent.putExtra("nickname",nickname);
                startActivity(meet_intent);
            }
        });
        fab_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent store_intent = new Intent(getActivity(),PostCommunityStoreActivity.class);
                store_intent.putExtra("userId",userId);
                store_intent.putExtra("nickname",nickname);
                startActivity(store_intent);
            }
        });


        tabRoot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition())
                {
                    case 0:
                        Bundle bundle = new Bundle();
                        bundle.putLong("userId", userId);
                        bundle.putString("nickname",nickname);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragment1.setArguments(bundle);
                        transaction.replace(R.id.tab_container, fragment1);
                        transaction.commit();

                        //fragmentManager.beginTransaction().replace(R.id.tab_container, fragment1).commit();
                        break;
                    case 1:
                        Bundle bundle2 = new Bundle();
                        bundle2.putLong("userID", userId);
                        bundle2.putString("nickname",nickname);
                        FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                        fragment2.setArguments(bundle2);
                        transaction2.replace(R.id.tab_container, fragment2);
                        transaction2.commit();
//                        fragmentManager.beginTransaction().replace(R.id.tab_container, fragment2).commit();
                        break;
                    case 2:
                        Bundle bundle3 = new Bundle();
                        bundle3.putLong("userID", userId);
                        bundle3.putString("nickname",nickname);
                        FragmentTransaction transaction3 = getActivity().getSupportFragmentManager().beginTransaction();
                        fragment3.setArguments(bundle3);
                        transaction3.replace(R.id.tab_container, fragment3);
                        transaction3.commit();
//                        fragmentManager.beginTransaction().replace(R.id.tab_container, fragment3).commit();
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