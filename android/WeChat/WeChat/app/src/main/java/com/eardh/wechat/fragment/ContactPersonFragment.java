package com.eardh.wechat.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eardh.wechat.R;
import com.eardh.wechat.adapter.ContactFragmentAdapter;
import com.eardh.wechat.fragment.contact.FriendFragment;
import com.eardh.wechat.fragment.contact.GroupFragment;
import com.eardh.wechat.page.AddFriendActivity;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ContactPersonFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ViewPager2 viewPager;
    private LinearLayout l_tab_friends, l_tab_group, l_current;
    private ImageView add_person_view;
    private Context context;

    public ContactPersonFragment(Context context) {
        this.context = context;
    }

    public static ContactPersonFragment newInstance(Context context) {
        ContactPersonFragment fragment = new ContactPersonFragment(context);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_contact_person, container, false);
            initTabView(view);
            initPagerView(view);
        }
        return view;
    }

    public void initTabView(View view) {
        l_tab_friends = view.findViewById(R.id.tab_contact_person_friend);
        l_tab_friends.setOnClickListener(this);
        l_tab_group = view.findViewById(R.id.tab_contact_person_group);
        l_tab_group.setOnClickListener(this);

        l_tab_friends.setSelected(true);
        l_current = l_tab_friends;

        add_person_view = view.findViewById(R.id.add_person_img_view);
        add_person_view.setOnClickListener(v -> startActivity(new Intent(context, AddFriendActivity.class)));
    }

    public void initPagerView(View view) {
        viewPager = view.findViewById(R.id.tab_contact_person_viewPager);
        List<Fragment> list = new ArrayList<>();
        list.add(FriendFragment.newInstance(context));
        list.add(GroupFragment.newInstance(context));

        viewPager.setAdapter(new ContactFragmentAdapter(getFragmentManager(), getLifecycle(), list));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                changeTab(position);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void changeTab(int position) {
        l_current.setSelected(false);
        switch (position) {
            case R.id.tab_contact_person_friend:
                viewPager.setCurrentItem(0, false);
            case 0:
                l_tab_friends.setSelected(true);
                l_current = l_tab_friends;
                break;
            case R.id.tab_contact_person_group:
                viewPager.setCurrentItem(1, false);
            case 1:
                l_tab_group.setSelected(true);
                l_current = l_tab_group;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }
}