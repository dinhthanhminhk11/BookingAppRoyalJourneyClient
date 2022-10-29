package com.example.bookingapproyaljourney;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.ui.activity.NearFromYouMapsActivity;
import com.example.bookingapproyaljourney.ui.fragment.HomeFragment;
import com.example.bookingapproyaljourney.ui.view.menu.DrawerAdapter;
import com.example.bookingapproyaljourney.ui.view.menu.DrawerItem;
import com.example.bookingapproyaljourney.ui.view.menu.SimpleItem;
import com.example.bookingapproyaljourney.ui.view.menu.SpaceItem;
import com.example.librarynav.SlidingRootNav;
import com.example.librarynav.SlidingRootNavBuilder;
import com.google.android.material.appbar.MaterialToolbar;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_HOME = 0;
    private static final int POS_PROFILE = 1;
    private static final int POS_NEARBY = 2;

    private static final int POS_BOOKMARK = 4;
    private static final int POS_NOTIFICATION = 5;
    private static final int POS_MESSAGES = 6;

    private static final int POS_SETTING = 8;
    private static final int POS_HELP = 9;
    private static final int POS_LOGOUT = 10;


    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    private FrameLayout container;
    private MaterialToolbar toolbar;
    private Spinner spinnerNav;
    private ImageView bell;
    private DrawerAdapter adapter;
    private List<String> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (FrameLayout) findViewById(R.id.containerMain);
        toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        spinnerNav = (Spinner) findViewById(R.id.spinnerLocationMain);
        bell = (ImageView) findViewById(R.id.bellMain);


        slidingRootNav = new SlidingRootNavBuilder(this)
                .withMenuOpened(false)
                .withToolbarMenuToggle(toolbar)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_PROFILE),
                createItemFor(POS_NEARBY),
                new SpaceItem(48),
                createItemFor(POS_BOOKMARK),
                createItemFor(POS_NOTIFICATION),
                createItemFor(POS_MESSAGES),
                new SpaceItem(48),
                createItemFor(POS_SETTING),
                createItemFor(POS_HELP),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.listMenuLeftDrawer);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setSelected(POS_HOME);
        initData();
    }

    private void initData() {
        locations.add("Hà Nội");
        locations.add("Hải Phòng");
        locations.add("Thái Bình ");
        locations.add("Hưng Yên");
        locations.add("Tp.Hồ Chí Minh");
        ArrayAdapter listAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, locations);
        spinnerNav.setAdapter(listAdapter);
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            UserClient userClient = UserClient.getInstance();
            userClient.setEmail("");
            userClient.setId("");
            userClient.setName("");
            userClient.setImage("");
            userClient.setPhone("");
            userClient.setAddress("");
            finish();
        } else if (position == POS_HOME) {
            // chỗ này là đoạn show home Frament
            // những cái kia tương tự
            // nếu show ở trong nay thì comment ở cái dưới đi dòng 92 93 ok
            showFragment(new HomeFragment());
        } else if (position == POS_NEARBY) {
            startActivity(new Intent(MainActivity.this, NearFromYouMapsActivity.class));
        }
        slidingRootNav.closeMenu();
//        Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position], slidingRootNav);
        showFragment(new HomeFragment());
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMain, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
                .withBackground(R.drawable.background_blue_activity)
                .withSelectedIconTint(color(R.color.blue))
                .withSelectedTextTint(color(R.color.blue))
                .withSelectedBackGroundTint(R.drawable.background_select_white_activity);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}