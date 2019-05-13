package com.example.todo;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class FolderContentActivity extends AppCompatActivity implements View.OnClickListener {
    static final String ARG_FOLDER_NAME = "argument_folder_name";

    private AppCompatImageView acivBack;
    private TextView tvTitle;
    private AppCompatImageView acivMenu;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabAdd;

    PagerAdapter pagerAdapter;
    private TaskAdapter taskAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_content);

        acivBack = findViewById(R.id.acivBack);
        acivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle = findViewById(R.id.tvTitle);
        acivMenu = findViewById(R.id.acivMenu);
        acivMenu.setOnClickListener(this);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);

        tvTitle.setText("Название папки");

        tabLayout.setupWithViewPager(viewPager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acivMenu:
                showPopup(v);
                break;
            case R.id.fabAdd:
                DialogFragment dialog = new TaskAdditionDialogFragment();
                dialog.show(getSupportFragmentManager(), "TaskAdditionDialogFragment");
                break;
        }
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.folder_content_toolbar_menu, popup.getMenu());
        popup.show();
    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            if (position == 0) {
                fragment = new TaskInProcessFragment();
            } else if (position == 1) {
                fragment = new TaskIsDoneFragment();
            } else {
                fragment = new TaskAllFragment();
            }
            String folderName = getIntent().getStringExtra("folder_name");
            Bundle args = new Bundle();
            args.putString(ARG_FOLDER_NAME, folderName);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return getString(R.string.in_process);
            else if (position == 1)
                return getString(R.string.done);
            else return getString(R.string.all);
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

}